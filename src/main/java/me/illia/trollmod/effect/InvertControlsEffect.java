package me.illia.trollmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class InvertControlsEffect extends StatusEffect {
	public InvertControlsEffect() {
		super(StatusEffectCategory.HARMFUL, 0xC750D4);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}
}
