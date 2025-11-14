package me.illia.trollmod.datagen.providers.lang;

import me.illia.trollmod.Util;
import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.client.ModKeybinds;
import me.illia.trollmod.effect.ModEffects;
import me.illia.trollmod.entity.ModEntities;
import me.illia.trollmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModEnUsProvider extends FabricLanguageProvider {
	public ModEnUsProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generateTranslations(TranslationBuilder t) {
		Util.t(t, ModBlocks.PURPLEHEART_WOOD, "Purpleheart Wood");
		Util.t(t, ModBlocks.PURPLEHEART_LOG, "Purpleheart Log");
		Util.t(t, ModBlocks.PURPLEHEART_PLANKS, "Purpleheart Planks");
		Util.t(t, ModBlocks.PURPLEHEART_BUTTON, "Purpleheart Button");
		Util.t(t, ModBlocks.PURPLEHEART_STAIRS, "Purpleheart Stairs");
		Util.t(t, ModBlocks.PURPLEHEART_SLAB, "Purpleheart Slab");
		Util.t(t, ModBlocks.PURPLEHEART_TRAPDOOR, "Purpleheart Trapdoor");
		Util.t(t, ModBlocks.PURPLEHEART_DOOR, "Purpleheart Door");
		Util.t(t, ModBlocks.PURPLEHEART_FENCE, "Purpleheart Fence");
		Util.t(t, ModBlocks.PURPLEHEART_FENCE_GATE, "Purpleheart Fence Gate");
		Util.t(t, ModBlocks.PURPLEHEART_PRESSURE_PLATE, "Purpleheart Pressure Plate");

		Util.t(t, ModBlocks.STRIPPED_PURPLEHEART_WOOD, "Stripped Purpleheart Wood");
		Util.t(t, ModBlocks.STRIPPED_PURPLEHEART_LOG, "Stripped Purpleheart Log");
		Util.t(t, ModBlocks.PURPLEHEART_LEAVES, "Purpleheart Leaves");
		Util.t(t, ModBlocks.PURPLEHEART_SAPLING, "Purpleheart Sapling");

		Util.t(t, ModBlocks.GHOST_BLOCK, "Ghost Block");

		Util.t(t, ModItems.BOOMERANG, "Boomerang");
		Util.t(t, ModItems.WOODEN_TEAPOT, "Wooden Teapot");
		Util.t(t, ModItems.HOT_AIR_BALLOON, "Hot Air Balloon");
		Util.t(t, ModItems.MOVING_WAND, "Moving Wand");
		Util.t(t, ModItems.TOTEM_OF_DYING, "Totem of Dying");

		Util.t(t, ModEffects.INVERT_CONTROLS.value(), "Invert Controls");

		Util.t(t, "item.minecraft.potion.effect.invert_controls", "Potion of Inverted Controls");
		Util.t(t, "item.minecraft.potion.effect.long_invert_controls", "Potion of Inverted Controls");

		Util.t(t, "item.minecraft.splash_potion.effect.invert_controls", "Splash Potion of Inverted Controls");
		Util.t(t, "item.minecraft.splash_potion.effect.long_invert_controls", "Splash Potion of Inverted Controls");

		Util.t(t, "item.minecraft.lingering_potion.effect.invert_controls", "Lingering Potion of Inverted Controls");
		Util.t(t, "item.minecraft.lingering_potion.effect.long_invert_controls", "Lingering Potion of Inverted Controls");


		Util.t(t, ModEntities.HOT_AIR_BALLOON, "Hot Air Balloon");

		Util.t(t, ModKeybinds.CATCH.getName(), "Catch Boomerang");
		Util.t(t, ModKeybinds.PHASE.getName(), "Phase");
		Util.t(t, ModKeybinds.CATCH.getCategory(), "trollmod");

		Util.t(t, ModItems.ITEMS_GROUP_KEY, "%1$s items");
		Util.t(t, ModBlocks.BLOCKS_GROUP_KEY, "%1$s blocks");

		Util.t(t, "message.trollmod.sound_set", "Sound set to %1$s");

		Util.t(t, "death.attack.boomerang", "%1$s was obliterated by a boomerang");
		Util.t(t, "screen.trollmod.teapot", "Teapot");
		Util.t(t, "msg.trollmod.locked", "Perspective is locked!");
	}
}
