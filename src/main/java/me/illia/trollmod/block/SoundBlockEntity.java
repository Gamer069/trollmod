package me.illia.trollmod.block;

import me.illia.trollmod.SoundUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.nio.file.Files;

public class SoundBlockEntity extends BlockEntity {
	private SoundUtil.AudioData data;

	public SoundBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.SOUND_BLOCK_ENTITY_TYPE, pos, state);
	}

	public void setData(SoundUtil.AudioData data) {
		this.data = data;
		setChanged();
	}

	public SoundUtil.AudioData getData() {
		return data;
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		if (data != null)
			data.writeToFile(worldPosition, level.getServer());

		super.saveAdditional(nbt);
	}

	@Override
	public void setLevel(Level world) {
		super.setLevel(world);

		if (!world.isClientSide) {
			MinecraftServer server = world.getServer();
			if (Files.exists(SoundUtil.AudioData.getPath(worldPosition, server))) {
				data = SoundUtil.AudioData.readFromFile(worldPosition, server);
			}
		}
	}
}
