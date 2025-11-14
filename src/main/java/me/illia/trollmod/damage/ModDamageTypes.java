package me.illia.trollmod.damage;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class ModDamageTypes {
	public static final ResourceKey<DamageType> BOOMERANG_DAMAGE_TYPE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, Util.id("boomerang_damage_type"));
	public static final DamageType BOOMERANG_DAMAGE_TYPE = new DamageType("boomerang", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f);

	public static void init() {
		Trollmod.LOGGER.info("Initializing damage types for mod " + Trollmod.MODID);
	}

	public static DamageSource of(Level world, ResourceKey<DamageType> key) {
		return new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
	}
}