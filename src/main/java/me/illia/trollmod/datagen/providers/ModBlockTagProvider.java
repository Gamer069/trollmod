package me.illia.trollmod.datagen.providers;

import me.illia.trollmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		tag(BlockTags.LOGS_THAT_BURN)
			.add(ModBlocks.PURPLEHEART_LOG)
			.add(ModBlocks.STRIPPED_PURPLEHEART_LOG)
			.add(ModBlocks.PURPLEHEART_WOOD)
			.add(ModBlocks.STRIPPED_PURPLEHEART_WOOD)
			.add(ModBlocks.PURPLEHEART_PLANKS);

		tag(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)
			.add(ModBlocks.GHOST_BLOCK);
	}
}
