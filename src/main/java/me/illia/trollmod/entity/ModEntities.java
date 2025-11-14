package me.illia.trollmod.entity;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import me.illia.trollmod.item.HotAirBalloonItem;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
	public static final EntityType<BoomerangEntity> BOOMERANG = Registry.register(
		Registries.ENTITY_TYPE,
		Util.id("boomerang"),
		FabricEntityTypeBuilder.<BoomerangEntity>create(SpawnGroup.MISC, BoomerangEntity::new)
			.dimensions(EntityDimensions.fixed(0.35f, 0.35f)).build()
	);
	public static final EntityType<HotAirBalloonEntity> HOT_AIR_BALLOON = Registry.register(
		Registries.ENTITY_TYPE,
		Util.id("hot_air_balloon"),
		FabricEntityTypeBuilder.<HotAirBalloonEntity>create(SpawnGroup.MISC, HotAirBalloonEntity::new)
			.dimensions(EntityDimensions.fixed(2f, 4f)).build()
	);

	public static void init() {
		FabricDefaultAttributeRegistry.register(HOT_AIR_BALLOON, HotAirBalloonEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8));
		Trollmod.LOGGER.info("Initializing entities for mod " + Trollmod.MODID);
	}
}
