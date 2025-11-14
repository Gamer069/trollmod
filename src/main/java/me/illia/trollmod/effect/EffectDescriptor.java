package me.illia.trollmod.effect;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public record EffectDescriptor(Holder<MobEffect> entry, int duration, int amplifier) {}
