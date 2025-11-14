package me.illia.trollmod.block;

import me.illia.trollmod.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GhostBlockEntity extends BlockEntity {
	private Block block;
	private PlayerEntity owner;

	public GhostBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.GHOST_BLOCK_ENTITY_TYPE, pos, state);
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
		markDirty();
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

		if (block != null) {
			nbt.putString("ghost_block_id", Registries.BLOCK.getId(block).toString());
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);

		if (nbt.contains("ghost_block_id", NbtElement.STRING_TYPE)) {
			String idStr = nbt.getString("ghost_block_id");
			if (idStr != null) {
				Identifier id = Util.idFrom(idStr);
				block = Registries.BLOCK.get(id);
			}
		} else {
			block = null;
		}
	}

	public PlayerEntity getOwner() {
		return owner;
	}

	public void setOwner(PlayerEntity owner) {
		this.owner = owner;
	}
}
