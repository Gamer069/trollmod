package me.illia.trollmod.datagen.providers;

import me.illia.trollmod.Util;
import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.block.ModSetTypes;
import me.illia.trollmod.block.ModSigns;
import me.illia.trollmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators b) {
		b.woodProvider(ModBlocks.PURPLEHEART_LOG).logWithHorizontal(ModBlocks.PURPLEHEART_LOG).wood(ModBlocks.PURPLEHEART_WOOD);
		b.woodProvider(ModBlocks.STRIPPED_PURPLEHEART_LOG).logWithHorizontal(ModBlocks.STRIPPED_PURPLEHEART_LOG).wood(ModBlocks.STRIPPED_PURPLEHEART_WOOD);
		b.createTrivialBlock(ModBlocks.PURPLEHEART_LEAVES, TexturedModel.LEAVES);
		b.createCrossBlockWithDefaultItem(ModBlocks.PURPLEHEART_SAPLING, BlockModelGenerators.TintState.NOT_TINTED);

		BlockModelGenerators.BlockFamilyProvider signTexturePool = b.family(ModBlocks.PURPLEHEART_PLANKS);

		signTexturePool.generateFor(ModSetTypes.PURPLEHEART_FAMILY);

		b.createHangingSign(ModBlocks.STRIPPED_PURPLEHEART_LOG, ModSigns.PURPLEHEART_HANGING_SIGN, ModSigns.PURPLEHEART_WALL_HANGING_SIGN);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(ModItems.BOOMERANG, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.HOT_AIR_BALLOON, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.WOODEN_TEAPOT, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.MOVING_WAND, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.TOTEM_OF_DYING, ModelTemplates.FLAT_ITEM);
	}
}
