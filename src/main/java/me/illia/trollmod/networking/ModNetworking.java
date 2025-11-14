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
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ModNetworking {
	public static final Identifier TEAPOT_SYNC = Util.id("teapot_sync");
	public static final Identifier CATCH_BOOMERANG = Util.id("catch_boomerang");
	public static final Identifier PHASE = Util.id("phase");
	public static final Identifier UPLOAD_SOUND = Util.id("upload_sound");
	public static final Identifier SET_SPIKY = Util.id("set_spiky");

	public static void init() {
		Trollmod.LOGGER.info("Initializing networking for mod " + Trollmod.MODID);

		ServerPlayNetworking.registerGlobalReceiver(TEAPOT_SYNC, (server, player, handler, buf, _responseSender) -> {
			byte syncId = buf.readByte();
			NbtCompound inv = buf.readNbt();

			int size = buf.readInt();
			DefaultedList<ItemStack> stacks = DefaultedList.ofSize(size);

			Inventories.readNbt(inv, stacks);

			ScreenHandler screenHandler = player.currentScreenHandler;

			if (screenHandler instanceof TeapotScreenHandler teapotScreenHandler && screenHandler.syncId == syncId) {
				Trollmod.LOGGER.info("Syncing, size: " + stacks.size() + ", stacks: " + stacks);

				for (int i = 0; i < stacks.size(); i++) {
					Trollmod.LOGGER.info("SIZE ITERATION");
					ItemStack stack = stacks.get(i);
					teapotScreenHandler.getSlot(i).setStack(stack);

					teapotScreenHandler.sendContentUpdates();
				}

				teapotScreenHandler.inv.markDirty();
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(CATCH_BOOMERANG, (server, player, handler, buf, _responseSender) -> {
			int entityId = buf.readVarInt();
			BoomerangEntity entity = (BoomerangEntity)player.getServerWorld().getEntityById(entityId);

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
				BlockEntity entity = player.getWorld().getBlockEntity(pos);

				if (entity instanceof SoundBlockEntity sound) {
					sound.setData(data);
				}
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(SET_SPIKY, (server, player, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();

			NbtCompound nbt = buf.readNbt();
			if (nbt == null) return;

			BlockState state = BlockState.CODEC.parse(NbtOps.INSTANCE, nbt)
				.resultOrPartial(error -> {
					Trollmod.LOGGER.error("Failed to decode with codec: " + error);
				})
				.orElseThrow();

			server.execute(() -> {
				BlockEntity entity = player.getWorld().getBlockEntity(pos);

				if (entity instanceof SpikyBlockEntity sound) {
					sound.setBlock(state);
				}
			});
		});
	}
}
