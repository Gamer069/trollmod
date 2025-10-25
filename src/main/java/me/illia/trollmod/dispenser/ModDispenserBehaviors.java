package me.illia.trollmod.dispenser;

import me.illia.trollmod.item.ModItems;
import net.minecraft.block.DispenserBlock;

public class ModDispenserBehaviors {
	public static void init() {
		DispenserBlock.registerBehavior(ModItems.BOOMERANG, new BoomerangDispenserBehavior());
	}
}
