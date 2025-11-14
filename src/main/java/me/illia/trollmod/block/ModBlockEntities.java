package me.illia.trollmod.block;

import me.illia.trollmod.Util;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
	public static final BlockEntityType<GhostBlockEntity> GHOST_BLOCK_ENTITY_TYPE = register("ghost_block", GhostBlockEntity::new, ModBlocks.GHOST_BLOCK);
	public static final BlockEntityType<SoundBlockEntity> SOUND_BLOCK_ENTITY_TYPE = register("sound_block", SoundBlockEntity::new, ModBlocks.SOUND_BLOCK);
	public static final BlockEntityType<SpikyBlockEntity> SPIKY_BLOCK_ENTITY_TYPE = register("spiky_block", SpikyBlockEntity::new, ModBlocks.SPIKY_BLOCK);

	private static <T extends BlockEntity> BlockEntityType<T> register(
		String name,
		FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
		Block... blocks
	) {
		ResourceLocation id = Util.id(name);
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
	}
}
