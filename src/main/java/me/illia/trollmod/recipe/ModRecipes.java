package me.illia.trollmod.recipe;

import me.illia.trollmod.Trollmod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
	public static void register() {
		Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(Trollmod.MODID, TeapotRecipe.Serializer.ID), TeapotRecipe.Serializer.INSTANCE);
		Registry.register(Registries.RECIPE_TYPE, Identifier.of(Trollmod.MODID, TeapotRecipe.Type.ID), TeapotRecipe.Type.INSTANCE);
	}
}