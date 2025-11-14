package me.illia.trollmod.block;

import me.illia.trollmod.Trollmod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SpikyBlockEntity extends BlockEntity {
	public BlockState block;

	public SpikyBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.SPIKY_BLOCK_ENTITY_TYPE, pos, state);
	}

	public void setBlock(BlockState block) {
		this.block = block;
		setChanged();
	}

	public BlockState getBlock() {
		return block;
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("state", BlockState.CODEC.encodeStart(NbtOps.INSTANCE, block).resultOrPartial(error -> Trollmod.LOGGER.error("Failed to encode blockstate to nbt: " + error)).get());
		super.saveAdditional(nbt);
	}

	@Override
	public void load(CompoundTag nbt) {
		block = BlockState.CODEC.decode(NbtOps.INSTANCE, nbt.get("state")).resultOrPartial(error -> Trollmod.LOGGER.error("Failed to decode blockstate from nbt: " + error)).get().getFirst();
		super.load(nbt);
	}
}
