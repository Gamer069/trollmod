package me.illia.trollmod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.client.TrollmodClient;
import me.illia.trollmod.component.BoomerangCatchComponent;
import me.illia.trollmod.component.ModComponents;
import me.illia.trollmod.component.PhasingComponent;
import me.illia.trollmod.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin {
	@Shadow private int scaledWidth;
	@Shadow private int scaledHeight;

	@Shadow @Final private PlayerListHud playerListHud;

	@Shadow @Final private MinecraftClient client;

	@Inject(method = "renderHotbar", at = @At("TAIL"))
	public void renderHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
		int x = scaledWidth / 2 - 90 + 9 * 20 + 2;
		int y = scaledHeight - 16 - 3;
		ItemStack stack = new ItemStack(ModItems.BOOMERANG);

		context.getMatrices().push();

		RenderSystem.setShaderColor(0.2f, 0.2f, 0.2f, 1.0f);

		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0) continue;
				context.getMatrices().push();
				context.getMatrices().translate(dx, dy, 0);
				context.drawItem(stack, x, y);
				context.getMatrices().pop();
			}
		}

		context.getMatrices().push();
		context.getMatrices().translate(0, 0, 0);
		context.drawItem(stack, x, y);
		context.getMatrices().pop();

		context.getMatrices().pop();

		RenderSystem.setShaderColor(1, 1, 1, 1);

		BoomerangCatchComponent comp = client.player.getComponent(ModComponents.BOOMERANG_CATCH_COMPONENT_KEY);
		if (comp.isWithin()) {
			context.drawItem(stack, x, y);
		}

		PhasingComponent phasingComp = client.player.getComponent(ModComponents.PHASING_COMPONENT_KEY);
		int ticksLeft = phasingComp.getTicksLeft();
		int maxTicks = TrollmodClient.PHASE_TIME; // max duration in ticks
		if (ticksLeft > 0) {
			int barWidth = 100;
			int barHeight = 6;
			int barY = (client.player.isCreative() || client.player.isSpectator()) ? y - 20 : y - 40;
			int barX = x - 100;
			// Calculate progress (0.0 to 1.0)
			float progress = ticksLeft / (float)maxTicks;
			int fillWidth = (int)(progress * barWidth);

			// Draw background
			context.fill(barX, barY, barX + barWidth, barY + barHeight, 0xFF555555); // dark gray

			// Draw filled portion with segments
			if (fillWidth > 0) {
				// Calculate threshold positions
				int greenThreshold = (int)(0.66f * barWidth);  // Position where green ends
				int yellowThreshold = (int)(0.33f * barWidth); // Position where yellow ends

				// Draw green section (66% - 100%)
				if (fillWidth > greenThreshold) {
					context.fill(barX + greenThreshold, barY, barX + fillWidth, barY + barHeight, 0xFF00FF00);
				}

				// Draw yellow section (33% - 66%)
				if (fillWidth > yellowThreshold) {
					int yellowStart = Math.max(yellowThreshold, 0);
					int yellowEnd = Math.min(fillWidth, greenThreshold);
					if (yellowEnd > yellowStart) {
						context.fill(barX + yellowStart, barY, barX + yellowEnd, barY + barHeight, 0xFFFFFF00);
					}
				}

				// Draw red section (0% - 33%)
				if (fillWidth > 0) {
					int redEnd = Math.min(fillWidth, yellowThreshold);
					if (redEnd > 0) {
						context.fill(barX, barY, barX + redEnd, barY + barHeight, 0xFFFF0000);
					}
				}
			}

			// Draw vertical divider lines at color thresholds
			int dividerColor = 0xFF000000; // Black dividers
			int greenDividerX = barX + (int)(0.66f * barWidth);
			int yellowDividerX = barX + (int)(0.33f * barWidth);

			// Draw divider between yellow and green (at 66%)
			context.drawVerticalLine(greenDividerX, barY, barY + barHeight, dividerColor);

			// Draw divider between red and yellow (at 33%)
			context.drawVerticalLine(yellowDividerX, barY, barY + barHeight, dividerColor);

			// Draw border
			context.drawBorder(barX, barY, barWidth, barHeight, 0xFFFFFFFF);
		}
	}
}
