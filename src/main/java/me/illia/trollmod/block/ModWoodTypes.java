package me.illia.trollmod.block;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.level.block.state.properties.WoodType;
import java.util.Set;

public class ModWoodTypes {
	public static final WoodType PURPLEHEART = register(WoodTypeBuilder.copyOf(WoodType.OAK).register(Util.id("purpleheart"), ModSetTypes.PURPLEHEART));

	public static WoodType register(WoodType type) {
		return type;
	}

	public static void init() {
		Trollmod.LOGGER.info("Initializing wood types for mod " + Trollmod.MODID);
	}
}
