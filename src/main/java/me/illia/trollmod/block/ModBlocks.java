package me.illia.trollmod.block;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import me.illia.trollmod.world.tree.PurpleheartSaplingGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.TrapdoorBlock;

import java.util.ArrayList;

public class ModBlocks {
	public static final RegistryKey<ItemGroup> BLOCKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Util.id("blocks"));
	public static final ItemGroup BLOCKS_GROUP = FabricItemGroup.builder()
		.icon(() -> new ItemStack(ModBlocks.PURPLEHEART_LOG))
		.displayName(Text.translatable("itemGroup.trollmod.blocks", Trollmod.MODID))
		.build();

	public static final ArrayList<Block> BLOCKS = new ArrayList<>();
	public static final ArrayList<Item> ITEMS = new ArrayList<>();

	public static final Block PURPLEHEART_LOG = register(new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(2f)), "purpleheart_log");
	public static final Block PURPLEHEART_WOOD = register(new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2f)), "purpleheart_wood");
	public static final Block STRIPPED_PURPLEHEART_LOG = register(new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(2f)), "stripped_purpleheart_log");
	public static final Block STRIPPED_PURPLEHEART_WOOD = register(new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(2f)), "stripped_purpleheart_wood");

	public static final Block PURPLEHEART_PLANKS = register(new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2f)), "purpleheart_planks");
	public static final Block PURPLEHEART_LEAVES = register(new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(.2f).nonOpaque()), "purpleheart_leaves");
	public static final Block PURPLEHEART_SAPLING = register(new SaplingBlock(new PurpleheartSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).breakInstantly()), "purpleheart_sapling");
	public static final Block PURPLEHEART_SLAB = register(new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)), "purpleheart_slab");
	public static final Block PURPLEHEART_STAIRS = register(new StairsBlock(PURPLEHEART_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)), "purpleheart_stairs");
	public static final Block PURPLEHEART_BUTTON = register(new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON), ModSetTypes.PURPLEHEART, 10, true), "purpleheart_button");
	public static final Block PURPLEHEART_FENCE = register(new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE)), "purpleheart_fence");
	public static final Block PURPLEHEART_FENCE_GATE = register(new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE), ModWoodTypes.PURPLEHEART), "purpleheart_fence_gate");
	public static final Block PURPLEHEART_PRESSURE_PLATE = register(new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE), ModSetTypes.PURPLEHEART), "purpleheart_pressure_plate");
	public static final Block PURPLEHEART_DOOR = register(new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR), ModSetTypes.PURPLEHEART), "purpleheart_door");
	public static final Block PURPLEHEART_TRAPDOOR = register(new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).nonOpaque(), ModSetTypes.PURPLEHEART), "purpleheart_trapdoor");



	public static final Block GHOST_BLOCK = register(new GhostBlock(FabricBlockSettings.create().noCollision()), "ghost_block");

	public static final Block SOUND_BLOCK = register(new SoundBlock(FabricBlockSettings.create()), "sound_block");

	public static final Block SPIKY_BLOCK = register(new SpikyBlock(FabricBlockSettings.create()), "spiky_block");

	public static Block register(Block block, String name) {
		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Identifier id = Util.id(name);
		Registry.register(Registries.ITEM, id, blockItem);

		BLOCKS.add(block);

		return Registry.register(Registries.BLOCK, id, block);
	}

	public static Block registerWithoutItem(Block block, String name) {
		return Registry.register(Registries.BLOCK, Util.id(name), block);
	}

	public static void init() {
		Trollmod.LOGGER.info("Initializing blocks for mod" + Trollmod.MODID);

		Registry.register(Registries.ITEM_GROUP, BLOCKS_GROUP_KEY, BLOCKS_GROUP);

		ItemGroupEvents.modifyEntriesEvent(BLOCKS_GROUP_KEY).register((itemGroup) -> {
			for (Block block : BLOCKS) {
				itemGroup.add(block.asItem());
			}

			for (Item item : ITEMS) {
				itemGroup.add(item);
			}
		});

		registerDefaultProps();
	}

	public static void registerDefaultProps() {
		StrippableBlockRegistry.register(ModBlocks.PURPLEHEART_LOG, ModBlocks.STRIPPED_PURPLEHEART_LOG);
		StrippableBlockRegistry.register(ModBlocks.PURPLEHEART_WOOD, ModBlocks.STRIPPED_PURPLEHEART_WOOD);

		FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();

		flammableBlockRegistry.add(ModBlocks.PURPLEHEART_LOG, 5, 5);
		flammableBlockRegistry.add(ModBlocks.PURPLEHEART_WOOD, 5, 5);
		flammableBlockRegistry.add(ModBlocks.PURPLEHEART_PLANKS, 5, 20);
		flammableBlockRegistry.add(ModBlocks.PURPLEHEART_LEAVES, 30, 60);
		flammableBlockRegistry.add(ModBlocks.STRIPPED_PURPLEHEART_LOG, 5, 5);
		flammableBlockRegistry.add(ModBlocks.STRIPPED_PURPLEHEART_WOOD, 5, 5);
		flammableBlockRegistry.add(ModBlocks.PURPLEHEART_SLAB, 5, 20);
		flammableBlockRegistry.add(ModBlocks.PURPLEHEART_STAIRS, 5, 20);
	}
}
