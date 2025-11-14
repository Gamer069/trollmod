package me.illia.trollmod.world.tree;

import me.illia.trollmod.world.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class PurpleheartSaplingGenerator extends AbstractTreeGrower {
	@Override
	protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
		return ModConfiguredFeatures.PURPLEHEART_KEY;
	}
}
