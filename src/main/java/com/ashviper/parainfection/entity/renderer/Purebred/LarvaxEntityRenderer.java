package com.ashviper.parainfection.entity.renderer.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.GnawlingEntity;
import com.ashviper.parainfection.entity.custom.Purebred.LarvaxEntity;
import com.ashviper.parainfection.entity.model.Purebred.GnawlingModel;
import com.ashviper.parainfection.entity.model.Purebred.LarvaxModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@SuppressWarnings("removal")
public class LarvaxEntityRenderer extends GeoEntityRenderer<LarvaxEntity> {
    public LarvaxEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new LarvaxModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(LarvaxEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        // Y軸に 90 度回転
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        poseStack.popPose();
    }
}
