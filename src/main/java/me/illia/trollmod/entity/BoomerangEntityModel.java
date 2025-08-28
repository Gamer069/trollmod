package me.illia.trollmod.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BoomerangEntityModel extends EntityModel<BoomerangEntity> {
	private final ModelPart bb_main;
	public BoomerangEntityModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -2.0F, -3.0F, 7.0F, 2.0F, 7.0F, new Dilation(0.0F))
		.uv(6, 13).cuboid(3.0F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 17).cuboid(3.0F, -2.0F, -3.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 13).cuboid(3.0F, -2.0F, 2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 9).cuboid(3.0F, -2.0F, 3.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 12).cuboid(3.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 15).cuboid(2.0F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 9).cuboid(1.0F, -2.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(18, 18).cuboid(-4.0F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 19).cuboid(-1.0F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 9).cuboid(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(8, 19).cuboid(-5.0F, -2.0F, -3.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 16).cuboid(-6.0F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 19).cuboid(-5.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 20).cuboid(-5.0F, -2.0F, 3.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 16).cuboid(-6.0F, -2.0F, 2.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 21).cuboid(-4.0F, -2.0F, 4.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 9).cuboid(-3.0F, -2.0F, 4.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 21).cuboid(-1.0F, -2.0F, 4.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 13).cuboid(1.0F, -2.0F, 4.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(4, 22).cuboid(2.0F, -2.0F, 4.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(BoomerangEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}