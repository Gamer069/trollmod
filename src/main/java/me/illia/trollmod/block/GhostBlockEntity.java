package me.illia.trollmod.block;

import me.illia.trollmod.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GhostBlockEntity extends BlockEntity {
	private Block block;
	private Player owner;

	public GhostBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.GHOST_BLOCK_ENTITY_TYPE, pos, state);
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
		setChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);

		if (block != null) {
			nbt.putString("ghost_block_id", BuiltInRegistries.BLOCK.getKey(block).toString());
		}
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);

		if (nbt.contains("ghost_block_id", Tag.TAG_STRING)) {
			String idStr = nbt.getString("ghost_block_id");
			if (idStr != null) {
				ResourceLocation id = Util.idFrom(idStr);
				block = BuiltInRegistries.BLOCK.get(id);
			}
		} else {
			block = null;
		}
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
