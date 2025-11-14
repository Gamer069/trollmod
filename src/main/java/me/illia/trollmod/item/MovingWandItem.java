package me.illia.trollmod.item;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.attachment.ModAttachmentTypes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class MovingWandItem extends Item {
	public Entity controlledEntity;

	public MovingWandItem(FabricItemSettings fabricItemSettings) {
		super(fabricItemSettings);
	}

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		double reach = 3.0;
		Vec3d eyePos = user.getCameraPosVec(1.0f);
		Vec3d look = user.getRotationVec(1.0f);
		Vec3d targetPos = eyePos.add(look.multiply(reach));

		Box box = user.getBoundingBox().stretch(look.multiply(reach)).expand(1.0);

		EntityHitResult entityHit = ProjectileUtil.raycast(
			user,
			eyePos,
			targetPos,
			box,
			e -> !e.isSpectator() && e.isAlive(),
			reach * reach
		);

		if (entityHit != null) {
			Entity entity = entityHit.getEntity();

			UUID controlledBy = entity.getAttachedOrElse(ModAttachmentTypes.IS_CONTROLLED, null);
			controlledEntity = controlledBy == null ? entity : null;
			entity.setGlowing(controlledBy == null);
			entity.setNoGravity(controlledBy == null);
			entity.setAttached(ModAttachmentTypes.IS_CONTROLLED, controlledBy == null ? user.getUuid() : null);

			if (entity instanceof ItemEntity itemEntity && controlledBy != null) {
				itemEntity.setToDefaultPickupDelay();
			}

			return TypedActionResult.success(user.getStackInHand(hand), true);
		}

		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}
