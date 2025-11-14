package me.illia.trollmod.mixin;

import me.illia.trollmod.GravityChangeable;
import me.illia.trollmod.entity.GravityChangingEntity;
import net.minecraft.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({FallingBlockEntity.class, PrimedTnt.class, ItemEntity.class, ExperienceOrb.class, AbstractMinecart.class, LlamaSpit.class})
public class GravityTickEntitiesMixin implements GravityChangeable {
	@Override
	public float trollmod$getNormalGravity() {
		if (this instanceof GravityChangingEntity gce) {
			return (float)gce.gravity();
		}

		float normalGravity = -0.04f;
		if ((Entity)(Object)this instanceof ExperienceOrb) {
			normalGravity = -0.03f;
		} else if ((Entity)(Object)this instanceof LlamaSpit) {
			normalGravity = -0.06f;
		} else if ((Entity)(Object)this instanceof AbstractMinecart ame) {
			normalGravity = ame.isInWater() ? -0.005f : -0.04f;
		}

		return ((Entity)(Object)this).isNoGravity() ? 0.0f : normalGravity;
	}

	@Override
	public Entity trollmod$getEntity() {
		return (Entity)(Object)this;
	}

	@Override
	public Level trollmod$getWorld() {
		return ((Entity)(Object)this).level();
	}

	@ModifyArg(method = "tick", at = @At(value="INVOKE", target="Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;"), index = 1)
	private double gravity(double orig) {
		return trollmod$getGravity();
	}
}
