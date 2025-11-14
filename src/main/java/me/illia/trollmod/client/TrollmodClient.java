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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

import java.util.Comparator;
import java.util.List;

public class TrollmodClient implements ClientModInitializer {
	public static final EntityModelLayer BOOMERANG_LAYER = new EntityModelLayer(
		Util.id("boomerang"), "main"
	);
	public static final EntityModelLayer HOT_AIR_BALLOON_LAYER = new EntityModelLayer(
		Util.id("hot_air_balloon"), "main"
	);
	public boolean wasCatchPressedLastTick;
	public boolean wasPhasePressedLastTick;

	private int phaseTick = 0;
	public static final int PHASE_TIME = 40;

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLEHEART_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLEHEART_LEAVES, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLEHEART_TRAPDOOR, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLEHEART_DOOR, RenderLayer.getCutoutMipped());
		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getFoliageColor(view, pos) : FoliageColors.getDefaultColor(), ModBlocks.PURPLEHEART_LEAVES);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), ModBlocks.PURPLEHEART_LEAVES.asItem());

		EntityRendererRegistry.register(ModEntities.BOOMERANG, BoomerangEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.HOT_AIR_BALLOON, HotAirBalloonEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(BOOMERANG_LAYER, BoomerangEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(HOT_AIR_BALLOON_LAYER, HotAirBalloonEntityModel::getTexturedModelData);
		BlockEntityRendererFactories.register(ModBlockEntities.GHOST_BLOCK_ENTITY_TYPE, GhostBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(ModBlockEntities.SPIKY_BLOCK_ENTITY_TYPE, SpikyBlockEntityRenderer::new);

		HandledScreens.register(ModScreenHandlers.TEAPOT_SCREEN_HANDLER, TeapotScreen::new);
		ModKeybinds.init();
		ModShaders.init();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			handleBoomerangCatch(client);
			handlePhase(client);
		});
	}

	private void handlePhase(MinecraftClient client) {
		if (client.player == null) return;

		// Key pressed -> start phasing
		if (ModKeybinds.PHASE.isPressed() && !wasPhasePressedLastTick) {
			wasPhasePressedLastTick = true;

			if (phaseTick == 0) {
				phaseTick = PHASE_TIME; // set countdown to 40 ticks
				client.player.getComponent(ModComponents.PHASING_COMPONENT_KEY).setPhasing(true);

				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(true);
				buf.writeInt(phaseTick);
				ClientPlayNetworking.send(ModNetworking.PHASE, buf);
			}
		} else if (!ModKeybinds.PHASE.isPressed()) {
			wasPhasePressedLastTick = false;
		}

		// Countdown
		if (phaseTick > 0) {
			phaseTick--;

			client.player.getComponent(ModComponents.PHASING_COMPONENT_KEY).setTicksLeft(phaseTick);

			if (phaseTick == 0) {
				client.player.getComponent(ModComponents.PHASING_COMPONENT_KEY).setPhasing(false);

				PacketByteBuf buf = PacketByteBufs.create();
				buf.writeBoolean(false);
				buf.writeInt(phaseTick);

				ClientPlayNetworking.send(ModNetworking.PHASE, buf);
			}
		}
	}

	private void handleBoomerangCatch(MinecraftClient client) {
		if (client.player == null) return;

		Vec3d pos = client.player.getPos();
		List<BoomerangEntity> boomerangs = client.player.getWorld().getEntitiesByClass(
			BoomerangEntity.class,
			new Box(pos.subtract(5, 5, 5), pos.add(5, 5, 5)),
			e -> e.squaredDistanceTo(pos) < 25
		);

		BoomerangEntity nearest = boomerangs.stream()
			.min(Comparator.comparingDouble(e -> e.squaredDistanceTo(pos)))
			.orElse(null);

		BoomerangCatchComponent comp = client.player.getComponent(ModComponents.BOOMERANG_CATCH_COMPONENT_KEY);
		comp.set(nearest != null);
		comp.setEntity(nearest == null ? 0 : nearest.getId());

		boolean currentlyPressed = ModKeybinds.CATCH.isPressed();

		if (currentlyPressed && !wasCatchPressedLastTick && comp.isWithin()) {
			wasCatchPressedLastTick = true;

			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeVarInt(nearest.getId());
			ClientPlayNetworking.send(ModNetworking.CATCH_BOOMERANG, buf);
		} else if (!currentlyPressed) {
			wasCatchPressedLastTick = false;
		}
	}
}
