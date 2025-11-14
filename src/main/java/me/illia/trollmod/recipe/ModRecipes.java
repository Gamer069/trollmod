package me.illia.trollmod.recipe;

import me.illia.trollmod.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModRecipes {
	public static void init() {
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Util.id(TeapotRecipe.Serializer.ID), TeapotRecipe.Serializer.INSTANCE);
		Registry.register(BuiltInRegistries.RECIPE_TYPE, Util.id(TeapotRecipe.Type.ID), TeapotRecipe.Type.INSTANCE);
	}
}