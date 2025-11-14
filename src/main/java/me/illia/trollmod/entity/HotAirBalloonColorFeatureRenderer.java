package me.illia.trollmod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.illia.trollmod.Util;
import me.illia.trollmod.client.TrollmodClient;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class HotAirBalloonColorFeatureRenderer extends RenderLayer<HotAirBalloonEntity, HotAirBalloonEntityModel> {
	private static final ResourceLocation SKIN = Util.id("textures/entity/hot_air_balloon.png");
	private final HotAirBalloonEntityModel model;
	public HotAirBalloonColorFeatureRenderer(HotAirBalloonEntityRenderer hotAirBalloonEntityRenderer, EntityModelSet modelLoader) {
		super(hotAirBalloonEntityRenderer);
		this.model = new HotAirBalloonEntityModel(modelLoader.bakeLayer(TrollmodClient.HOT_AIR_BALLOON_LAYER));
	}

	@Override
	public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, HotAirBalloonEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		float[] col = HotAirBalloonEntity.COLORS.get(DyeColor.byId(entity.getColor()));
		coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, SKIN, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch, col[0], col[1], col[2]);
	}
}
