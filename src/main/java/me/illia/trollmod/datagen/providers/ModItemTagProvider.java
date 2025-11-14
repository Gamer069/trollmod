package me.illia.trollmod.datagen.providers;

import me.illia.trollmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		tag(ItemTags.PLANKS)
			.add(ModBlocks.PURPLEHEART_PLANKS.asItem());

		tag(ItemTags.LOGS_THAT_BURN)
			.add(ModBlocks.PURPLEHEART_LOG.asItem())
			.add(ModBlocks.STRIPPED_PURPLEHEART_LOG.asItem())
			.add(ModBlocks.PURPLEHEART_WOOD.asItem())
			.add(ModBlocks.STRIPPED_PURPLEHEART_WOOD.asItem());
	}
}
