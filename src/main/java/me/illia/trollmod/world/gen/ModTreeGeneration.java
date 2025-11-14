package me.illia.trollmod.world.gen;

import me.illia.trollmod.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModTreeGeneration {
	public static void init() {
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PURPLEHEART_PLACED_KEY);
	}
}
