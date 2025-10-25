package me.illia.trollmod.datagen.providers;

import me.illia.trollmod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.TexturedModel;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerLog(ModBlocks.PURPLEHEART_LOG).log(ModBlocks.PURPLEHEART_LOG).wood(ModBlocks.PURPLEHEART_WOOD);
		blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_PURPLEHEART_LOG).log(ModBlocks.STRIPPED_PURPLEHEART_LOG).wood(ModBlocks.STRIPPED_PURPLEHEART_WOOD);
		blockStateModelGenerator.registerSingleton(ModBlocks.PURPLEHEART_LEAVES, TexturedModel.LEAVES);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PURPLEHEART_PLANKS);
		blockStateModelGenerator.registerTintableCross(ModBlocks.PURPLEHEART_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
	}
}
