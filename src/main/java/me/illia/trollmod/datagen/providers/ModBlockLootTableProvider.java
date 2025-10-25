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
		addDrop(ModBlocks.PURPLEHEART_LOG);
		addDrop(ModBlocks.PURPLEHEART_PLANKS);
		addDrop(ModBlocks.PURPLEHEART_WOOD);
		addDrop(ModBlocks.STRIPPED_PURPLEHEART_LOG);
		addDrop(ModBlocks.STRIPPED_PURPLEHEART_WOOD);
		addDrop(ModBlocks.PURPLEHEART_SAPLING);

		addDrop(ModBlocks.PURPLEHEART_LEAVES, leavesDrops(ModBlocks.PURPLEHEART_LEAVES, ModBlocks.PURPLEHEART_SAPLING, 0.0025f));
	}
}
