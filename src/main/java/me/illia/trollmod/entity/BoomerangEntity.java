package me.illia.trollmod.entity;

import me.illia.trollmod.damage.ModDamageTypes;
import me.illia.trollmod.item.ModItems;
import me.illia.trollmod.Trollmod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BoomerangEntity extends ProjectileEntity {
	public boolean bounced = false;
	public int damage = 0;
	public BlockPos pos;

	public BoomerangEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public BoomerangEntity(World world, LivingEntity owner, int damage, BlockPos pos) {
		super(ModEntities.BOOMERANG, world);
		setOwner(owner);
		this.damage = damage;
		this.pos = pos;
	}

	@Override
	public boolean canHit() {
		return true;
	}

	@Override
	public void tick() {
		super.tick();

		this.setPosition(this.getPos().add(this.getVelocity()));
		this.setYaw(this.getYaw() + 5);
		this.setVelocity(this.getVelocity().subtract(0.001,0,0.001));

		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			this.onBlockHit((BlockHitResult)hitResult);
		} else if (hitResult.getType() == HitResult.Type.ENTITY) {
			this.onEntityHit((EntityHitResult)hitResult);
		}

		if (getWorld().isClient) {
			Trollmod.LOGGER.info("Tick " + this.age + ": Pos=(" +
				String.format("%.3f,%.3f,%.3f", this.getX(), this.getY(), this.getZ()) +
				") Vel=(" +
				String.format("%.3f,%.3f,%.3f", this.getVelocity().x, this.getVelocity().y, this.getVelocity().z) + ")");
			getWorld().addParticle(
				ParticleTypes.END_ROD,
				this.getX(),
				this.getY(),
				this.getZ(),
				0, 0, 0
			);
		}
	}

	@Override
	protected void initDataTracker() {
	}

	@Override
	public Packet<ClientPlayPacketListener> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this, this.getOwner() != null ? this.getOwner().getId() : 0);
	}

	@Override
	public void onSpawnPacket(EntitySpawnS2CPacket packet) {
		super.onSpawnPacket(packet);
		int ownerId = packet.getEntityData();
		if (ownerId > 0) {
			Entity owner = this.getWorld().getEntityById(ownerId);
			if (owner instanceof LivingEntity living) {
				this.setOwner(living);
			}
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		if (!blockHitResult.getBlockPos().equals(pos)) {
			setVelocity(getVelocity().multiply(-1));
			bounced = true;
			getWorld().breakBlock(blockHitResult.getBlockPos(), true, this);
		}
	}

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		DamageSources sources = player.getWorld().getDamageSources();
		if (getOwner() != null && !player.getUuidAsString().equals(getOwner().getUuidAsString())) {
			this.discard();
			player.damage(ModDamageTypes.of(player.getWorld(), ModDamageTypes.BOOMERANG_DAMAGE_TYPE), 5);
		} else if (bounced) {
			ItemStack boomerang = ModItems.BOOMERANG.getDefaultStack();
			if (player instanceof ServerPlayerEntity serverPlayerEntity) {
				if (damage + 1 < boomerang.getMaxDamage()) {
					boomerang.damage(1 + damage, getWorld().getRandom(), serverPlayerEntity);
					if (player.getInventory().getEmptySlot() != -1) {
						setVelocity(getVelocity().multiply(-0.85));
						bounced = true;

						player.getInventory().setStack(player.getInventory().getEmptySlot(), boomerang);
						this.discard();
					}
				}
			}
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entity instanceof PlayerEntity player) {
			onPlayerCollision(player);
			return;
		}

		entity.damage(ModDamageTypes.of(entity.getWorld(), ModDamageTypes.BOOMERANG_DAMAGE_TYPE), 5);
		setVelocity(getVelocity().multiply(-0.85));
		bounced = true;
		super.onEntityHit(entityHitResult);
	}
}
