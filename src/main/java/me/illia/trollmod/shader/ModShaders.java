package me.illia.trollmod.shader;

import me.illia.trollmod.Util;
import me.illia.trollmod.effect.ModEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import java.io.IOException;

@Environment(EnvType.CLIENT)
public class ModShaders {
	public static PostChain VIGNETTE_EFFECT;

	public static void init() {
		WorldRenderEvents.AFTER_TRANSLUCENT.register(ctx -> {
			Minecraft client = Minecraft.getInstance();
			if (client.level == null || client.player == null) return;

			if (VIGNETTE_EFFECT == null) {
				try {
					VIGNETTE_EFFECT = new PostChain(client.getTextureManager(), client.getResourceManager(), client.getMainRenderTarget(), Util.id("shaders/post/vignette.json"));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			if (VIGNETTE_EFFECT != null && client.player.hasEffect(ModEffects.INVERT_CONTROLS.value())) {
				VIGNETTE_EFFECT.resize(client.getWindow().getWidth(), client.getWindow().getHeight());

				VIGNETTE_EFFECT.process(ctx.tickDelta());

				client.getMainRenderTarget().bindWrite(false);
			}
		});
	}
}
