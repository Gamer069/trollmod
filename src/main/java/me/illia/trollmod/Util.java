package me.illia.trollmod;

import net.minecraft.util.Identifier;

public class Util {
	public static Identifier id(String path) {
		return new Identifier(Trollmod.MODID, path);
	}
}
