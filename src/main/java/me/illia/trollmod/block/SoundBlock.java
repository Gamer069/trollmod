package me.illia.trollmod.block;

import me.illia.trollmod.SoundUtil;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.networking.ModNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class SoundBlock extends BaseEntityBlock {
	public SoundBlock(FabricBlockSettings fabricBlockSettings) {
		super(fabricBlockSettings);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide && hand == InteractionHand.MAIN_HAND) {
			AtomicReference<String> shared = new AtomicReference<>();

			Minecraft client = Minecraft.getInstance();
			client.pauseGame(false);

			new Thread(() -> {
				try (MemoryStack stack = MemoryStack.stackPush()) {
					String[] filters = { "*.wav", "*.aiff", "*.aif" };
					PointerBuffer filterBuffer = stack.mallocPointer(filters.length);
					for (String f : filters) filterBuffer.put(stack.UTF8(f));
					filterBuffer.flip();
					String fname = TinyFileDialogs.tinyfd_openFileDialog("Select sound", null, filterBuffer, "Audio files", false);

					if (fname == null) {
						return;
					}

					SoundUtil.AudioData data = SoundUtil.loadSound(fname);

					Trollmod.LOGGER.info("client-side data before main thread: " + data);

					client.doRunTask(() -> {
						Trollmod.LOGGER.info("client-side data: " + data);

						FriendlyByteBuf buf = PacketByteBufs.create();

						buf.writeBlockPos(pos);

						data.writeTo(buf);

						ClientPlayNetworking.send(ModNetworking.UPLOAD_SOUND, buf);

						player.displayClientMessage(Component.translatable("message.trollmod.sound_set", fname), true);
					});
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					throw new RuntimeException(e);
				}
			}).start();
		}

		return super.use(state, world, pos, player, hand, hit);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SoundBlockEntity(pos, state);
	}
}
