package me.illia.trollmod.entity;

import me.illia.trollmod.Util;
import me.illia.trollmod.client.TrollmodClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class HotAirBalloonColorFeatureRenderer extends FeatureRenderer<HotAirBalloonEntity, HotAirBalloonEntityModel> {
	private static final Identifier SKIN = Util.id("textures/entity/hot_air_balloon.png");
	private final HotAirBalloonEntityModel model;
	public HotAirBalloonColorFeatureRenderer(HotAirBalloonEntityRenderer hotAirBalloonEntityRenderer, EntityModelLoader modelLoader) {
		super(hotAirBalloonEntityRenderer);
		this.model = new HotAirBalloonEntityModel(modelLoader.getModelPart(TrollmodClient.HOT_AIR_BALLOON_LAYER));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, HotAirBalloonEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		float[] col = HotAirBalloonEntity.COLORS.get(DyeColor.byId(entity.getColor()));
		render(this.getContextModel(), this.model, SKIN, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch, col[0], col[1], col[2]);
	}
}
