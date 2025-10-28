package me.illia.trollmod.shader;

import me.illia.trollmod.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class ModShaders {
	public static PostEffectProcessor VIGNETTE_EFFECT;

	public static void init() {
		WorldRenderEvents.AFTER_TRANSLUCENT.register(ctx -> {
			MinecraftClient client = MinecraftClient.getInstance();
			if (client.world == null || client.player == null) return;

			if (VIGNETTE_EFFECT == null) {
				try {
					VIGNETTE_EFFECT = new PostEffectProcessor(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), Util.id("shaders/post/vignette.json"));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			if (VIGNETTE_EFFECT != null) {
				VIGNETTE_EFFECT.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());

				VIGNETTE_EFFECT.render(ctx.tickDelta());

				client.getFramebuffer().beginWrite(false);
			}
		});
	}
}
