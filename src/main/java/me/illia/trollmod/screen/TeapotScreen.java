package me.illia.trollmod.screen;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TeapotScreen extends AbstractContainerScreen<TeapotScreenHandler> {
	public static final ResourceLocation TEXTURE = Util.id("textures/gui/teapot.png");

	public TeapotScreen(TeapotScreenHandler handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
	}

	@Override
	protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;

		context.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
	}
}
