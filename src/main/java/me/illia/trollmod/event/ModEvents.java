package me.illia.trollmod.event;

import me.illia.trollmod.attachment.ModAttachmentTypes;
import me.illia.trollmod.item.ModItems;
import me.illia.trollmod.item.MovingWandItem;
import me.illia.trollmod.item.TotemOfDyingItem;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ModEvents {
	public static void init() {
		ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> {
			if (entity instanceof ItemEntity item && item.getStack().getItem() instanceof MovingWandItem mwi) {
				Entity controlled = mwi.controlledEntity;
				if (controlled != null) {
					controlled.setAttached(ModAttachmentTypes.IS_CONTROLLED, null);
					controlled.setGlowing(false);
					controlled.setNoGravity(false);
					controlled = null;
				}
			}
		});

		ServerEntityEvents.EQUIPMENT_CHANGE.register(((livingEntity, equipmentSlot, itemStack, itemStack1) -> {
			if (equipmentSlot.getType() == EquipmentSlot.Type.HAND && livingEntity instanceof PlayerEntity playerEntity) {
				Item item = itemStack.getItem();
				if (item instanceof MovingWandItem mwi) {
					if (mwi.controlledEntity != null) {
						mwi.controlledEntity.setAttached(ModAttachmentTypes.IS_CONTROLLED, null);
						mwi.controlledEntity.setGlowing(false);
						mwi.controlledEntity.setNoGravity(false);

						if (mwi.controlledEntity instanceof ItemEntity itemEntity) {
							itemEntity.setToDefaultPickupDelay();
						}

						mwi.controlledEntity = null;
					}
				}
			}
		}));
	}

	public static void afterDamage(LivingEntity entity, DamageSource source, float amount) {
		if (entity instanceof PlayerEntity player && player.isHolding(ModItems.TOTEM_OF_DYING)) {
			player.getWorld().createExplosion(entity, player.getX(), player.getY(), player.getZ(),20f, true, World.ExplosionSourceType.BLOCK);

			ItemStack mainHandStack = player.getStackInHand(Hand.MAIN_HAND);
			ItemStack offHandStack = player.getStackInHand(Hand.OFF_HAND);

			if (mainHandStack.getItem() instanceof TotemOfDyingItem) {
				mainHandStack.decrement(1);
			} else if (offHandStack.getItem() instanceof TotemOfDyingItem) {
				offHandStack.decrement(1);
			}

			player.kill();

			return;
		}

		if (source.isOf(DamageTypes.PLAYER_ATTACK)) {
			entity.damage(source, amount / 2);
		}
	}
}
