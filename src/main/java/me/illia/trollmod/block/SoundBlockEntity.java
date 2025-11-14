package me.illia.trollmod.block;

import me.illia.trollmod.SoundUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.nio.file.Files;

public class SoundBlockEntity extends BlockEntity {
	private SoundUtil.AudioData data;

	public SoundBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.SOUND_BLOCK_ENTITY_TYPE, pos, state);
	}

	public void setData(SoundUtil.AudioData data) {
		this.data = data;
		markDirty();
	}

	public SoundUtil.AudioData getData() {
		return data;
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		if (data != null)
			data.writeToFile(pos, world.getServer());

		super.writeNbt(nbt);
	}

	@Override
	public void setWorld(World world) {
		super.setWorld(world);

		if (!world.isClient) {
			MinecraftServer server = world.getServer();
			if (Files.exists(SoundUtil.AudioData.getPath(pos, server))) {
				data = SoundUtil.AudioData.readFromFile(pos, server);
			}
		}
	}
}
