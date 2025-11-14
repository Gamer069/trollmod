package me.illia.trollmod.effect;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
	public static final Holder<MobEffect> INVERT_CONTROLS = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Util.id("invert_controls"), new InvertControlsEffect());
	public static final Holder<MobEffect> HIGH_GRAVITY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Util.id("high_gravity"), new HighGravityEffect());
	public static final Holder<MobEffect> LOW_GARVITY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Util.id("low_gravity"), new LowGravityEffect());

	public static void init() {
		Trollmod.LOGGER.info("Initializing status effects for mod " + Trollmod.MODID);
	}
}
