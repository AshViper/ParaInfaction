package com.ashviper.parainfection.entity.renderer.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.IncompletefieldEntity;
import com.ashviper.parainfection.entity.model.Purebred.IncompletefieldModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IncompletefieldRenderer extends GeoEntityRenderer<IncompletefieldEntity> {
    public IncompletefieldRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new IncompletefieldModel());
        this.shadowRadius = 0.5f;
        this.scaleHeight = 1.5f;
        this.scaleWidth = 1.5f;
    }
}
