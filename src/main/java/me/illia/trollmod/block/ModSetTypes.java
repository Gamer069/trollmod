package me.illia.trollmod.block;

import com.google.common.collect.Maps;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;

import java.util.Map;

public class ModSetTypes {
	private static final Map<Block, BlockFamily> BASE_BLOCKS_TO_FAMILIES = Maps.newHashMap();

	public static final BlockSetType PURPLEHEART = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(Util.id("purpleheart"));
	public static final BlockFamily PURPLEHEART_FAMILY = register(ModBlocks.PURPLEHEART_PLANKS)
		.button(ModBlocks.PURPLEHEART_BUTTON)
		.fence(ModBlocks.PURPLEHEART_FENCE)
		.fenceGate(ModBlocks.PURPLEHEART_FENCE_GATE)
		.pressurePlate(ModBlocks.PURPLEHEART_PRESSURE_PLATE)
		.sign(ModSigns.PURPLEHEART_SIGN, ModSigns.PURPLEHEART_WALL_SIGN)
		.slab(ModBlocks.PURPLEHEART_SLAB)
		.stairs(ModBlocks.PURPLEHEART_STAIRS)
		.door(ModBlocks.PURPLEHEART_DOOR)
		.trapdoor(ModBlocks.PURPLEHEART_TRAPDOOR)
		.group("wooden")
		.unlockCriterionName("has_planks")
		.build();

	public static BlockFamily.Builder register(Block baseBlock) {
		BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
		BlockFamily blockFamily = BASE_BLOCKS_TO_FAMILIES.put(baseBlock, builder.build());
		if (blockFamily != null) {
			throw new IllegalStateException("Duplicate family definition for " + Registries.BLOCK.getId(baseBlock));
		} else {
			return builder;
		}
	}

	public static void init() {
		Trollmod.LOGGER.info("Initializing set types for mod " + Trollmod.MODID);
	}
}
