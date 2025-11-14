package me.illia.trollmod.client;

import me.illia.trollmod.Util;
import me.illia.trollmod.block.GhostBlockEntityRenderer;
import me.illia.trollmod.block.ModBlockEntities;
import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.block.SpikyBlockEntityRenderer;
import me.illia.trollmod.component.BoomerangCatchComponent;
import me.illia.trollmod.component.ModComponents;
import me.illia.trollmod.entity.*;
import me.illia.trollmod.networking.ModNetworking;
import me.illia.trollmod.screen.ModScreenHandlers;
import me.illia.trollmod.screen.TeapotScreen;
import me.illia.trollmod.shader.ModShaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import java.util.Comparator;
import java.util.List;

public class TrollmodClient implements ClientModInitializer {
	public static final ModelLayerLocation BOOMERANG_LAYER = new ModelLayerLocation(
		Util.id("boomerang"), "main"
	);
	public static final ModelLayerLocation HOT_AIR_BALLOON_LAYER = new ModelLayerLocation(
		Util.id("hot_air_balloon"), "main"
	);
	public boolean wasCatchPressedLastTick;
	public boolean wasPhasePressedLastTick;

	private int phaseTick = 0;
	public static final int PHASE_TIME = 40;

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLEHEART_SAPLING, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLEHEART_LEAVES, RenderType.cutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLEHEART_TRAPDOOR, RenderType.cutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLEHEART_DOOR, RenderType.cutoutMipped());
		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getAverageFoliageColor(view, pos) : FoliageColor.getDefaultColor(), ModBlocks.PURPLEHEART_LEAVES);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColor.getDefaultColor(), ModBlocks.PURPLEHEART_LEAVES.asItem());

		EntityRendererRegistry.register(ModEntities.BOOMERANG, BoomerangEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.HOT_AIR_BALLOON, HotAirBalloonEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BOOMERANG_LAYER, BoomerangEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(HOT_AIR_BALLOON_LAYER, HotAirBalloonEntityModel::getTexturedModelData);
		BlockEntityRenderers.register(ModBlockEntities.GHOST_BLOCK_ENTITY_TYPE, GhostBlockEntityRenderer::new);
		BlockEntityRenderers.register(ModBlockEntities.SPIKY_BLOCK_ENTITY_TYPE, SpikyBlockEntityRenderer::new);

		MenuScreens.register(ModScreenHandlers.TEAPOT_SCREEN_HANDLER, TeapotScreen::new);
		ModKeybinds.init();
		ModShaders.init();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			handleBoomerangCatch(client);
			handlePhase(client);
		});
	}

	private void handlePhase(Minecraft client) {
		if (client.player == null) return;

		// Key pressed -> start phasing
		if (ModKeybinds.PHASE.isDown() && !wasPhasePressedLastTick) {
			wasPhasePressedLastTick = true;

			if (phaseTick == 0) {
				phaseTick = PHASE_TIME; // set countdown to 40 ticks
				client.player.getComponent(ModComponents.PHASING_COMPONENT_KEY).setPhasing(true);

				FriendlyByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(true);
				buf.writeInt(phaseTick);
				ClientPlayNetworking.send(ModNetworking.PHASE, buf);
			}
		} else if (!ModKeybinds.PHASE.isDown()) {
			wasPhasePressedLastTick = false;
		}

		// Countdown
		if (phaseTick > 0) {
			phaseTick--;

			client.player.getComponent(ModComponents.PHASING_COMPONENT_KEY).setTicksLeft(phaseTick);

			if (phaseTick == 0) {
				client.player.getComponent(ModComponents.PHASING_COMPONENT_KEY).setPhasing(false);

				FriendlyByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(false);
				buf.writeInt(phaseTick);

				ClientPlayNetworking.send(ModNetworking.PHASE, buf);
			}
		}
	}

	private void handleBoomerangCatch(Minecraft client) {
		if (client.player == null) return;

		Vec3 pos = client.player.position();
		List<BoomerangEntity> boomerangs = client.player.level().getEntitiesOfClass(
			BoomerangEntity.class,
			new AABB(pos.subtract(5, 5, 5), pos.add(5, 5, 5)),
			e -> e.distanceToSqr(pos) < 25
		);

		BoomerangEntity nearest = boomerangs.stream()
			.min(Comparator.comparingDouble(e -> e.distanceToSqr(pos)))
			.orElse(null);

		BoomerangCatchComponent comp = client.player.getComponent(ModComponents.BOOMERANG_CATCH_COMPONENT_KEY);
		comp.set(nearest != null);
		comp.setEntity(nearest == null ? 0 : nearest.getId());

		boolean currentlyPressed = ModKeybinds.CATCH.isDown();

		if (currentlyPressed && !wasCatchPressedLastTick && comp.isWithin()) {
			wasCatchPressedLastTick = true;

			FriendlyByteBuf buf = PacketByteBufs.create();
			buf.writeVarInt(nearest.getId());
			ClientPlayNetworking.send(ModNetworking.CATCH_BOOMERANG, buf);
		} else if (!currentlyPressed) {
			wasCatchPressedLastTick = false;
		}
	}
}
