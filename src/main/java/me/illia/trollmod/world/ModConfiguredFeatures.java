package me.illia.trollmod.world;

import me.illia.trollmod.Util;
import me.illia.trollmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ModConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLEHEART_KEY = registerKey("purpleheart");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		register(context, PURPLEHEART_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(ModBlocks.PURPLEHEART_LOG),
			new StraightTrunkPlacer(4, 2, 3),
			BlockStateProvider.simple(ModBlocks.PURPLEHEART_LEAVES),
			new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 3),
			new TwoLayersFeatureSize(1, 0, 2)
		).build());
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Util.id(name));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
		context.register(key, new ConfiguredFeature<>(feature, config));
	}
}
