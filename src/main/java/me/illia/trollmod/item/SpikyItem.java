package me.illia.trollmod.item;

import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.block.SpikyBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpikyItem extends Item {
	public SpikyItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos pos = context.getBlockPos();
		World world = context.getWorld();

		if (!world.isClient) {
			BlockState state = world.getBlockState(pos);
			world.setBlockState(pos, ModBlocks.SPIKY_BLOCK.getDefaultState());

			((SpikyBlockEntity) world.getBlockEntity(pos)).setBlock(state);

			return ActionResult.SUCCESS;
		} else {
			return ActionResult.PASS;
		}
	}
}
