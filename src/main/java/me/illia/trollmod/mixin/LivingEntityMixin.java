package me.illia.trollmod.mixin;

import me.illia.trollmod.GravityChangeable;
import me.illia.trollmod.attachment.ModAttachmentTypes;
import me.illia.trollmod.entity.GravityChangingEntity;
import me.illia.trollmod.event.ModEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements GravityChangeable {
	@Override
	public float trollmod$getNormalGravity() {
		if (this instanceof GravityChangingEntity gce) {
			return (float)gce.gravity();
		}
		return ((LivingEntity)(Object)this).hasNoGravity() ? 0.0f : 0.08F;
	}

	@Override
	public LivingEntity trollmod$getEntity() {
		return (LivingEntity)(Object)this;
	}

	@Override
	public World trollmod$getWorld() {
		return ((LivingEntity)(Object)this).getWorld();
	}

	@ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08))
	private double gravity(double orig) {
		return trollmod$getGravity();
	}

	@Inject(method = "damage", at = @At(value = "HEAD"))
	private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		Entity thisEntity = (Entity)(Object)this;

		if (thisEntity.getAttachedOrElse(ModAttachmentTypes.IS_CONTROLLED, null) != null) {
			thisEntity.setAttached(ModAttachmentTypes.IS_CONTROLLED, null);
			thisEntity.setGlowing(false);
			thisEntity.setNoGravity(false);
		}
	}

	@Inject(method = "damage", at = @At(value = "TAIL"))
	private void afterDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity thisEntity = (LivingEntity)(Object)this;
		ModEvents.afterDamage(thisEntity, source, amount);
	}
}
