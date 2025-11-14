package me.illia.trollmod.mixin;

import me.illia.trollmod.GravityChangeable;
import me.illia.trollmod.entity.GravityChangingEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({FallingBlockEntity.class, TntEntity.class, ItemEntity.class, ExperienceOrbEntity.class, AbstractMinecartEntity.class, LlamaSpitEntity.class})
public class GravityTickEntitiesMixin implements GravityChangeable {
	@Override
	public float trollmod$getNormalGravity() {
		if (this instanceof GravityChangingEntity gce) {
			return (float)gce.gravity();
		}

		float normalGravity = -0.04f;
		if ((Entity)(Object)this instanceof ExperienceOrbEntity) {
			normalGravity = -0.03f;
		} else if ((Entity)(Object)this instanceof LlamaSpitEntity) {
			normalGravity = -0.06f;
		} else if ((Entity)(Object)this instanceof AbstractMinecartEntity ame) {
			normalGravity = ame.isTouchingWater() ? -0.005f : -0.04f;
		}

		return ((Entity)(Object)this).hasNoGravity() ? 0.0f : normalGravity;
	}

	@Override
	public Entity trollmod$getEntity() {
		return (Entity)(Object)this;
	}

	@Override
	public World trollmod$getWorld() {
		return ((Entity)(Object)this).getWorld();
	}

	@ModifyArg(method = "tick", at = @At(value="INVOKE", target="Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"), index = 1)
	private double gravity(double orig) {
		return trollmod$getGravity();
	}
}
