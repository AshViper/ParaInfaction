package com.ashviper.parainfection.entity.renderer.Developed;

import com.ashviper.parainfection.entity.custom.Developed.Developed_PlaguelatchEntity;
import com.ashviper.parainfection.entity.model.Developed.Developed_PlaguelatchModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Developed_PlaguelatchRenderer extends GeoEntityRenderer<Developed_PlaguelatchEntity> {
    public Developed_PlaguelatchRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Developed_PlaguelatchModel());
        this.shadowRadius = 0.5f;
        float Scale = 1.0f;
        this.scaleHeight = Scale;
        this.scaleWidth = Scale;
    }

    @Override
    public void render(Developed_PlaguelatchEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float scale = 1.0F;
        if (!entity.isAlive()) {
            // 死亡中は時間に応じて大きくする
            float progress = Math.min(entity.deathTime / 20.0F, 1.0F); // 1秒かけて膨張
            scale += progress * 0.5F; // 最大1.5倍
        }
        poseStack.scale(scale, scale, scale);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}