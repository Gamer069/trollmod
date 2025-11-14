package me.illia.trollmod.effect;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffects {
	public static final RegistryEntry<StatusEffect> INVERT_CONTROLS = Registry.registerReference(Registries.STATUS_EFFECT, Util.id("invert_controls"), new InvertControlsEffect());
	public static final RegistryEntry<StatusEffect> HIGH_GRAVITY = Registry.registerReference(Registries.STATUS_EFFECT, Util.id("high_gravity"), new HighGravityEffect());
	public static final RegistryEntry<StatusEffect> LOW_GARVITY = Registry.registerReference(Registries.STATUS_EFFECT, Util.id("low_gravity"), new LowGravityEffect());

	public static void init() {
		Trollmod.LOGGER.info("Initializing status effects for mod " + Trollmod.MODID);
	}
}
