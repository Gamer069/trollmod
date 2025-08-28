package me.illia.trollmod.client;

import me.illia.trollmod.entity.ModEntities;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.entity.BoomerangEntityModel;
import me.illia.trollmod.entity.BoomerangEntityRenderer;
import me.illia.trollmod.screen.ModScreenHandlers;
import me.illia.trollmod.screen.TeapotScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.util.Identifier;

public class TrollmodClient implements ClientModInitializer {
	public static final EntityModelLayer BOOMERANG_LAYER = new EntityModelLayer(
		new Identifier(Trollmod.MODID, "boomerang"), "main"
	);

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.BOOMERANG, BoomerangEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BOOMERANG_LAYER, BoomerangEntityModel::getTexturedModelData);
		HandledScreens.register(ModScreenHandlers.TEAPOT_SCREEN_HANDLER, TeapotScreen::new);
	}
}
