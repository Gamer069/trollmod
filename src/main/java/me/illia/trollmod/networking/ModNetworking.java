package me.illia.trollmod.networking;

import me.illia.trollmod.SoundUtil;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import me.illia.trollmod.block.SoundBlockEntity;
import me.illia.trollmod.block.SpikyBlockEntity;
import me.illia.trollmod.component.ModComponents;
import me.illia.trollmod.entity.BoomerangEntity;
import me.illia.trollmod.screen.TeapotScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModNetworking {
	public static final ResourceLocation TEAPOT_SYNC = Util.id("teapot_sync");
	public static final ResourceLocation CATCH_BOOMERANG = Util.id("catch_boomerang");
	public static final ResourceLocation PHASE = Util.id("phase");
	public static final ResourceLocation UPLOAD_SOUND = Util.id("upload_sound");
	public static final ResourceLocation SET_SPIKY = Util.id("set_spiky");

	public static void init() {
		Trollmod.LOGGER.info("Initializing networking for mod " + Trollmod.MODID);

		ServerPlayNetworking.registerGlobalReceiver(TEAPOT_SYNC, (server, player, handler, buf, _responseSender) -> {
			byte syncId = buf.readByte();
			CompoundTag inv = buf.readNbt();

			int size = buf.readInt();
			NonNullList<ItemStack> stacks = NonNullList.createWithCapacity(size);

			ContainerHelper.loadAllItems(inv, stacks);

			AbstractContainerMenu screenHandler = player.containerMenu;

			if (screenHandler instanceof TeapotScreenHandler teapotScreenHandler && screenHandler.containerId == syncId) {
				Trollmod.LOGGER.info("Syncing, size: " + stacks.size() + ", stacks: " + stacks);

				for (int i = 0; i < stacks.size(); i++) {
					Trollmod.LOGGER.info("SIZE ITERATION");
					ItemStack stack = stacks.get(i);
					teapotScreenHandler.getSlot(i).setByPlayer(stack);

					teapotScreenHandler.broadcastChanges();
				}

				teapotScreenHandler.inv.setChanged();
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(CATCH_BOOMERANG, (server, player, handler, buf, _responseSender) -> {
			int entityId = buf.readVarInt();
			BoomerangEntity entity = (BoomerangEntity)player.serverLevel().getEntity(entityId);

			if (entity != null) {
				entity.give(player, false);
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(PHASE, (server, player, handler, buf, _responseSender) -> {
			boolean phasing = buf.readBoolean();
			int ticksLeft = buf.readInt();
			player.getComponent(ModComponents.PHASING_COMPONENT_KEY).setPhasing(phasing);
			player.getComponent(ModComponents.PHASING_COMPONENT_KEY).setTicksLeft(ticksLeft);
		});

		ServerPlayNetworking.registerGlobalReceiver(UPLOAD_SOUND, (server, player, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();
			SoundUtil.AudioData data = SoundUtil.AudioData.readFrom(buf);

			Trollmod.LOGGER.info("server-side data: " + data);
			Trollmod.LOGGER.info("pos: " + pos);

			server.execute(() -> {
				BlockEntity entity = player.level().getBlockEntity(pos);

				if (entity instanceof SoundBlockEntity sound) {
					sound.setData(data);
				}
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(SET_SPIKY, (server, player, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();

			CompoundTag nbt = buf.readNbt();
			if (nbt == null) return;

			BlockState state = BlockState.CODEC.parse(NbtOps.INSTANCE, nbt)
				.resultOrPartial(error -> {
					Trollmod.LOGGER.error("Failed to decode with codec: " + error);
				})
				.orElseThrow();

			server.execute(() -> {
				BlockEntity entity = player.level().getBlockEntity(pos);

				if (entity instanceof SpikyBlockEntity sound) {
					sound.setBlock(state);
				}
			});
		});
	}
}
