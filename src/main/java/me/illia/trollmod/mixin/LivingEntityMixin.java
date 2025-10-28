package me.illia.trollmod.mixin;

import me.illia.trollmod.entity.GravityChangingEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08))
	private double gravity(double orig) {
		if (this instanceof GravityChangingEntity gce) {
			return gce.gravity();
		} else {
			return orig;
		}
	}
}
