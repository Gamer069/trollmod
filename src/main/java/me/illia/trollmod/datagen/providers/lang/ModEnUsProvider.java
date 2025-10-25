package me.illia.trollmod.datagen.providers.lang;

import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.client.ModKeybinds;
import me.illia.trollmod.entity.ModEntities;
import me.illia.trollmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModEnUsProvider extends FabricLanguageProvider {
	public ModEnUsProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generateTranslations(TranslationBuilder translationBuilder) {
		translationBuilder.add(ModBlocks.PURPLEHEART_WOOD, "Purpleheart Wood");
		translationBuilder.add(ModBlocks.PURPLEHEART_LOG, "Purpleheart Log");
		translationBuilder.add(ModBlocks.PURPLEHEART_PLANKS, "Purpleheart Planks");
		translationBuilder.add(ModBlocks.STRIPPED_PURPLEHEART_WOOD, "Stripped Purpleheart Wood");
		translationBuilder.add(ModBlocks.STRIPPED_PURPLEHEART_LOG, "Stripped Purpleheart Log");
		translationBuilder.add(ModBlocks.PURPLEHEART_LEAVES, "Purpleheart Leaves");
		translationBuilder.add(ModBlocks.PURPLEHEART_SAPLING, "Purpleheart Sapling");

		translationBuilder.add(ModItems.BOOMERANG, "Boomerang");
		translationBuilder.add(ModItems.WOODEN_TEAPOT, "Wooden Teapot");
		translationBuilder.add(ModItems.HOT_AIR_BALLOON, "Hot Air Balloon");

		translationBuilder.add(ModEntities.HOT_AIR_BALLOON, "Hot Air Balloon");

		translationBuilder.add(ModKeybinds.CATCH.getTranslationKey(), "Catch Boomerang");
		translationBuilder.add(ModKeybinds.PHASE.getTranslationKey(), "Phase");
		translationBuilder.add(ModKeybinds.CATCH.getCategory(), "trollmod");

		translationBuilder.add(ModItems.ITEMS_GROUP_KEY, "%1$s items");
		translationBuilder.add(ModBlocks.BLOCKS_GROUP_KEY, "%1$s blocks");

		translationBuilder.add("death.attack.boomerang", "%1$s was obliterated by a boomerang");
		translationBuilder.add("screen.trollmod.teapot", "Teapot");
		translationBuilder.add("msg.trollmod.locked", "Perspective is locked!");
	}
}
