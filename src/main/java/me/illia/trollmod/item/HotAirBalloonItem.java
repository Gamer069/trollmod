package me.illia.trollmod.item;

import me.illia.trollmod.entity.HotAirBalloonEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HotAirBalloonItem extends Item {
	public HotAirBalloonItem(FabricItemSettings settings) {
		super(settings);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		HotAirBalloonEntity entity = new HotAirBalloonEntity(world, user);
		entity.setPos(user.blockPosition().getCenter());
		world.addFreshEntity(entity);
		user.getItemInHand(hand).shrink(1);
		return super.use(world, user, hand);
	}
}
