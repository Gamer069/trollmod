package me.illia.trollmod.world;

import me.illia.trollmod.Util;
import me.illia.trollmod.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
	public static final RegistryKey<PlacedFeature> PURPLEHEART_PLACED_KEY = registerKey("purpleheart_placed");

	public static RegistryKey<PlacedFeature> registerKey(String name) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Util.id(name));
	}

	private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> config, List<PlacementModifier> mods) {
		context.register(key, new PlacedFeature(config, List.copyOf(mods)));
	}

	public static void bootstrap(Registerable<PlacedFeature> context) {
		RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
		register(
			context,
			PURPLEHEART_PLACED_KEY,
			configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PURPLEHEART_KEY),
			VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
				PlacedFeatures.createCountExtraModifier(1, 0.1f, 2),
				ModBlocks.PURPLEHEART_SAPLING
			)
		);
	}
}
