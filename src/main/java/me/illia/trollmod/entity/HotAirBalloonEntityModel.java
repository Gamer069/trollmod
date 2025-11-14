package me.illia.trollmod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

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
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition seat = modelPartData.addOrReplaceChild("seat", CubeListBuilder.create().texOffs(0, 76).addBox(-12.0F, -2.0F, -12.0F, 24.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition walls = seat.addOrReplaceChild("walls", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wall_r1 = walls.addOrReplaceChild("wall_r1", CubeListBuilder.create().texOffs(192, 95).addBox(2.0F, -2.0F, -12.0F, 20.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -2.0F, -12.0F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition wall_r2 = walls.addOrReplaceChild("wall_r2", CubeListBuilder.create().texOffs(192, 81).addBox(2.0F, -2.0F, -12.0F, 20.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -2.0F, -12.0F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition wall_r3 = walls.addOrReplaceChild("wall_r3", CubeListBuilder.create().texOffs(192, 67).addBox(0.0F, -2.0F, -12.0F, 24.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -2.0F, 10.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition wall_r4 = walls.addOrReplaceChild("wall_r4", CubeListBuilder.create().texOffs(192, 53).addBox(0.0F, -2.0F, -12.0F, 24.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -2.0F, -12.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition ropes = modelPartData.addOrReplaceChild("ropes", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition rope1 = ropes.addOrReplaceChild("rope1", CubeListBuilder.create().texOffs(88, 72).addBox(-1.0F, -14.0F, -14.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(136, 64).addBox(-1.0F, -16.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(136, 68).addBox(-1.0F, -18.0F, -14.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 145).addBox(-1.0F, -22.0F, -14.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 125).addBox(-1.0F, -20.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 149).addBox(-2.0F, -24.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 189).addBox(-3.0F, -26.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.5F, 0.0F, -13.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rope2 = ropes.addOrReplaceChild("rope2", CubeListBuilder.create().texOffs(184, 53).addBox(-1.0F, -14.0F, -14.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(184, 57).addBox(-1.0F, -16.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 109).addBox(-1.0F, -18.0F, -14.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 141).addBox(-1.0F, -22.0F, -14.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 129).addBox(-1.0F, -20.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 153).addBox(-2.0F, -24.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 185).addBox(-3.0F, -26.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.5F, 0.0F, 13.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition rope3 = ropes.addOrReplaceChild("rope3", CubeListBuilder.create().texOffs(192, 113).addBox(-14.0F, -14.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 117).addBox(-14.0F, -16.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 121).addBox(-14.0F, -18.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 137).addBox(-14.0F, -22.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 133).addBox(-14.0F, -20.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 157).addBox(-13.0F, -24.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 197).addBox(-12.0F, -26.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition rope4 = ropes.addOrReplaceChild("rope4", CubeListBuilder.create().texOffs(192, 161).addBox(-14.0F, -14.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 165).addBox(-14.0F, -16.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 169).addBox(-14.0F, -18.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 173).addBox(-14.0F, -22.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 177).addBox(-14.0F, -20.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 181).addBox(-13.0F, -24.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(192, 193).addBox(-12.0F, -26.0F, -2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition balloon = modelPartData.addOrReplaceChild("balloon", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -80.0F, -11.0F, 22.0F, 54.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(88, 0).addBox(11.0F, -78.0F, -11.0F, 2.0F, 50.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(96, 144).addBox(-19.0F, -72.0F, -11.0F, 2.0F, 38.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(136, 0).addBox(-17.0F, -74.0F, -11.0F, 2.0F, 42.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 102).addBox(-15.0F, -76.0F, -11.0F, 2.0F, 46.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(96, 72).addBox(-13.0F, -78.0F, -11.0F, 2.0F, 50.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(48, 102).addBox(13.0F, -76.0F, -11.0F, 2.0F, 46.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(144, 64).addBox(15.0F, -74.0F, -11.0F, 2.0F, 42.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(144, 128).addBox(17.0F, -72.0F, -11.0F, 2.0F, 38.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(184, 0).addBox(-23.0F, -68.0F, -11.0F, 2.0F, 31.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 170).addBox(-21.0F, -70.0F, -11.0F, 2.0F, 35.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(144, 188).addBox(21.0F, -68.0F, -11.0F, 2.0F, 30.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(48, 170).addBox(19.0F, -70.0F, -11.0F, 2.0F, 34.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(modelData, 512, 512);
	}

	@Override
	public void setAngles(HotAirBalloonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		seat.render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);
		ropes.render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);
		balloon.render(matrices, vertexConsumer, light, overlay, red, green, blue, 1);
	}
}