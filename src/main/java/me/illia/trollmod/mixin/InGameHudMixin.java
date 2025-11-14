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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin {
	@Shadow private int screenWidth;
	@Shadow private int screenHeight;

	@Shadow @Final private PlayerTabOverlay tabList;

	@Shadow @Final private Minecraft minecraft;

	@Inject(method = "renderHotbar", at = @At("TAIL"))
	public void renderHotbar(float tickDelta, GuiGraphics context, CallbackInfo ci) {
		int x = screenWidth / 2 - 90 + 9 * 20 + 2;
		int y = screenHeight - 16 - 3;
		ItemStack stack = new ItemStack(ModItems.BOOMERANG);

		context.pose().pushPose();

		RenderSystem.setShaderColor(0.2f, 0.2f, 0.2f, 1.0f);

		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0) continue;
				context.pose().pushPose();
				context.pose().translate(dx, dy, 0);
				context.renderItem(stack, x, y);
				context.pose().popPose();
			}
		}

		context.pose().pushPose();
		context.pose().translate(0, 0, 0);
		context.renderItem(stack, x, y);
		context.pose().popPose();

		context.pose().popPose();

		RenderSystem.setShaderColor(1, 1, 1, 1);

		BoomerangCatchComponent comp = minecraft.player.getComponent(ModComponents.BOOMERANG_CATCH_COMPONENT_KEY);
		if (comp.isWithin()) {
			context.renderItem(stack, x, y);
		}

		PhasingComponent phasingComp = minecraft.player.getComponent(ModComponents.PHASING_COMPONENT_KEY);
		int ticksLeft = phasingComp.getTicksLeft();
		int maxTicks = TrollmodClient.PHASE_TIME; // max duration in ticks
		if (ticksLeft > 0) {
			int barWidth = 100;
			int barHeight = 6;
			int barY = (minecraft.player.isCreative() || minecraft.player.isSpectator()) ? y - 20 : y - 40;
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
			context.vLine(greenDividerX, barY, barY + barHeight, dividerColor);

			// Draw divider between red and yellow (at 33%)
			context.vLine(yellowDividerX, barY, barY + barHeight, dividerColor);

			// Draw border
			context.renderOutline(barX, barY, barWidth, barHeight, 0xFFFFFFFF);
		}
	}
}
