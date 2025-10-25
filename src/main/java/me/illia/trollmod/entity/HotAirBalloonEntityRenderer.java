package me.illia.trollmod.entity;

import me.illia.trollmod.Util;
import me.illia.trollmod.client.TrollmodClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;

public class HotAirBalloonEntityRenderer extends LivingEntityRenderer<HotAirBalloonEntity, HotAirBalloonEntityModel> {
	public HotAirBalloonEntityRenderer(EntityRendererFactory.Context ctx, HotAirBalloonEntityModel model, float shadowRadius) {
		super(ctx, model, shadowRadius);
		this.addFeature(new HotAirBalloonColorFeatureRenderer(this, ctx.getModelLoader()));
	}

	public HotAirBalloonEntityRenderer(EntityRendererFactory.Context ctx) {
		this(ctx, new HotAirBalloonEntityModel(ctx.getPart(TrollmodClient.HOT_AIR_BALLOON_LAYER)), 0.5f);
	}

	@Override
	public Identifier getTexture(HotAirBalloonEntity entity) {
		return Util.id("textures/entity/hot_air_balloon.png");
	}
}
