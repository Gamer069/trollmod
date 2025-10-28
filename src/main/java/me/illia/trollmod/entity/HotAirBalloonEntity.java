package me.illia.trollmod.entity;

import com.google.common.collect.Maps;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.item.ModItems;
import me.illia.trollmod.mixin.JumpingAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class HotAirBalloonEntity extends FlyingEntity implements GravityChangingEntity {
	private static final TrackedData<String> OWNER_UUID = DataTracker.registerData(HotAirBalloonEntity.class, TrackedDataHandlerRegistry.STRING);
	private static final TrackedData<Byte> COLOR = DataTracker.registerData(HotAirBalloonEntity.class, TrackedDataHandlerRegistry.BYTE);
	public static final Map<DyeColor, float[]> COLORS = Maps.newEnumMap(
		(Map)Arrays.stream(DyeColor.values()).collect(Collectors.toMap(color -> color, DyeColor::getColorComponents))
	);

	public LivingEntity controllingPassenger = null;

	@Override
	public boolean shouldRenderName() {
		return false;
	}

	@Override
	public boolean isInvisibleTo(PlayerEntity player) {
		if (player.getWorld().isClient && player.getUuidAsString().equals(getOwnerUuid())) {
			MinecraftClient client = MinecraftClient.getInstance();
			return client.options.getPerspective() == Perspective.FIRST_PERSON;
		}
		return super.isInvisibleTo(player);
	}

	private String getOwnerUuid() {
		return this.dataTracker.get(OWNER_UUID);
	}

	@Override
	public boolean damage(DamageSource damageSource, float amount) {
		if (damageSource.getAttacker() instanceof PlayerEntity player) {
			if (player.getUuidAsString().equals(getOwnerUuid())) {
				if (!player.getAbilities().creativeMode)
					player.giveItemStack(ModItems.HOT_AIR_BALLOON.getDefaultStack());
				discard();
			}
		}
		return super.damage(damageSource, amount);
	}

	public HotAirBalloonEntity(World world, PlayerEntity owner) {
		super(ModEntities.HOT_AIR_BALLOON, world);
		this.dataTracker.set(OWNER_UUID, owner.getUuidAsString());
	}

	public HotAirBalloonEntity(EntityType<HotAirBalloonEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void tickControlled(PlayerEntity player, Vec3d movementInput) {
		Vec3d forwardDir = player.getRotationVector().normalize();
		double speed = 0.1;

		// only forward/backwards
		double dx = forwardDir.x * player.forwardSpeed * speed;
		double dz = forwardDir.z * player.forwardSpeed * speed;

		// vertical
		double dy = ((JumpingAccessor) player).trollmod$isJumping() ? 0.15 : -0.05;

		this.setVelocity(dx, dy, dz);
		this.move(MovementType.SELF, this.getVelocity());

		setYaw(player.getHeadYaw());
		prevYaw = player.prevHeadYaw;
		headYaw = player.headYaw;

		super.tickControlled(player, movementInput);
	}

	@Override
	public LivingEntity getControllingPassenger() {
		return controllingPassenger;
	}

	@Override
	public double getMountedHeightOffset() {
		return 0;
	}

	@Override
	public boolean canTarget(LivingEntity target) {
		return false;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public void addPassenger(Entity entity) {
		super.addPassenger(entity);

		if (entity instanceof PlayerEntity) {
			MinecraftClient client = MinecraftClient.getInstance();
			client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
			Trollmod.LOCK = true;

			controllingPassenger = (LivingEntity)entity;
		}
	}

	@Override
	protected void removePassenger(Entity entity) {
		if (entity instanceof PlayerEntity) {
			MinecraftClient client = MinecraftClient.getInstance();
			client.options.setPerspective(Perspective.FIRST_PERSON);
			Trollmod.LOCK = false;

			controllingPassenger = null;
		}
		super.removePassenger(entity);
	}

	public byte getColor() {
		return dataTracker.get(COLOR);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() instanceof DyeItem item) {
			DyeColor color = item.getColor();
			if (color.getId() != dataTracker.get(COLOR)) {
				stack.decrement(1);
			}

			dataTracker.set(COLOR, (byte)color.getId());
			return ActionResult.CONSUME;
		} else {
			if (!this.getWorld().isClient) {
				player.startRiding(this);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(OWNER_UUID, "");
		this.dataTracker.startTracking(COLOR, (byte)0);
	}

	@Override
	public Iterable<ItemStack> getArmorItems() {
		return Collections.emptyList();
	}

	@Override
	public ItemStack getEquippedStack(EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void equipStack(EquipmentSlot slot, ItemStack stack) {
	}

	@Override
	public Arm getMainArm() {
		return Arm.RIGHT;
	}

	@Override
	public double gravity() {
		return 0.05;
	}
}
