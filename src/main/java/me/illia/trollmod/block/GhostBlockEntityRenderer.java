package me.illia.trollmod.block;

import me.illia.trollmod.mixin.BlockRenderManagerAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class GhostBlockEntityRenderer implements BlockEntityRenderer<GhostBlockEntity> {
	private final BlockRenderManager renderManager;

	public GhostBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		renderManager = ctx.getRenderManager();
	}

	@Override
	public void render(GhostBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (entity.getBlock() != null) {
//			BlockState state = entity.getBlock().getPlacementState(
//				new ItemPlacementContext(
//					entity.getWorld(),
//					entity.getOwner(),
//					entity.getOwner().getActiveHand(),
//					entity.getBlock().asItem().getDefaultStack(),
//					new BlockHitResult(
//						entity.getPos().toCenterPos(),
//						entity.getOwner().getHorizontalFacing(),
//						entity.getPos(),
//						false
//					)
//				)
//			);

			BlockState state = entity.getBlock().getDefaultState();

			renderBlockAsEntity(state, entity.getWorld(), entity.getPos(), matrices, vertexConsumers, light, overlay);
		}
	}

	public void renderBlockAsEntity(BlockState state, World world, BlockPos pos, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		BlockRenderType blockRenderType = state.getRenderType();
		BlockRenderManagerAccessor accessor = (BlockRenderManagerAccessor)renderManager;
		if (blockRenderType != BlockRenderType.INVISIBLE) {
			switch (blockRenderType) {
				case MODEL:
					BakedModel bakedModel = renderManager.getModel(state);
					int i = accessor.trollmod$blockColors().getColor(state, world, pos, 0);
					float f = (i >> 16 & 0xFF) / 255.0F;
					float g = (i >> 8 & 0xFF) / 255.0F;
					float h = (i & 0xFF) / 255.0F;
					accessor.trollmod$blockModelRenderer()
						.render(matrices.peek(), vertexConsumers.getBuffer(RenderLayers.getBlockLayer(state)), state, bakedModel, f, g, h, light, overlay);
					break;
				case ENTITYBLOCK_ANIMATED:
					accessor.trollmod$builtinModelItemRenderer().render(new ItemStack(state.getBlock()), ModelTransformationMode.NONE, matrices, vertexConsumers, light, overlay);
			}
		}
	}
}
