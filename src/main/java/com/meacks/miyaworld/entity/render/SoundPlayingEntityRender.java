package com.meacks.miyaworld.entity.render;

import com.meacks.miyaworld.MiyaWorld;
import com.meacks.miyaworld.entity.RobotLaser;
import com.meacks.miyaworld.entity.SoundPlayingEntity;
import com.meacks.miyaworld.entity.model.RobotLaserModel;
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
public class SoundPlayingEntityRender extends EntityRenderer<SoundPlayingEntity> {
    public SoundPlayingEntityRender(EntityRendererManager rendererManager) {
        super(rendererManager);
    }

    @Override
    public ResourceLocation getTextureLocation(SoundPlayingEntity p_110775_1_) {
        return null;
    }

    @Override
    public void render(SoundPlayingEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }


}
