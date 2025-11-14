package me.illia.trollmod;

import me.illia.trollmod.attachment.ModAttachmentTypes;
import me.illia.trollmod.effect.ModEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public interface GravityChangeable {
	default Level trollmod$getWorld() {
		return null;
	}

	default Entity trollmod$getEntity() {
		return null;
	}

	default float trollmod$getGravity() {
		if (trollmod$getEntity() instanceof LivingEntity living) {
			if (living.hasEffect(ModEffects.HIGH_GRAVITY.value())) {
				int amp = living.getEffect(ModEffects.HIGH_GRAVITY.value()).getAmplifier() + 1;
				return trollmod$getNormalGravity() * (2f * amp);
			}

			if (living.hasEffect(ModEffects.LOW_GARVITY.value())) {
				int amp = living.getEffect(ModEffects.LOW_GARVITY.value()).getAmplifier() + 1;
				return trollmod$getNormalGravity() * (0.5f / amp);
			}

		}

		if (trollmod$getEntity().getAttachedOrElse(ModAttachmentTypes.IS_CONTROLLED, null) != null) {
			return 0;
		}

		return trollmod$getNormalGravity();
	}

	default float trollmod$getNormalGravity() {
		return 0;
	}
}
