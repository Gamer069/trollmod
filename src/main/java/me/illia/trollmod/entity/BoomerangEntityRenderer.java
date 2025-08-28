package me.illia.trollmod.entity;

import me.illia.trollmod.Trollmod;
import me.illia.trollmod.client.TrollmodClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BoomerangEntityRenderer extends EntityRenderer<BoomerangEntity> {
	public BoomerangEntityModel model;

	public BoomerangEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.model = new BoomerangEntityModel(context.getPart(TrollmodClient.BOOMERANG_LAYER));
	}

	@Override
	public void render(BoomerangEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();

		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180F - yaw));

		model.render(
			matrices,
			vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(entity))),
			light,
			OverlayTexture.DEFAULT_UV,
			1f, 1f, 1f, 1f
		);

		matrices.pop();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(BoomerangEntity entity) {
		return Identifier.of(Trollmod.MODID, "textures/entity/boomerang.png");
	}
}
