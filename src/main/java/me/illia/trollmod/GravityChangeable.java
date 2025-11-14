package me.illia.trollmod;

import me.illia.trollmod.attachment.ModAttachmentTypes;
import me.illia.trollmod.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public interface GravityChangeable {
	default World trollmod$getWorld() {
		return null;
	}

	default Entity trollmod$getEntity() {
		return null;
	}

	default float trollmod$getGravity() {
		if (trollmod$getEntity() instanceof LivingEntity living) {
			if (living.hasStatusEffect(ModEffects.HIGH_GRAVITY.value())) {
				int amp = living.getStatusEffect(ModEffects.HIGH_GRAVITY.value()).getAmplifier() + 1;
				return trollmod$getNormalGravity() * (2f * amp);
			}

			if (living.hasStatusEffect(ModEffects.LOW_GARVITY.value())) {
				int amp = living.getStatusEffect(ModEffects.LOW_GARVITY.value()).getAmplifier() + 1;
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
