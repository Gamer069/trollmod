package me.illia.trollmod.block;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import me.illia.trollmod.world.tree.PurpleheartSaplingGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import java.util.ArrayList;

public class ModBlocks {
	public static final ResourceKey<CreativeModeTab> BLOCKS_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Util.id("blocks"));
	public static final CreativeModeTab BLOCKS_GROUP = FabricItemGroup.builder()
		.icon(() -> new ItemStack(ModBlocks.PURPLEHEART_LOG))
		.title(Component.translatable("itemGroup.trollmod.blocks", Trollmod.MODID))
		.build();

	public static final ArrayList<Block> BLOCKS = new ArrayList<>();
	public static final ArrayList<Item> ITEMS = new ArrayList<>();

	public static final Block PURPLEHEART_LOG = register(new RotatedPillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(2f)), "purpleheart_log");
	public static final Block PURPLEHEART_WOOD = register(new RotatedPillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2f)), "purpleheart_wood");
	public static final Block STRIPPED_PURPLEHEART_LOG = register(new RotatedPillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(2f)), "stripped_purpleheart_log");
	public static final Block STRIPPED_PURPLEHEART_WOOD = register(new RotatedPillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(2f)), "stripped_purpleheart_wood");

	public static final Block PURPLEHEART_PLANKS = register(new RotatedPillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2f)), "purpleheart_planks");
	public static final Block PURPLEHEART_LEAVES = register(new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(.2f).noOcclusion()), "purpleheart_leaves");
	public static final Block PURPLEHEART_SAPLING = register(new SaplingBlock(new PurpleheartSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).instabreak()), "purpleheart_sapling");
	public static final Block PURPLEHEART_SLAB = register(new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)), "purpleheart_slab");
	public static final Block PURPLEHEART_STAIRS = register(new StairBlock(PURPLEHEART_PLANKS.defaultBlockState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)), "purpleheart_stairs");
	public static final Block PURPLEHEART_BUTTON = register(new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON), ModSetTypes.PURPLEHEART, 10, true), "purpleheart_button");
	public static final Block PURPLEHEART_FENCE = register(new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE)), "purpleheart_fence");
	public static final Block PURPLEHEART_FENCE_GATE = register(new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE), ModWoodTypes.PURPLEHEART), "purpleheart_fence_gate");
	public static final Block PURPLEHEART_PRESSURE_PLATE = register(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE), ModSetTypes.PURPLEHEART), "purpleheart_pressure_plate");
	public static final Block PURPLEHEART_DOOR = register(new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR), ModSetTypes.PURPLEHEART), "purpleheart_door");
	public static final Block PURPLEHEART_TRAPDOOR = register(new TrapDoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).noOcclusion(), ModSetTypes.PURPLEHEART), "purpleheart_trapdoor");



	public static final Block GHOST_BLOCK = register(new GhostBlock(FabricBlockSettings.of().noCollission()), "ghost_block");

	public static final Block SOUND_BLOCK = register(new SoundBlock(FabricBlockSettings.of()), "sound_block");

	public static final Block SPIKY_BLOCK = register(new SpikyBlock(FabricBlockSettings.of()), "spiky_block");

	public static Block register(Block block, String name) {
		BlockItem blockItem = new BlockItem(block, new Item.Properties());
		ResourceLocation id = Util.id(name);
		Registry.register(BuiltInRegistries.ITEM, id, blockItem);

		BLOCKS.add(block);

		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

	public static Block registerWithoutItem(Block block, String name) {
		return Registry.register(BuiltInRegistries.BLOCK, Util.id(name), block);
	}

	public static void init() {
		Trollmod.LOGGER.info("Initializing blocks for mod" + Trollmod.MODID);

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, BLOCKS_GROUP_KEY, BLOCKS_GROUP);

		ItemGroupEvents.modifyEntriesEvent(BLOCKS_GROUP_KEY).register((itemGroup) -> {
			for (Block block : BLOCKS) {
				itemGroup.accept(block.asItem());
			}

			for (Item item : ITEMS) {
				itemGroup.accept(item);
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
