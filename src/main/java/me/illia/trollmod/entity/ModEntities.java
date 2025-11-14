package me.illia.trollmod.entity;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import me.illia.trollmod.item.HotAirBalloonItem;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModEntities {
	public static final EntityType<BoomerangEntity> BOOMERANG = Registry.register(
		BuiltInRegistries.ENTITY_TYPE,
		Util.id("boomerang"),
		FabricEntityTypeBuilder.<BoomerangEntity>create(MobCategory.MISC, BoomerangEntity::new)
			.dimensions(EntityDimensions.fixed(0.35f, 0.35f)).build()
	);
	public static final EntityType<HotAirBalloonEntity> HOT_AIR_BALLOON = Registry.register(
		BuiltInRegistries.ENTITY_TYPE,
		Util.id("hot_air_balloon"),
		FabricEntityTypeBuilder.<HotAirBalloonEntity>create(MobCategory.MISC, HotAirBalloonEntity::new)
			.dimensions(EntityDimensions.fixed(2f, 4f)).build()
	);

	public static void init() {
		FabricDefaultAttributeRegistry.register(HOT_AIR_BALLOON, HotAirBalloonEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8));
		Trollmod.LOGGER.info("Initializing entities for mod " + Trollmod.MODID);
	}
}
