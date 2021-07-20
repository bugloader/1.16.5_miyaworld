package com.meacks.miyaworld.entity.render;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.GiantRobot;
import com.meacks.miyaworld.entity.RobotLaser;
import com.meacks.miyaworld.entity.model.RobotLaserModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RobotLaserRender extends EntityRenderer<RobotLaser> {
    private final EntityModel<RobotLaser> robotLaserModelEntityModel;
    public RobotLaserRender(EntityRendererManager rendererManager) {
        super(rendererManager);
        robotLaserModelEntityModel = new RobotLaserModel();
    }

    @Override
    public ResourceLocation getTextureLocation(RobotLaser robotLaser) {
        return new ResourceLocation(MiyaWorld.MODID, "textures/entity/robot_laser.png");
    }

    @Override
    public void render(RobotLaser entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.robotLaserModelEntityModel.renderType(this.getTextureLocation(entityIn)));
        this.robotLaserModelEntityModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
