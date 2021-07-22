package com.meacks.miyaworld.entity.model;

import com.meacks.miyaworld.entity.BulletEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BulletModel extends EntityModel<BulletEntity> {
    private final ModelRenderer body;

    public BulletModel() {
        super(RenderType::entityTranslucent);
        this.texWidth = 8;
        this.texHeight = 8;
        body = new ModelRenderer(this,0,35);
        body.addBox(-1f,-2f,-1f,2,2,2,0.0f);
        body.setPos(0,0,0);
    }

    @Override
    public void setupAnim(BulletEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        this.body.xRot = p_225597_2_;
        this.body.yRot = p_225597_3_;
        this.body.zRot = p_225597_4_;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn,red,green,blue,alpha);
    }
}
