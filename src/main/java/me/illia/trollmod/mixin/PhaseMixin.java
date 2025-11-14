package me.illia.trollmod.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.component.ModComponents;
import me.illia.trollmod.component.PhasingComponent;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Entity.class)
public abstract class PhaseMixin implements ComponentProvider {
	@Accessor("bb")
	public abstract AABB trollmod$getBoundingBox();

	@Accessor("level")
	public abstract Level trollmod$getWorld();

	@WrapMethod(
		method = "adjustMovementForCollisions*"
	)
	protected Vec3 adjustMovementForCollisions(Vec3 movement, Operation<Vec3> orig) {
		return orig.call(movement);
	}

	@WrapMethod(
		method = "move*"
	)
	protected void move(MoverType movementType, Vec3 movement, Operation<Void> orig) {
		orig.call(movementType, movement);
	}

	@Mixin(ServerPlayer.class)
	private static abstract class ServerPlayerEntityPhaseMixin extends PhaseMixin {
		@Override
		protected Vec3 adjustMovementForCollisions(Vec3 movement, Operation<Vec3> orig) {
			PhasingComponent phase = getComponent(ModComponents.PHASING_COMPONENT_KEY);

			if (phase.isPhasing()) {
				Vec3 vanilla = orig.call(movement);
				Trollmod.LOGGER.warn(new Vec3(movement.x, vanilla.y, movement.z).toString());

				((ServerPlayer)(Object)this).connection.send(new ClientboundSetEntityMotionPacket((ServerPlayer)(Object)this));

				return new Vec3(movement.x, vanilla.y, movement.z);
			}

			return orig.call(movement);
		}
	}

	@Mixin(Player.class)
	private static abstract class PlayerEntityPhaseMixin extends PhaseMixin {
		@WrapOperation(
			method = "tick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z")
		)
		protected boolean tick(Player instance, Operation<Boolean> orig) {
			PhasingComponent phase = getComponent(ModComponents.PHASING_COMPONENT_KEY);
			((Player) (Object) this).noPhysics = phase.isPhasing() || instance.isSpectator();

			return getComponent(ModComponents.PHASING_COMPONENT_KEY).isPhasing() || orig.call(instance);
		}

		@Override
		protected void move(MoverType movementType, Vec3 movement, Operation<Void> orig) {
			PhasingComponent phase = getComponent(ModComponents.PHASING_COMPONENT_KEY);
			Player inst = (Player)(Object)this;

			if (phase.isPhasing() && !inst.isCreative()) {
				inst.setPos(inst.getX() + movement.x, inst.getY(), inst.getZ() + movement.z);
				return;
			}
			super.move(movementType, movement, orig);
		}
	}
}
