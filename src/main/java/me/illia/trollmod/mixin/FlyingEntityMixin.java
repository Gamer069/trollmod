package me.illia.trollmod.mixin;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.illia.trollmod.entity.GravityChangingEntity;
import net.minecraft.entity.mob.FlyingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlyingEntity.class)
public class FlyingEntityMixin {
	@Expression("0.16277137 / ?")
	@ModifyExpressionValue(method = "travel", at = @At("MIXINEXTRAS:EXPRESSION"))
	private float gravity(float original) {
		if (this instanceof GravityChangingEntity gce) {
			return (float)(gce.gravity() / 0.02f);
		}
		return original;
	}
}
