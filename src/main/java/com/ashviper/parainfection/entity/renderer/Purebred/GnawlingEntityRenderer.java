package com.ashviper.parainfection.entity.renderer.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.GnawlingEntity;
import com.ashviper.parainfection.entity.model.Purebred.GnawlingModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@SuppressWarnings("removal")
public class GnawlingEntityRenderer extends GeoEntityRenderer<GnawlingEntity> {
    public GnawlingEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GnawlingModel());
        this.shadowRadius = 0.5f;
        this.scaleHeight = 1.5f;
        this.scaleWidth = 1.5f;
    }
}
