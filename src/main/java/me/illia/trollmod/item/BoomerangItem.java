package me.illia.trollmod.item;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.entity.BoomerangEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BoomerangItem extends Item implements Vanishable {
	public BoomerangItem(Settings settings) {
		super(settings);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		// for now use spear
		return UseAction.SPEAR;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		if (!world.isClient) {
			// Create boomerang entity with owner
			BoomerangEntity boomerang = new BoomerangEntity(world, user, stack.getDamage(), null);

			// Horizontal look vector (ignore Y)
			Vec3d look = user.getRotationVec(1.0F);
			Vec3d horizontal = new Vec3d(look.x, 0, look.z).normalize();

			boomerang.setPosition(
				user.getX() + horizontal.x * 0.5, // slightly in front
				user.getY() + 1.5,                // hand height
				user.getZ() + horizontal.z * 0.5
			);

			// Simple horizontal velocity
			double speed = 0.5; // tweak for visible travel
			boomerang.setVelocity(horizontal.x * speed, 0, horizontal.z * speed);

			world.spawnEntity(boomerang);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (user.getAbilities().creativeMode) {
			user.getInventory().removeOne(stack);
		} else {
			stack.decrement(1);
		}
//			stack.damage(1, world.getRandom(), serverPlayerEntity);

		return TypedActionResult.success(stack, world.isClient);
	}


}
