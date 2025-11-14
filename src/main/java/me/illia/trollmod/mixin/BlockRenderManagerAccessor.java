package me.illia.trollmod.mixin;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockRenderDispatcher.class)
public interface BlockRenderManagerAccessor {
	@Accessor("blockEntityRenderer")
	BlockEntityWithoutLevelRenderer trollmod$builtinModelItemRenderer();

	@Accessor("modelRenderer")
	ModelBlockRenderer trollmod$blockModelRenderer();

	@Accessor("blockColors")
	BlockColors trollmod$blockColors();
}
