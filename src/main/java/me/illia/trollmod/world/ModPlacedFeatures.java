package me.illia.trollmod.world;

import me.illia.trollmod.Util;
import me.illia.trollmod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import java.util.List;

public class ModPlacedFeatures {
	public static final ResourceKey<PlacedFeature> PURPLEHEART_PLACED_KEY = registerKey("purpleheart_placed");

	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Util.id(name));
	}

	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> config, List<PlacementModifier> mods) {
		context.register(key, new PlacedFeature(config, List.copyOf(mods)));
	}

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistryEntryLookup = context.lookup(Registries.CONFIGURED_FEATURE);
		register(
			context,
			PURPLEHEART_PLACED_KEY,
			configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PURPLEHEART_KEY),
			VegetationPlacements.treePlacement(
				PlacementUtils.countExtra(1, 0.1f, 2),
				ModBlocks.PURPLEHEART_SAPLING
			)
		);
	}
}
