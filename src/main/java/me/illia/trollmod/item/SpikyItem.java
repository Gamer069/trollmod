package me.illia.trollmod.item;

import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.block.SpikyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SpikyItem extends Item {
	public SpikyItem(Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Level world = context.getLevel();

		if (!world.isClientSide) {
			BlockState state = world.getBlockState(pos);
			world.setBlockAndUpdate(pos, ModBlocks.SPIKY_BLOCK.defaultBlockState());

			((SpikyBlockEntity) world.getBlockEntity(pos)).setBlock(state);

			return InteractionResult.SUCCESS;
		} else {
			return InteractionResult.PASS;
		}
	}
}
