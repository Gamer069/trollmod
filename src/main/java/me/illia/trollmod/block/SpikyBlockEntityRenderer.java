package me.illia.trollmod.block;

import com.mojang.blaze3d.vertex.PoseStack;
import me.illia.trollmod.mixin.BlockRenderManagerAccessor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class SpikyBlockEntityRenderer implements BlockEntityRenderer<SpikyBlockEntity> {
	private final BlockRenderDispatcher renderManager;

	public SpikyBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		renderManager = ctx.getBlockRenderDispatcher();
	}

	@Override
	public void render(SpikyBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		if (entity.getBlock() != null) {
			BlockState state = entity.getBlock();

			renderBlockAsEntity(state, entity.getLevel(), entity.getBlockPos(), matrices, vertexConsumers, light, overlay);
		}
	}

	public void renderBlockAsEntity(BlockState state, Level world, BlockPos pos, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		RenderShape blockRenderType = state.getRenderShape();
		BlockRenderManagerAccessor accessor = (BlockRenderManagerAccessor)renderManager;
		if (blockRenderType != RenderShape.INVISIBLE) {
			switch (blockRenderType) {
				case MODEL:
					BakedModel bakedModel = renderManager.getBlockModel(state);
					int i = accessor.trollmod$blockColors().getColor(state, world, pos, 0);
					float f = (i >> 16 & 0xFF) / 255.0F;
					float g = (i >> 8 & 0xFF) / 255.0F;
					float h = (i & 0xFF) / 255.0F;
					accessor.trollmod$blockModelRenderer()
						.renderModel(matrices.last(), vertexConsumers.getBuffer(ItemBlockRenderTypes.getChunkRenderType(state)), state, bakedModel, f, g, h, light, overlay);
					break;
				case ENTITYBLOCK_ANIMATED:
					accessor.trollmod$builtinModelItemRenderer().renderByItem(new ItemStack(state.getBlock()), ItemDisplayContext.NONE, matrices, vertexConsumers, light, overlay);
			}
		}
	}
}
