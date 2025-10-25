package me.illia.trollmod.datagen.providers;

import me.illia.trollmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
	public ModRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> consumer) {
		ShapelessRecipeJsonBuilder.create(
			RecipeCategory.MISC,
			ModBlocks.PURPLEHEART_PLANKS.asItem(),
			4
		)
			.input(ModBlocks.PURPLEHEART_LOG)
			.criterion(FabricRecipeProvider.hasItem(ModBlocks.PURPLEHEART_LOG.asItem()), FabricRecipeProvider.conditionsFromItem(ModBlocks.PURPLEHEART_LOG))
			.offerTo(consumer);
	}
}
