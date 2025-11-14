package me.illia.trollmod.block;

import me.illia.trollmod.Util;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
	public static final BlockEntityType<GhostBlockEntity> GHOST_BLOCK_ENTITY_TYPE = register("ghost_block", GhostBlockEntity::new, ModBlocks.GHOST_BLOCK);
	public static final BlockEntityType<SoundBlockEntity> SOUND_BLOCK_ENTITY_TYPE = register("sound_block", SoundBlockEntity::new, ModBlocks.SOUND_BLOCK);
	public static final BlockEntityType<SpikyBlockEntity> SPIKY_BLOCK_ENTITY_TYPE = register("spiky_block", SpikyBlockEntity::new, ModBlocks.SPIKY_BLOCK);

	private static <T extends BlockEntity> BlockEntityType<T> register(
		String name,
		FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
		Block... blocks
	) {
		Identifier id = Util.id(name);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
	}
}
