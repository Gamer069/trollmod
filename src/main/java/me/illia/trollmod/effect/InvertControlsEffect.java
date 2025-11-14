package me.illia.trollmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class InvertControlsEffect extends MobEffect {
	public InvertControlsEffect() {
		super(MobEffectCategory.HARMFUL, 0xC750D4);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
