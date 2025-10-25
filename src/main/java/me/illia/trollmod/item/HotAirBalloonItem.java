package me.illia.trollmod.item;

import me.illia.trollmod.entity.HotAirBalloonEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HotAirBalloonItem extends Item {
	public HotAirBalloonItem(FabricItemSettings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		HotAirBalloonEntity entity = new HotAirBalloonEntity(world, user);
		entity.setPosition(user.getBlockPos().toCenterPos());
		world.spawnEntity(entity);
		user.getStackInHand(hand).decrement(1);
		return super.use(world, user, hand);
	}
}
