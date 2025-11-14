package me.illia.trollmod.mixin;

import me.illia.trollmod.attachment.ModAttachmentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	private World world;

	@Inject(method = "baseTick", at = @At(value = "HEAD"), cancellable = true)
	private void tick(CallbackInfo ci) {
		Entity thisEntity = (Entity)(Object)this;
		UUID uuid = thisEntity.getAttachedOrElse(ModAttachmentTypes.IS_CONTROLLED, null);
		if (uuid == null) return;

		PlayerEntity player = world.getPlayerByUuid(uuid);
		if (player == null) {
			thisEntity.setAttached(ModAttachmentTypes.IS_CONTROLLED, null);
			thisEntity.setGlowing(false);
			thisEntity.setNoGravity(false);

			if (thisEntity instanceof ItemEntity itemEntity) {
				itemEntity.setToDefaultPickupDelay();
			}

			return;
		}

		Vec3d lookDir = player.getRotationVec(1.0f);
		Vec3d pos = player.getCameraPosVec(1.0f).add(lookDir.multiply(3));

		thisEntity.setNoGravity(true);

		if (thisEntity instanceof ItemEntity itemEntity) {
			itemEntity.setPickupDelayInfinite();
		}

		thisEntity.setPosition(pos);
		thisEntity.setVelocity(Vec3d.ZERO);
		thisEntity.velocityDirty = true;
	}
}
