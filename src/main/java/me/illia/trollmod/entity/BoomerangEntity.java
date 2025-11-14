package me.illia.trollmod.entity;

import me.illia.trollmod.damage.ModDamageTypes;
import me.illia.trollmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BoomerangEntity extends Projectile {
	public boolean bounced = false;
	public int damage = 0;
	public BlockPos pos;

	public BoomerangEntity(EntityType<? extends Projectile> entityType, Level world) {
		super(entityType, world);
	}

	public BoomerangEntity(Level world, LivingEntity owner, int damage, BlockPos pos) {
		super(ModEntities.BOOMERANG, world);
		setOwner(owner);
		this.damage = damage;
		this.pos = pos;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public void tick() {
		super.tick();

		this.setPos(this.position().add(this.getDeltaMovement()));
		this.setYRot(this.getYRot() + 5);
		this.setDeltaMovement(this.getDeltaMovement().subtract(0.001,0,0.001));

		HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			this.onHitBlock((BlockHitResult)hitResult);
		} else if (hitResult.getType() == HitResult.Type.ENTITY) {
			this.onHitEntity((EntityHitResult)hitResult);
		}

		if (level().isClientSide) {
			level().addParticle(
				ParticleTypes.CLOUD,
				this.getX(),
				this.getY(),
				this.getZ(),
				0, 0, 0
			);
		}
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this, this.getOwner() != null ? this.getOwner().getId() : 0);
	}

	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
		super.recreateFromPacket(packet);
		int ownerId = packet.getData();
		if (ownerId > 0) {
			Entity owner = this.level().getEntity(ownerId);
			if (owner instanceof LivingEntity living) {
				this.setOwner(living);
			}
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		if (!blockHitResult.getBlockPos().equals(pos)) {
			setDeltaMovement(getDeltaMovement().scale(-1));
			bounced = true;
			level().destroyBlock(blockHitResult.getBlockPos(), true, this);
		}
	}

	public void give(Player player, boolean disableIfDamage) {
		ItemStack boomerang = ModItems.BOOMERANG.getDefaultInstance();

		if (player instanceof ServerPlayer serverPlayerEntity) {
			if (damage + 1 < boomerang.getMaxDamage()) {
				boomerang.hurt(1 + damage, level().getRandom(), serverPlayerEntity);

				if (player.getInventory().getFreeSlot() != -1) {
					player.getInventory().setItem(player.getInventory().getFreeSlot(), boomerang);

					this.discard();
				}
			} else if (!disableIfDamage) {
				this.discard();
			}
		}
	}

	@Override
	public void playerTouch(Player player) {
		if (getOwner() != null && !player.getStringUUID().equals(getOwner().getStringUUID())) {
			this.discard();
			player.hurt(ModDamageTypes.of(player.level(), ModDamageTypes.BOOMERANG_DAMAGE_TYPE_KEY), 5);

			setDeltaMovement(getDeltaMovement().scale(-0.85));
			bounced = true;
		} else if (bounced) {
			give(player, true);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entity instanceof Player player) {
			playerTouch(player);
			return;
		}

		entity.hurt(ModDamageTypes.of(entity.level(), ModDamageTypes.BOOMERANG_DAMAGE_TYPE_KEY), 5);
		setDeltaMovement(getDeltaMovement().scale(-0.85));
		bounced = true;
		super.onHitEntity(entityHitResult);
	}
}
