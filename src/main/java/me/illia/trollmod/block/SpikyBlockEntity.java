package me.illia.trollmod.block;

import me.illia.trollmod.Trollmod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.math.BlockPos;

public class SpikyBlockEntity extends BlockEntity {
	public BlockState block;

	public SpikyBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.SPIKY_BLOCK_ENTITY_TYPE, pos, state);
	}

	public void setBlock(BlockState block) {
		this.block = block;
		markDirty();
	}

	public BlockState getBlock() {
		return block;
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.put("state", BlockState.CODEC.encodeStart(NbtOps.INSTANCE, block).resultOrPartial(error -> Trollmod.LOGGER.error("Failed to encode blockstate to nbt: " + error)).get());
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		block = BlockState.CODEC.decode(NbtOps.INSTANCE, nbt.get("state")).resultOrPartial(error -> Trollmod.LOGGER.error("Failed to decode blockstate from nbt: " + error)).get().getFirst();
		super.readNbt(nbt);
	}
}
