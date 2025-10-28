package me.illia.trollmod.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.component.ModComponents;
import me.illia.trollmod.component.PhasingComponent;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Entity.class)
public abstract class PhaseMixin implements ComponentProvider {
	@Accessor("boundingBox")
	public abstract Box trollmod$getBoundingBox();

	@Accessor("world")
	public abstract World trollmod$getWorld();

	@WrapMethod(
		method = "adjustMovementForCollisions*"
	)
	protected Vec3d adjustMovementForCollisions(Vec3d movement, Operation<Vec3d> orig) {
		return orig.call(movement);
	}

	@WrapMethod(
		method = "move*"
	)
	protected void move(MovementType movementType, Vec3d movement, Operation<Void> orig) {
		orig.call(movementType, movement);
	}

	@Mixin(ServerPlayerEntity.class)
	private static abstract class ServerPlayerEntityPhaseMixin extends PhaseMixin {
		@Override
		protected Vec3d adjustMovementForCollisions(Vec3d movement, Operation<Vec3d> orig) {
			PhasingComponent phase = getComponent(ModComponents.PHASING_COMPONENT_KEY);

			if (phase.isPhasing()) {
				Vec3d vanilla = orig.call(movement);
				Trollmod.LOGGER.warn(new Vec3d(movement.x, vanilla.y, movement.z).toString());

				((ServerPlayerEntity)(Object)this).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket((ServerPlayerEntity)(Object)this));

				return new Vec3d(movement.x, vanilla.y, movement.z);
			}

			return orig.call(movement);
		}
	}

	@Mixin(PlayerEntity.class)
	private static abstract class PlayerEntityPhaseMixin extends PhaseMixin {
		@WrapOperation(
			method = "tick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z")
		)
		protected boolean tick(PlayerEntity instance, Operation<Boolean> orig) {
			PhasingComponent phase = getComponent(ModComponents.PHASING_COMPONENT_KEY);
			((PlayerEntity) (Object) this).noClip = phase.isPhasing() || instance.isSpectator();

			return getComponent(ModComponents.PHASING_COMPONENT_KEY).isPhasing() || orig.call(instance);
		}

		@Override
		protected void move(MovementType movementType, Vec3d movement, Operation<Void> orig) {
			PhasingComponent phase = getComponent(ModComponents.PHASING_COMPONENT_KEY);
			PlayerEntity inst = (PlayerEntity)(Object)this;

			if (phase.isPhasing() && !inst.isCreative()) {
				inst.setPosition(inst.getX() + movement.x, inst.getY(), inst.getZ() + movement.z);
				return;
			}
			super.move(movementType, movement, orig);
		}
	}
}
