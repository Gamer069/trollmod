package me.illia.trollmod.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import me.illia.trollmod.Util;

public class ModComponents implements EntityComponentInitializer {
	public static final ComponentKey<BoomerangCatchComponent> BOOMERANG_CATCH_COMPONENT_KEY = ComponentRegistry.getOrCreate(Util.id("catch"), BoomerangCatchComponent.class);
	public static final ComponentKey<PhasingComponent> PHASING_COMPONENT_KEY = ComponentRegistry.getOrCreate(Util.id("phasing"), PhasingComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
		entityComponentFactoryRegistry.registerForPlayers(BOOMERANG_CATCH_COMPONENT_KEY, player -> new BoomerangCatchComponentImpl(), RespawnCopyStrategy.NEVER_COPY);
		entityComponentFactoryRegistry.registerForPlayers(PHASING_COMPONENT_KEY, player -> new PhasingComponentImpl(), RespawnCopyStrategy.NEVER_COPY);
	}
}
