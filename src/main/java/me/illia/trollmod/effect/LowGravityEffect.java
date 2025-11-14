package me.illia.trollmod.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class LowGravityEffect extends StatusEffect {
	protected LowGravityEffect() {
		super(StatusEffectCategory.HARMFUL, 0x7FFFD4);
	}
}
