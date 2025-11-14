package me.illia.trollmod.item;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.attachment.ModAttachmentTypes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import java.util.UUID;

public class MovingWandItem extends Item {
	public Entity controlledEntity;

	public MovingWandItem(FabricItemSettings fabricItemSettings) {
		super(fabricItemSettings);
	}

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		double reach = 3.0;
		Vec3 eyePos = user.getEyePosition(1.0f);
		Vec3 look = user.getViewVector(1.0f);
		Vec3 targetPos = eyePos.add(look.scale(reach));

		AABB box = user.getBoundingBox().expandTowards(look.scale(reach)).inflate(1.0);

		EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
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
			entity.setGlowingTag(controlledBy == null);
			entity.setNoGravity(controlledBy == null);
			entity.setAttached(ModAttachmentTypes.IS_CONTROLLED, controlledBy == null ? user.getUUID() : null);

			if (entity instanceof ItemEntity itemEntity && controlledBy != null) {
				itemEntity.setDefaultPickUpDelay();
			}

			return InteractionResultHolder.sidedSuccess(user.getItemInHand(hand), true);
		}

		return InteractionResultHolder.pass(user.getItemInHand(hand));
	}
}
