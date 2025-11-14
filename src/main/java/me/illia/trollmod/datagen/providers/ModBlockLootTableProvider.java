package me.illia.trollmod.datagen.providers;

import me.illia.trollmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
	public ModBlockLootTableProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generate() {
		dropSelf(ModBlocks.PURPLEHEART_LOG);
		dropSelf(ModBlocks.PURPLEHEART_PLANKS);
		dropSelf(ModBlocks.PURPLEHEART_WOOD);
		dropSelf(ModBlocks.STRIPPED_PURPLEHEART_LOG);
		dropSelf(ModBlocks.STRIPPED_PURPLEHEART_WOOD);
		dropSelf(ModBlocks.PURPLEHEART_SAPLING);

		add(ModBlocks.PURPLEHEART_LEAVES, createLeavesDrops(ModBlocks.PURPLEHEART_LEAVES, ModBlocks.PURPLEHEART_SAPLING, 0.0025f));
	}
}
