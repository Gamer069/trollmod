package me.illia.trollmod.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;

public class HotAirBalloonEntityModel extends EntityModel<HotAirBalloonEntity> {
	private final ModelPart seat;
	private final ModelPart walls;
	private final ModelPart ropes;
	private final ModelPart rope1;
	private final ModelPart rope2;
	private final ModelPart rope3;
	private final ModelPart rope4;
	private final ModelPart balloon;

	public HotAirBalloonEntityModel(ModelPart root) {
		this.seat = root.getChild("seat");
		this.walls = this.seat.getChild("walls");
		this.ropes = root.getChild("ropes");
		this.rope1 = this.ropes.getChild("rope1");
		this.rope2 = this.ropes.getChild("rope2");
		this.rope3 = this.ropes.getChild("rope3");
		this.rope4 = this.ropes.getChild("rope4");
		this.balloon = root.getChild("balloon");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData seat = modelPartData.addChild("seat", ModelPartBuilder.create().uv(0, 76).cuboid(-12.0F, -2.0F, -12.0F, 24.0F, 2.0F, 24.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData walls = seat.addChild("walls", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData wall_r1 = walls.addChild("wall_r1", ModelPartBuilder.create().uv(192, 95).cuboid(2.0F, -2.0F, -12.0F, 20.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(12.0F, -2.0F, -12.0F, 0.0F, -1.5708F, -1.5708F));

		ModelPartData wall_r2 = walls.addChild("wall_r2", ModelPartBuilder.create().uv(192, 81).cuboid(2.0F, -2.0F, -12.0F, 20.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, -2.0F, -12.0F, 0.0F, -1.5708F, -1.5708F));

		ModelPartData wall_r3 = walls.addChild("wall_r3", ModelPartBuilder.create().uv(192, 67).cuboid(0.0F, -2.0F, -12.0F, 24.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-12.0F, -2.0F, 10.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData wall_r4 = walls.addChild("wall_r4", ModelPartBuilder.create().uv(192, 53).cuboid(0.0F, -2.0F, -12.0F, 24.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-12.0F, -2.0F, -12.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData ropes = modelPartData.addChild("ropes", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData rope1 = ropes.addChild("rope1", ModelPartBuilder.create().uv(88, 72).cuboid(-1.0F, -14.0F, -14.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(136, 64).cuboid(-1.0F, -16.0F, -15.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(136, 68).cuboid(-1.0F, -18.0F, -14.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 145).cuboid(-1.0F, -22.0F, -14.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 125).cuboid(-1.0F, -20.0F, -15.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 149).cuboid(-2.0F, -24.0F, -15.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 189).cuboid(-3.0F, -26.0F, -15.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(13.5F, 0.0F, -13.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData rope2 = ropes.addChild("rope2", ModelPartBuilder.create().uv(184, 53).cuboid(-1.0F, -14.0F, -14.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(184, 57).cuboid(-1.0F, -16.0F, -15.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 109).cuboid(-1.0F, -18.0F, -14.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 141).cuboid(-1.0F, -22.0F, -14.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 129).cuboid(-1.0F, -20.0F, -15.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 153).cuboid(-2.0F, -24.0F, -15.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 185).cuboid(-3.0F, -26.0F, -15.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-13.5F, 0.0F, 13.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData rope3 = ropes.addChild("rope3", ModelPartBuilder.create().uv(192, 113).cuboid(-14.0F, -14.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 117).cuboid(-14.0F, -16.0F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 121).cuboid(-14.0F, -18.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 137).cuboid(-14.0F, -22.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 133).cuboid(-14.0F, -20.0F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 157).cuboid(-13.0F, -24.0F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 197).cuboid(-12.0F, -26.0F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData rope4 = ropes.addChild("rope4", ModelPartBuilder.create().uv(192, 161).cuboid(-14.0F, -14.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 165).cuboid(-14.0F, -16.0F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 169).cuboid(-14.0F, -18.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 173).cuboid(-14.0F, -22.0F, -1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 177).cuboid(-14.0F, -20.0F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 181).cuboid(-13.0F, -24.0F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(192, 193).cuboid(-12.0F, -26.0F, -2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

		ModelPartData balloon = modelPartData.addChild("balloon", ModelPartBuilder.create().uv(0, 0).cuboid(-11.0F, -80.0F, -11.0F, 22.0F, 54.0F, 22.0F, new Dilation(0.0F))
		.uv(88, 0).cuboid(11.0F, -78.0F, -11.0F, 2.0F, 50.0F, 22.0F, new Dilation(0.0F))
		.uv(96, 144).cuboid(-19.0F, -72.0F, -11.0F, 2.0F, 38.0F, 22.0F, new Dilation(0.0F))
		.uv(136, 0).cuboid(-17.0F, -74.0F, -11.0F, 2.0F, 42.0F, 22.0F, new Dilation(0.0F))
		.uv(0, 102).cuboid(-15.0F, -76.0F, -11.0F, 2.0F, 46.0F, 22.0F, new Dilation(0.0F))
		.uv(96, 72).cuboid(-13.0F, -78.0F, -11.0F, 2.0F, 50.0F, 22.0F, new Dilation(0.0F))
		.uv(48, 102).cuboid(13.0F, -76.0F, -11.0F, 2.0F, 46.0F, 22.0F, new Dilation(0.0F))
		.uv(144, 64).cuboid(15.0F, -74.0F, -11.0F, 2.0F, 42.0F, 22.0F, new Dilation(0.0F))
		.uv(144, 128).cuboid(17.0F, -72.0F, -11.0F, 2.0F, 38.0F, 22.0F, new Dilation(0.0F))
		.uv(184, 0).cuboid(-23.0F, -68.0F, -11.0F, 2.0F, 31.0F, 22.0F, new Dilation(0.0F))
		.uv(0, 170).cuboid(-21.0F, -70.0F, -11.0F, 2.0F, 35.0F, 22.0F, new Dilation(0.0F))
		.uv(144, 188).cuboid(21.0F, -68.0F, -11.0F, 2.0F, 30.0F, 22.0F, new Dilation(0.0F))
		.uv(48, 170).cuboid(19.0F, -70.0F, -11.0F, 2.0F, 34.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 512, 512);
	}

	@Override
	public void setAngles(HotAirBalloonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		seat.render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);
		ropes.render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);
		balloon.render(matrices, vertexConsumer, light, overlay, red, green, blue, 1);
	}
}