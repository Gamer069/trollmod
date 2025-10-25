package me.illia.trollmod.damage;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class ModDamageTypes {
	public static final RegistryKey<DamageType> BOOMERANG_DAMAGE_TYPE_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Util.id("boomerang_damage_type"));
	public static final DamageType BOOMERANG_DAMAGE_TYPE = new DamageType("boomerang", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f);

	public static void init() {
		Trollmod.LOGGER.info("Registering damage types for " + Trollmod.MODID + "...");
	}

	public static DamageSource of(World world, RegistryKey<DamageType> key) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
	}
}