package me.illia.trollmod.dispenser;

import me.illia.trollmod.entity.BoomerangEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public class BoomerangDispenserBehavior extends DefaultDispenseItemBehavior {
	@Override
	protected ItemStack execute(BlockSource pointer, ItemStack stack) {
		ServerLevel world = pointer.getLevel();
		Direction dir = pointer.getBlockState().getValue(DispenserBlock.FACING);
		BlockPos pos = pointer.getPos().relative(dir);

		if (!world.isClientSide) {
			BoomerangEntity boomerang = new BoomerangEntity(world, null, stack.getDamageValue(), pos);
			Vec3 look = Vec3.atLowerCornerOf(dir.getNormal());
			Vec3 horizontal = new Vec3(look.x, 0, look.z).normalize();

			boomerang.setPos(
				pos.getX() + horizontal.x * 0.5,
				pos.getY(),
				pos.getZ() + horizontal.z * 0.5
			);

			double speed = 0.5;
			boomerang.setDeltaMovement(horizontal.x * speed, 0, horizontal.z * speed);

			world.addFreshEntity(boomerang);
		}

		stack.hurt(1, world.getRandom(), null);

		return stack;
	}
}
