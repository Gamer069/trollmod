package me.illia.trollmod.datagen.providers;

import me.illia.trollmod.Util;
import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.block.ModSetTypes;
import me.illia.trollmod.block.ModSigns;
import me.illia.trollmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator b) {
		b.registerLog(ModBlocks.PURPLEHEART_LOG).log(ModBlocks.PURPLEHEART_LOG).wood(ModBlocks.PURPLEHEART_WOOD);
		b.registerLog(ModBlocks.STRIPPED_PURPLEHEART_LOG).log(ModBlocks.STRIPPED_PURPLEHEART_LOG).wood(ModBlocks.STRIPPED_PURPLEHEART_WOOD);
		b.registerSingleton(ModBlocks.PURPLEHEART_LEAVES, TexturedModel.LEAVES);
		b.registerTintableCross(ModBlocks.PURPLEHEART_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

		BlockStateModelGenerator.BlockTexturePool signTexturePool = b.registerCubeAllModelTexturePool(ModBlocks.PURPLEHEART_PLANKS);

		signTexturePool.family(ModSetTypes.PURPLEHEART_FAMILY);

		b.registerHangingSign(ModBlocks.STRIPPED_PURPLEHEART_LOG, ModSigns.PURPLEHEART_HANGING_SIGN, ModSigns.PURPLEHEART_WALL_HANGING_SIGN);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModItems.BOOMERANG, Models.GENERATED);
		itemModelGenerator.register(ModItems.HOT_AIR_BALLOON, Models.GENERATED);
		itemModelGenerator.register(ModItems.WOODEN_TEAPOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.MOVING_WAND, Models.GENERATED);
		itemModelGenerator.register(ModItems.TOTEM_OF_DYING, Models.GENERATED);
	}
}
