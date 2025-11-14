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

// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BoomerangEntityModel extends EntityModel<BoomerangEntity> {
	private final ModelPart bb_main;

	public BoomerangEntityModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition bb_main = modelPartData.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -3.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
			.texOffs(6, 13).addBox(3.0F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 17).addBox(3.0F, -2.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(12, 13).addBox(3.0F, -2.0F, 2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(18, 9).addBox(3.0F, -2.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(18, 12).addBox(3.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(18, 15).addBox(2.0F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 9).addBox(1.0F, -2.0F, -5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(18, 18).addBox(-4.0F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(4, 19).addBox(-1.0F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(6, 9).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(8, 19).addBox(-5.0F, -2.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(6, 16).addBox(-6.0F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(12, 19).addBox(-5.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 20).addBox(-5.0F, -2.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(12, 16).addBox(-6.0F, -2.0F, 2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(16, 21).addBox(-4.0F, -2.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(12, 9).addBox(-3.0F, -2.0F, 4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(20, 21).addBox(-1.0F, -2.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(0, 13).addBox(1.0F, -2.0F, 4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
			.texOffs(4, 22).addBox(2.0F, -2.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4F, 0.0F));
		return LayerDefinition.create(modelData, 32, 32);
	}

	@Override
	public void setAngles(BoomerangEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}