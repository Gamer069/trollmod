package me.illia.trollmod.item;

import me.illia.trollmod.Util;
import me.illia.trollmod.effect.ModEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class ModPotions {
	public static final Potion INVERT_CONTROLS = Util.pot(Util.id("invert_controls"), Util.desc(ModEffects.INVERT_CONTROLS, 1800, 0));
	public static final Potion LONG_INVERT_CONTROLS = Util.pot(Util.id("long_invert_controls"), Util.desc(ModEffects.INVERT_CONTROLS, 4800, 0));

	public static void init() {
		Util.potRecipe(Items.PUFFERFISH, INVERT_CONTROLS, Potions.SLOWNESS);
		Util.potRecipe(Items.PUFFERFISH, LONG_INVERT_CONTROLS, Potions.LONG_SLOWNESS);
		Util.potRecipe(Items.PUFFERFISH, INVERT_CONTROLS, Potions.STRONG_SLOWNESS);
	}
}
