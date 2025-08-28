package me.illia.trollmod.item;

import me.illia.trollmod.Trollmod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModItems {
	public static ArrayList<Item> ITEMS = new ArrayList<>();

	public static final Item BOOMERANG = register(
		new BoomerangItem(new FabricItemSettings().maxCount(1).maxDamage(50)),
		"boomerang"
	);

	public static final Item WOODEN_TEAPOT = register(
		new TeapotItem(ToolMaterials.WOOD, new FabricItemSettings().maxCount(1)),
		"wooden_teapot"
	);

	public static Item register(Item item, String id) {
		// Create the identifier for the item.
		Identifier itemID = Identifier.of(Trollmod.MODID, id);

		Item registered = Registry.register(Registries.ITEM, itemID, item);

		ITEMS.add(registered);

		return registered;
	}

	public static void init() {
		Trollmod.LOGGER.info("Initting items...");
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
			.register((itemGroup) -> {
				for (Item item : ITEMS) {
					itemGroup.add(item);
				}
			});
	}
}
