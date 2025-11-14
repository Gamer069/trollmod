package me.illia.trollmod.datagen.providers.lang;

import me.illia.trollmod.Util;
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
	public void generateTranslations(TranslationBuilder t) {
		Util.t(t, ModBlocks.PURPLEHEART_WOOD, "Амарантнова деревина");
		Util.t(t, ModBlocks.PURPLEHEART_LOG, "Амарантнова колода");
		Util.t(t, ModBlocks.PURPLEHEART_PLANKS, "Амарантнові дошки");
		Util.t(t, ModBlocks.STRIPPED_PURPLEHEART_WOOD, "Обтесана амарантнова деревина");
		Util.t(t, ModBlocks.STRIPPED_PURPLEHEART_LOG, "Обтесана амарантнова колода");
		Util.t(t, ModBlocks.PURPLEHEART_LEAVES, "Амарантнове листя");
		Util.t(t, ModBlocks.PURPLEHEART_SAPLING, "Паросток амаранта");

		Util.t(t, ModItems.BOOMERANG, "Бумеранг");
		Util.t(t, ModItems.WOODEN_TEAPOT, "Дерев'яний чайник");
		Util.t(t, ModItems.HOT_AIR_BALLOON, "Повітряна куля");

		Util.t(t, ModEntities.HOT_AIR_BALLOON, "Повітряна куля");

		Util.t(t, ModKeybinds.CATCH.getName(), "Спіймати бумеранг");
		Util.t(t, ModKeybinds.PHASE.getName(), "Фазувати");
		Util.t(t, ModKeybinds.CATCH.getCategory(), "trollmod");

		Util.t(t, ModItems.ITEMS_GROUP_KEY, "%1$s предмети");
		Util.t(t, ModBlocks.BLOCKS_GROUP_KEY, "%1$s блоки");

		Util.t(t, "message.trollmod.sound_set", "Звук %1$s поставлен");

		Util.t(t, "death.attack.boomerang", "%1$s був знищений бумерангом");
		Util.t(t, "screen.trollmod.teapot", "Чайник");
		Util.t(t, "msg.trollmod.locked", "Перспектива заблокована!");
	}
}
