package me.illia.trollmod.mixin;

import me.illia.trollmod.attachment.ModAttachmentTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	private Level level;

	@Inject(method = "baseTick", at = @At(value = "HEAD"), cancellable = true)
	private void tick(CallbackInfo ci) {
		Entity thisEntity = (Entity)(Object)this;
		UUID uuid = thisEntity.getAttachedOrElse(ModAttachmentTypes.IS_CONTROLLED, null);
		if (uuid == null) return;

		Player player = level.getPlayerByUUID(uuid);
		if (player == null) {
			thisEntity.setAttached(ModAttachmentTypes.IS_CONTROLLED, null);
			thisEntity.setGlowingTag(false);
			thisEntity.setNoGravity(false);

			if (thisEntity instanceof ItemEntity itemEntity) {
				itemEntity.setDefaultPickUpDelay();
			}

			return;
		}

		Vec3 lookDir = player.getViewVector(1.0f);
		Vec3 pos = player.getEyePosition(1.0f).add(lookDir.scale(3));

		thisEntity.setNoGravity(true);

		if (thisEntity instanceof ItemEntity itemEntity) {
			itemEntity.setNeverPickUp();
		}

		thisEntity.setPos(pos);
		thisEntity.setDeltaMovement(Vec3.ZERO);
		thisEntity.hasImpulse = true;
	}
}
