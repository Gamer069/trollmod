package me.illia.trollmod.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;

public record EffectDescriptor(RegistryEntry<StatusEffect> entry, int duration, int amplifier) {}
