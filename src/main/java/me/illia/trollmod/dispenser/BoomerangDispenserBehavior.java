package me.illia.trollmod.dispenser;

import net.minecraft.block.dispenser.ItemDispenserBehavior;
import me.illia.trollmod.entity.BoomerangEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BoomerangDispenserBehavior extends ItemDispenserBehavior {
	@Override
	protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		ServerWorld world = pointer.getWorld();
		Direction dir = pointer.getBlockState().get(DispenserBlock.FACING);
		BlockPos pos = pointer.getPos().offset(dir);

		if (!world.isClient) {
			BoomerangEntity boomerang = new BoomerangEntity(world, null, stack.getDamage(), pos);
			Vec3d look = Vec3d.of(dir.getVector());
			Vec3d horizontal = new Vec3d(look.x, 0, look.z).normalize();

			boomerang.setPosition(
				pos.getX() + horizontal.x * 0.5,
				pos.getY(),
				pos.getZ() + horizontal.z * 0.5
			);

			double speed = 0.5;
			boomerang.setVelocity(horizontal.x * speed, 0, horizontal.z * speed);

			world.spawnEntity(boomerang);
		}

		stack.damage(1, world.getRandom(), null);

		return stack;
	}
}
