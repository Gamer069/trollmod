package me.illia.trollmod.damage;

import me.illia.trollmod.Trollmod;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes {
	public static final RegistryKey<DamageType> BOOMERANG_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Trollmod.MODID, "boomerang_damage_type"));

	public static void init() {
		Trollmod.LOGGER.info("Registering damage types for " + Trollmod.MODID + "...");
	}

	public static DamageSource of(World world, RegistryKey<DamageType> key) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
	}
}