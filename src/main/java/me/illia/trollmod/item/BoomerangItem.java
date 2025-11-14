package me.illia.trollmod.item;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.entity.BoomerangEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BoomerangItem extends Item implements Vanishable {
	public BoomerangItem(Properties settings) {
		super(settings);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		// for now use spear
		return UseAnim.SPEAR;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack stack = user.getItemInHand(hand);

		if (!world.isClientSide) {
			// Create boomerang entity with owner
			BoomerangEntity boomerang = new BoomerangEntity(world, user, stack.getDamageValue(), null);

			// Horizontal look vector (ignore Y)
			Vec3 look = user.getViewVector(1.0F);
			Vec3 horizontal = new Vec3(look.x, 0, look.z).normalize();

			boomerang.setPos(
				user.getX() + horizontal.x * 0.5, // slightly in front
				user.getY() + 1.5,                // hand height
				user.getZ() + horizontal.z * 0.5
			);

			// Simple horizontal velocity
			double speed = 0.5; // tweak for visible travel
			boomerang.setDeltaMovement(horizontal.x * speed, 0, horizontal.z * speed);

			world.addFreshEntity(boomerang);
		}
		user.awardStat(Stats.ITEM_USED.get(this));
		if (user.getAbilities().instabuild) {
			user.getInventory().removeItem(stack);
		} else {
			stack.shrink(1);
		}
//			stack.damage(1, world.getRandom(), serverPlayerEntity);

		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
	}


}
