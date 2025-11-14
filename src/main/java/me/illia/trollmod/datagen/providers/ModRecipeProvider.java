package me.illia.trollmod.datagen.providers;

import me.illia.trollmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
	public ModRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(
			RecipeCategory.MISC,
			ModBlocks.PURPLEHEART_PLANKS.asItem(),
			4
		)
			.requires(ModBlocks.PURPLEHEART_LOG)
			.unlockedBy(FabricRecipeProvider.getHasName(ModBlocks.PURPLEHEART_LOG.asItem()), FabricRecipeProvider.has(ModBlocks.PURPLEHEART_LOG))
			.save(consumer);
	}
}
