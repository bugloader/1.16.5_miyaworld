package com.meacks.miyaworld.entity.render;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.GiantRobot;
import com.meacks.miyaworld.entity.model.GiantRobotModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolemCracksLayer;
import net.minecraft.client.renderer.entity.layers.IronGolenFlowerLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantRobotRender extends MobRenderer<GiantRobot, GiantRobotModel<GiantRobot>> {
    private final EntityModel<GiantRobot> robotEntityModel;

    public GiantRobotRender(EntityRendererManager rendererManager) {
        super(rendererManager,new GiantRobotModel<>(),0.7F);
        robotEntityModel = new IronGolemModel<>();
    }

    @Override
    public ResourceLocation getTextureLocation(GiantRobot giantRobot) {
        return new ResourceLocation(MiyaWorld.MODID, "textures/entity/giant_robot.png");
    }

    protected void setupRotations(GiantRobot p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
        super.setupRotations(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
        if (!((double)p_225621_1_.animationSpeed < 0.01D)) {
            float f = 13.0F;
            float f1 = p_225621_1_.animationPosition - p_225621_1_.animationSpeed * (1.0F - p_225621_5_) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            p_225621_2_.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
        }
    }
}
