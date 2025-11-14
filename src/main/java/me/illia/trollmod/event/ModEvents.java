package me.illia.trollmod.event;

import me.illia.trollmod.attachment.ModAttachmentTypes;
import me.illia.trollmod.item.ModItems;
import me.illia.trollmod.item.MovingWandItem;
import me.illia.trollmod.item.TotemOfDyingItem;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModEvents {
	public static void init() {
		ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> {
			if (entity instanceof ItemEntity item && item.getItem().getItem() instanceof MovingWandItem mwi) {
				Entity controlled = mwi.controlledEntity;
				if (controlled != null) {
					controlled.setAttached(ModAttachmentTypes.IS_CONTROLLED, null);
					controlled.setGlowingTag(false);
					controlled.setNoGravity(false);
					controlled = null;
				}
			}
		});

		ServerEntityEvents.EQUIPMENT_CHANGE.register(((livingEntity, equipmentSlot, itemStack, itemStack1) -> {
			if (equipmentSlot.getType() == EquipmentSlot.Type.HAND && livingEntity instanceof Player playerEntity) {
				Item item = itemStack.getItem();
				if (item instanceof MovingWandItem mwi) {
					if (mwi.controlledEntity != null) {
						mwi.controlledEntity.setAttached(ModAttachmentTypes.IS_CONTROLLED, null);
						mwi.controlledEntity.setGlowingTag(false);
						mwi.controlledEntity.setNoGravity(false);

						if (mwi.controlledEntity instanceof ItemEntity itemEntity) {
							itemEntity.setDefaultPickUpDelay();
						}

						mwi.controlledEntity = null;
					}
				}
			}
		}));
	}

	public static void afterDamage(LivingEntity entity, DamageSource source, float amount) {
		if (entity instanceof Player player && player.isHolding(ModItems.TOTEM_OF_DYING)) {
			player.level().explode(entity, player.getX(), player.getY(), player.getZ(),20f, true, Level.ExplosionInteraction.BLOCK);

			ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
			ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);

			if (mainHandStack.getItem() instanceof TotemOfDyingItem) {
				mainHandStack.shrink(1);
			} else if (offHandStack.getItem() instanceof TotemOfDyingItem) {
				offHandStack.shrink(1);
			}

			player.kill();

			return;
		}

		if (source.is(DamageTypes.PLAYER_ATTACK)) {
			entity.hurt(source, amount / 2);
		}
	}
}
