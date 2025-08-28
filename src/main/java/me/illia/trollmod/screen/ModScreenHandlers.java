package me.illia.trollmod.screen;

import me.illia.trollmod.Trollmod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
	public static ScreenHandlerType<TeapotScreenHandler> TEAPOT_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Trollmod.MODID, "teapot_screen_handler"), new ScreenHandlerType<>(new ScreenHandlerType.Factory<TeapotScreenHandler>() {
		@Override
		public TeapotScreenHandler create(int syncId, PlayerInventory playerInventory) {
			return new TeapotScreenHandler(syncId, playerInventory);
		}
	}, FeatureSet.empty()));

	public static void init() {
	}
}
