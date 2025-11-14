package me.illia.trollmod.client;

import com.mojang.blaze3d.platform.InputConstants;
import me.illia.trollmod.Trollmod;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
	public static KeyMapping CATCH = KeyBindingHelper.registerKeyBinding(new KeyMapping(
		"key.trollmod.catch",
		InputConstants.Type.KEYSYM,
		GLFW.GLFW_KEY_R,
		"category.trollmod.trollmod"
	));

	public static KeyMapping PHASE = KeyBindingHelper.registerKeyBinding(new KeyMapping(
		"key.trollmod.phase",
		InputConstants.Type.KEYSYM,
		GLFW.GLFW_KEY_T,
		"category.trollmod.trollmod"
	));

	public static void init() {
		Trollmod.LOGGER.info("Initializing keybinds for mod " + Trollmod.MODID);
	}
}
