package me.illia.trollmod.world.gen;

import me.illia.trollmod.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {
	public static void init() {
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PURPLEHEART_PLACED_KEY);
	}
}
