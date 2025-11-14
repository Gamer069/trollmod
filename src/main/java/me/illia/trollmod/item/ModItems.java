package me.illia.trollmod.item;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModItems {
	public static final RegistryKey<ItemGroup> ITEMS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Util.id("items"));
	public static final ItemGroup ITEMS_GROUP = FabricItemGroup.builder()
		.icon(() -> new ItemStack(ModItems.BOOMERANG))
		.displayName(Text.translatable("itemGroup.trollmod.items", Trollmod.MODID))
		.build();

	public static ArrayList<Item> ITEMS = new ArrayList<>();

	public static final Item BOOMERANG = register(
		new BoomerangItem(new FabricItemSettings().maxCount(1).maxDamage(50)),
		"boomerang"
	);
	public static final Item WOODEN_TEAPOT = register(
		new TeapotItem(ToolMaterials.WOOD, new FabricItemSettings().maxCount(1)),
		"wooden_teapot"
	);
	public static final Item HOT_AIR_BALLOON = register(
		new HotAirBalloonItem(new FabricItemSettings().maxCount(1)),
		"hot_air_balloon"
	);

	public static final Item MOVING_WAND = register(
		new MovingWandItem(new FabricItemSettings().maxCount(1)),
		"moving_wand"
	);

	public static final Item TOTEM_OF_DYING = register(
		new TotemOfDyingItem(new FabricItemSettings().maxCount(1)),
		"totem_of_dying"
	);

	public static final Item SPIKY_ITEM = register(
		new SpikyItem(new FabricItemSettings().maxCount(1)),
		"spiky_item"
	);

	public static Item register(Item item, String id) {
		Identifier itemID = Util.id(id);

		Item registered = Registry.register(Registries.ITEM, itemID, item);

		ITEMS.add(registered);

		return registered;
	}

	public static void init() {
		Trollmod.LOGGER.info("Initializing items for mod " + Trollmod.MODID);

		Registry.register(Registries.ITEM_GROUP, ITEMS_GROUP_KEY, ITEMS_GROUP);

		ItemGroupEvents.modifyEntriesEvent(ITEMS_GROUP_KEY)
			.register((itemGroup) -> {
				for (Item item : ITEMS) {
					itemGroup.add(item);
				}
			});
	}
}
