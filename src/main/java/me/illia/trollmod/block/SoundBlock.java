package me.illia.trollmod.block;

import me.illia.trollmod.SoundUtil;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.networking.ModNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class SoundBlock extends BlockWithEntity {
	public SoundBlock(FabricBlockSettings fabricBlockSettings) {
		super(fabricBlockSettings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient && hand == Hand.MAIN_HAND) {
			AtomicReference<String> shared = new AtomicReference<>();

			MinecraftClient client = MinecraftClient.getInstance();
			client.openPauseMenu(false);

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

					client.executeTask(() -> {
						Trollmod.LOGGER.info("client-side data: " + data);

						PacketByteBuf buf = PacketByteBufs.create();

						buf.writeBlockPos(pos);

						data.writeTo(buf);

						ClientPlayNetworking.send(ModNetworking.UPLOAD_SOUND, buf);

						player.sendMessage(Text.translatable("message.trollmod.sound_set", fname), true);
					});
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					throw new RuntimeException(e);
				}
			}).start();
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SoundBlockEntity(pos, state);
	}
}
