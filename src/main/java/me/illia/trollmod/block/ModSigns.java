package me.illia.trollmod.block;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import me.illia.trollmod.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSigns {
	public static final Block PURPLEHEART_SIGN = registerSignBlock(
		new StandingSignBlock(
			FabricBlockSettings.copyOf(Blocks.OAK_SIGN),
			ModWoodTypes.PURPLEHEART
		) {
			@Override
			public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
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
			public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
				return new PurpleheartSign(pos, state);
			}
		},
		"purpleheart_wall_sign"
	);

	public static final Block PURPLEHEART_HANGING_SIGN = registerSignBlock(
		new StandingSignBlock(
			FabricBlockSettings.copyOf(
				Blocks.OAK_HANGING_SIGN
			),
			ModWoodTypes.PURPLEHEART
		) {
			@Override
			public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
				return new PurpleheartHangingSign(pos, state);
			}
		},
		"purpleheart_hanging_sign"
	);

	public static final Block PURPLEHEART_WALL_HANGING_SIGN = registerSignBlock(
		new StandingSignBlock(
			FabricBlockSettings.copyOf(
				Blocks.OAK_WALL_HANGING_SIGN
			),
			ModWoodTypes.PURPLEHEART
		) {
			@Override
			public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
				return new PurpleheartHangingSign(pos, state);
			}
		},
		"purpleheart_wall_hanging_sign"
	);

	public static final SignItem PURPLEHEART_SIGN_ITEM = register(new SignItem(new Item.Properties(), PURPLEHEART_SIGN, PURPLEHEART_WALL_SIGN), "purpleheart_sign");
	public static final SignItem PURPLEHEART_HANGING_SIGN_ITEM = register(new HangingSignItem(PURPLEHEART_HANGING_SIGN, PURPLEHEART_WALL_HANGING_SIGN, new Item.Properties()), "purpleheart_hanging_sign");
	public static final BlockEntityType<PurpleheartSign> PURPLEHEART_SIGN_BLOCK_ENTITY = registerSignBlockEntity(FabricBlockEntityTypeBuilder.create(PurpleheartSign::new, PURPLEHEART_SIGN, PURPLEHEART_WALL_SIGN).build(), "purpleheart_sign");
	public static final BlockEntityType<PurpleheartHangingSign> PURPLEHEART_HANGING_SIGN_BLOCK_ENTITY = registerSignBlockEntity(FabricBlockEntityTypeBuilder.create(PurpleheartHangingSign::new, PURPLEHEART_HANGING_SIGN, PURPLEHEART_WALL_HANGING_SIGN).build(), "purpleheart_hanging_sign");


	public static SignItem register(SignItem item, String id) {
		SignItem res = Registry.register(BuiltInRegistries.ITEM, id, item);

		ModBlocks.ITEMS.add(res);

		return res;
	}

	public static<T extends BlockEntity> BlockEntityType<T> registerSignBlockEntity(BlockEntityType<T> type, String id) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Util.id(id), type);
	}

	public static Block registerSignBlock(Block block, String id) {
		return Registry.register(BuiltInRegistries.BLOCK, Util.id(id), block);
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