package me.illia.trollmod.entity;

import me.illia.trollmod.Util;
import me.illia.trollmod.client.TrollmodClient;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class HotAirBalloonEntityRenderer extends LivingEntityRenderer<HotAirBalloonEntity, HotAirBalloonEntityModel> {
	public HotAirBalloonEntityRenderer(EntityRendererProvider.Context ctx, HotAirBalloonEntityModel model, float shadowRadius) {
		super(ctx, model, shadowRadius);
		this.addLayer(new HotAirBalloonColorFeatureRenderer(this, ctx.getModelSet()));
	}

	public HotAirBalloonEntityRenderer(EntityRendererProvider.Context ctx) {
		this(ctx, new HotAirBalloonEntityModel(ctx.bakeLayer(TrollmodClient.HOT_AIR_BALLOON_LAYER)), 0.5f);
	}

	@Override
	public ResourceLocation getTexture(HotAirBalloonEntity entity) {
		return Util.id("textures/entity/hot_air_balloon.png");
	}
}
