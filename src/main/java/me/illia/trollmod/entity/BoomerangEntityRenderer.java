package me.illia.trollmod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.illia.trollmod.Trollmod;
import me.illia.trollmod.Util;
import me.illia.trollmod.client.TrollmodClient;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BoomerangEntityRenderer extends EntityRenderer<BoomerangEntity> {
	public BoomerangEntityModel model;

	public BoomerangEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new BoomerangEntityModel(context.bakeLayer(TrollmodClient.BOOMERANG_LAYER));
	}

	@Override
	public void render(BoomerangEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();

		matrices.mulPose(Axis.YP.rotationDegrees(180F - yaw));

		model.renderToBuffer(
			matrices,
			vertexConsumers.getBuffer(RenderType.entityCutout(getTexture(entity))),
			light,
			OverlayTexture.NO_OVERLAY,
			1f, 1f, 1f, 1f
		);

		matrices.popPose();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public ResourceLocation getTexture(BoomerangEntity entity) {
		return Util.id("textures/entity/boomerang.png");
	}
}
