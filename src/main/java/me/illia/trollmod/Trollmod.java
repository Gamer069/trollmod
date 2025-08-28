package me.illia.trollmod;

import me.illia.trollmod.damage.ModDamageTypes;
import me.illia.trollmod.entity.BoomerangEntity;
import me.illia.trollmod.entity.ModEntities;
import me.illia.trollmod.item.ModItems;
import me.illia.trollmod.recipe.ModRecipes;
import me.illia.trollmod.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trollmod implements ModInitializer {
	public static String MODID = "trollmod";
	public static Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModItems.init();
		ModEntities.init();
		ModScreenHandlers.init();
		ModDamageTypes.init();
		ModRecipes.register();

		DispenserBlock.registerBehavior(ModItems.BOOMERANG, new ItemDispenserBehavior() {
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
		});
	}
}
