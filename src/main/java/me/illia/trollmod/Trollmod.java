package me.illia.trollmod;

import me.illia.trollmod.attachment.ModAttachmentTypes;
import me.illia.trollmod.block.ModBlocks;
import me.illia.trollmod.block.ModSetTypes;
import me.illia.trollmod.block.ModSigns;
import me.illia.trollmod.block.ModWoodTypes;
import me.illia.trollmod.damage.ModDamageTypes;
import me.illia.trollmod.dispenser.ModDispenserBehaviors;
import me.illia.trollmod.effect.ModEffects;
import me.illia.trollmod.entity.ModEntities;
import me.illia.trollmod.event.ModEvents;
import me.illia.trollmod.item.ModItems;
import me.illia.trollmod.item.ModPotions;
import me.illia.trollmod.networking.ModNetworking;
import me.illia.trollmod.recipe.ModRecipes;
import me.illia.trollmod.screen.ModScreenHandlers;
import me.illia.trollmod.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trollmod implements ModInitializer {
	public static String MODID = "trollmod";
	public static Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static boolean LOCK = false;

	@Override
	public void onInitialize() {
		ModAttachmentTypes.init();
		ModEvents.init();
		ModItems.init();
		ModSetTypes.init();
		ModWoodTypes.init();
		ModSigns.init();
		ModBlocks.init();
		ModEntities.init();
		ModDamageTypes.init();
		ModNetworking.init();
		ModScreenHandlers.init();
		ModDispenserBehaviors.init();
		ModWorldGeneration.init();
		ModRecipes.init();
		ModEffects.init();
		ModPotions.init();
	}
}
