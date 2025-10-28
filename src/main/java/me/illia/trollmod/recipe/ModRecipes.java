package me.illia.trollmod.recipe;

import me.illia.trollmod.Util;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
	public static void init() {
		Registry.register(Registries.RECIPE_SERIALIZER, Util.id(TeapotRecipe.Serializer.ID), TeapotRecipe.Serializer.INSTANCE);
		Registry.register(Registries.RECIPE_TYPE, Util.id(TeapotRecipe.Type.ID), TeapotRecipe.Type.INSTANCE);
	}
}