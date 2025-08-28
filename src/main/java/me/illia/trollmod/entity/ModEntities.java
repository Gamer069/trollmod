package me.illia.trollmod.entity;

import me.illia.trollmod.Trollmod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
	public static final EntityType<BoomerangEntity> BOOMERANG = Registry.register(
		Registries.ENTITY_TYPE,
		Identifier.of(Trollmod.MODID, "boomerang"),
		FabricEntityTypeBuilder.<BoomerangEntity>create(SpawnGroup.MISC, BoomerangEntity::new)
			.dimensions(EntityDimensions.fixed(0.35f, 0.35f)).build()
	);

	public static void init() {
		Trollmod.LOGGER.info("Initting entities");
	}
}
