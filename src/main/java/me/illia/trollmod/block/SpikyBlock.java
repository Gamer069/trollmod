package me.illia.trollmod.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SpikyBlock extends BlockWithEntity {
	public SpikyBlock(FabricBlockSettings fabricBlockSettings) {
		super(fabricBlockSettings);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SpikyBlockEntity(pos, state);
	}
}
