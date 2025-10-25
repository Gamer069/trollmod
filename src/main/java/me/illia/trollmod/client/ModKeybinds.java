package me.illia.trollmod.client;

import me.illia.trollmod.Trollmod;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
	public static KeyBinding CATCH = KeyBindingHelper.registerKeyBinding(new KeyBinding(
		"key.trollmod.catch",
		InputUtil.Type.KEYSYM,
		GLFW.GLFW_KEY_R,
		"category.trollmod.trollmod"
	));

	public static KeyBinding PHASE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
		"key.trollmod.phase",
		InputUtil.Type.KEYSYM,
		GLFW.GLFW_KEY_T,
		"category.trollmod.trollmod"
	));

	public static void init() {
		Trollmod.LOGGER.info("Initializing keybinds for mod " + Trollmod.MODID);
	}
}
