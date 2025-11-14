package me.illia.trollmod.block;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import me.illia.trollmod.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;

public class ModSigns {
	public static final Block PURPLEHEART_SIGN = registerSignBlock(
		new SignBlock(
			FabricBlockSettings.copyOf(Blocks.OAK_SIGN),
			ModWoodTypes.PURPLEHEART
		) {
			@Override
			public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
				return new PurpleheartSign(pos, state);
			}
		},
		"purpleheart_sign"
	);

	public static final Block PURPLEHEART_WALL_SIGN = registerSignBlock(
		new WallSignBlock(
			FabricBlockSettings.copyOf(
				Blocks.OAK_WALL_SIGN
			),
			ModWoodTypes.PURPLEHEART
		) {
			@Override
			public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
				return new PurpleheartSign(pos, state);
			}
		},
		"purpleheart_wall_sign"
	);

	public static final Block PURPLEHEART_HANGING_SIGN = registerSignBlock(
		new SignBlock(
			FabricBlockSettings.copyOf(
				Blocks.OAK_HANGING_SIGN
			),
			ModWoodTypes.PURPLEHEART
		) {
			@Override
			public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
				return new PurpleheartHangingSign(pos, state);
			}
		},
		"purpleheart_hanging_sign"
	);

	public static final Block PURPLEHEART_WALL_HANGING_SIGN = registerSignBlock(
		new SignBlock(
			FabricBlockSettings.copyOf(
				Blocks.OAK_WALL_HANGING_SIGN
			),
			ModWoodTypes.PURPLEHEART
		) {
			@Override
			public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
				return new PurpleheartHangingSign(pos, state);
			}
		},
		"purpleheart_wall_hanging_sign"
	);

	public static final SignItem PURPLEHEART_SIGN_ITEM = register(new SignItem(new Item.Settings(), PURPLEHEART_SIGN, PURPLEHEART_WALL_SIGN), "purpleheart_sign");
	public static final SignItem PURPLEHEART_HANGING_SIGN_ITEM = register(new HangingSignItem(PURPLEHEART_HANGING_SIGN, PURPLEHEART_WALL_HANGING_SIGN, new Item.Settings()), "purpleheart_hanging_sign");
	public static final BlockEntityType<PurpleheartSign> PURPLEHEART_SIGN_BLOCK_ENTITY = registerSignBlockEntity(FabricBlockEntityTypeBuilder.create(PurpleheartSign::new, PURPLEHEART_SIGN, PURPLEHEART_WALL_SIGN).build(), "purpleheart_sign");
	public static final BlockEntityType<PurpleheartHangingSign> PURPLEHEART_HANGING_SIGN_BLOCK_ENTITY = registerSignBlockEntity(FabricBlockEntityTypeBuilder.create(PurpleheartHangingSign::new, PURPLEHEART_HANGING_SIGN, PURPLEHEART_WALL_HANGING_SIGN).build(), "purpleheart_hanging_sign");


	public static SignItem register(SignItem item, String id) {
		SignItem res = Registry.register(Registries.ITEM, id, item);

		ModBlocks.ITEMS.add(res);

		return res;
	}

	public static<T extends BlockEntity> BlockEntityType<T> registerSignBlockEntity(BlockEntityType<T> type, String id) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Util.id(id), type);
	}

	public static Block registerSignBlock(Block block, String id) {
		return Registry.register(Registries.BLOCK, Util.id(id), block);
	}


	public static void init() {
		Trollmod.LOGGER.info("Initializing signs for mod " + Trollmod.MODID);
	}

	public static class PurpleheartSign extends SignBlockEntity {
		public PurpleheartSign(BlockPos pos, BlockState state) {
			super(pos, state);
		}

		@Override
		public BlockEntityType<?> getType() {
			return ModSigns.PURPLEHEART_SIGN_BLOCK_ENTITY;
		}
	}

	public static class PurpleheartHangingSign extends HangingSignBlockEntity {
		public PurpleheartHangingSign(BlockPos pos, BlockState state) {
			super(pos, state);
		}

		@Override
		public BlockEntityType<?> getType() {
			return ModSigns.PURPLEHEART_HANGING_SIGN_BLOCK_ENTITY;
		}
	}
};