package com.meacks.miyaworld.entity.render;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.BulletEntity;
import com.meacks.miyaworld.entity.model.BulletModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BulletRender extends EntityRenderer<BulletEntity> {
    private final EntityModel<BulletEntity> bulletEntityEntityModel;
    public BulletRender(EntityRendererManager rendererManager) {
        super(rendererManager);
        bulletEntityEntityModel = new BulletModel();
    }

    @Override
    public ResourceLocation getTextureLocation(BulletEntity bulletEntity) {
        return new ResourceLocation(MiyaWorld.MODID, "textures/entity/robot_laser.png");
    }

    @Override
    public void render(BulletEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.bulletEntityEntityModel.renderType(this.getTextureLocation(entityIn)));
        this.bulletEntityEntityModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
