package me.illia.trollmod.entity;

import com.google.common.collect.Maps;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.item.ModItems;
import me.illia.trollmod.mixin.JumpingAccessor;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class HotAirBalloonEntity extends FlyingMob implements GravityChangingEntity {
	private static final EntityDataAccessor<String> OWNER_UUID = SynchedEntityData.defineId(HotAirBalloonEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Byte> COLOR = SynchedEntityData.defineId(HotAirBalloonEntity.class, EntityDataSerializers.BYTE);
	public static final Map<DyeColor, float[]> COLORS = Maps.newEnumMap(
		(Map)Arrays.stream(DyeColor.values()).collect(Collectors.toMap(color -> color, DyeColor::getTextureDiffuseColors))
	);

	public LivingEntity controllingPassenger = null;

	@Override
	public boolean shouldShowName() {
		return false;
	}

	@Override
	public boolean isInvisibleTo(Player player) {
		if (player.level().isClientSide && player.getStringUUID().equals(getOwnerUuid())) {
			Minecraft client = Minecraft.getInstance();
			return client.options.getCameraType() == CameraType.FIRST_PERSON;
		}
		return super.isInvisibleTo(player);
	}

	private String getOwnerUuid() {
		return this.entityData.get(OWNER_UUID);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource.getEntity() instanceof Player player) {
			if (player.getStringUUID().equals(getOwnerUuid())) {
				if (!player.getAbilities().instabuild)
					player.addItem(ModItems.HOT_AIR_BALLOON.getDefaultInstance());
				discard();
			}
		}
		return super.hurt(damageSource, amount);
	}

	public HotAirBalloonEntity(Level world, Player owner) {
		super(ModEntities.HOT_AIR_BALLOON, world);
		this.entityData.set(OWNER_UUID, owner.getStringUUID());
	}

	public HotAirBalloonEntity(EntityType<HotAirBalloonEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void tickRidden(Player player, Vec3 movementInput) {
		Vec3 forwardDir = player.getLookAngle().normalize();
		double speed = 0.1;

		// only forward/backwards
		double dx = forwardDir.x * player.zza * speed;
		double dz = forwardDir.z * player.zza * speed;

		// vertical
		double dy = ((JumpingAccessor) player).trollmod$isJumping() ? 0.15 : -0.05;

		this.setDeltaMovement(dx, dy, dz);
		this.move(MoverType.SELF, this.getDeltaMovement());

		setYRot(player.getYHeadRot());
		yRotO = player.yHeadRotO;
		yHeadRot = player.yHeadRot;

		super.tickRidden(player, movementInput);
	}

	@Override
	public LivingEntity getControllingPassenger() {
		return controllingPassenger;
	}

	@Override
	public double getPassengersRidingOffset() {
		return 0;
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		return false;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public void addPassenger(Entity entity) {
		super.addPassenger(entity);

		if (entity instanceof Player) {
			Minecraft client = Minecraft.getInstance();
			client.options.setCameraType(CameraType.THIRD_PERSON_BACK);
			Trollmod.LOCK = true;

			controllingPassenger = (LivingEntity)entity;
		}
	}

	@Override
	protected void removePassenger(Entity entity) {
		if (entity instanceof Player) {
			Minecraft client = Minecraft.getInstance();
			client.options.setCameraType(CameraType.FIRST_PERSON);
			Trollmod.LOCK = false;

			controllingPassenger = null;
		}
		super.removePassenger(entity);
	}

	public byte getColor() {
		return entityData.get(COLOR);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof DyeItem item) {
			DyeColor color = item.getDyeColor();
			if (color.getId() != entityData.get(COLOR)) {
				stack.shrink(1);
			}

			entityData.set(COLOR, (byte)color.getId());
			return InteractionResult.CONSUME;
		} else {
			if (!this.level().isClientSide) {
				player.startRiding(this);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(OWNER_UUID, "");
		this.entityData.define(COLOR, (byte)0);
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return Collections.emptyList();
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
	}

	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
	}

	@Override
	public double gravity() {
		return 0.05;
	}
}
