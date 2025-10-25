package me.illia.trollmod.datagen.providers.lang;

import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.client.ModKeybinds;
import me.illia.trollmod.entity.ModEntities;
import me.illia.trollmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModUkUaProvider extends FabricLanguageProvider {
	public ModUkUaProvider(FabricDataOutput dataOutput) {
		super(dataOutput, "uk_ua");
	}

	@Override
	public void generateTranslations(TranslationBuilder translationBuilder) {
		translationBuilder.add(ModBlocks.PURPLEHEART_WOOD, "Амарантнова деревина");
		translationBuilder.add(ModBlocks.PURPLEHEART_LOG, "Амарантнова колода");
		translationBuilder.add(ModBlocks.PURPLEHEART_PLANKS, "Амарантнові дошки");
		translationBuilder.add(ModBlocks.STRIPPED_PURPLEHEART_WOOD, "Обтесана амарантнова деревина");
		translationBuilder.add(ModBlocks.STRIPPED_PURPLEHEART_LOG, "Обтесана амарантнова колода");
		translationBuilder.add(ModBlocks.PURPLEHEART_LEAVES, "Амарантнове листя");
		translationBuilder.add(ModBlocks.PURPLEHEART_SAPLING, "Паросток амаранта");

		translationBuilder.add(ModItems.BOOMERANG, "Бумеранг");
		translationBuilder.add(ModItems.WOODEN_TEAPOT, "Дерев'яний чайник");
		translationBuilder.add(ModItems.HOT_AIR_BALLOON, "Повітряна куля");

		translationBuilder.add(ModEntities.HOT_AIR_BALLOON, "Повітряна куля");

		translationBuilder.add(ModKeybinds.CATCH.getTranslationKey(), "Спіймати бумеранг");
		translationBuilder.add(ModKeybinds.PHASE.getTranslationKey(), "Фазувати");
		translationBuilder.add(ModKeybinds.CATCH.getCategory(), "trollmod");

		translationBuilder.add(ModItems.ITEMS_GROUP_KEY, "%1$s предмети");
		translationBuilder.add(ModBlocks.BLOCKS_GROUP_KEY, "%1$s блоки");

		translationBuilder.add("death.attack.boomerang", "%1$s був знищений бумерангом");
		translationBuilder.add("screen.trollmod.teapot", "Чайник");
		translationBuilder.add("msg.trollmod.locked", "Перспектива заблокована!");
	}
}
