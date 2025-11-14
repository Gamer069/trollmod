package me.illia.trollmod.screen;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class ModScreenHandlers {
	public static MenuType<TeapotScreenHandler> TEAPOT_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, Util.id("teapot_screen_handler"), new MenuType<>(new MenuType.MenuSupplier<TeapotScreenHandler>() {
		@Override
		public TeapotScreenHandler create(int syncId, Inventory playerInventory) {
			return new TeapotScreenHandler(syncId, playerInventory);
		}
	}, FeatureFlagSet.of()));

	public static void init() {
	}
}
