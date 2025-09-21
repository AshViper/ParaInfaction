package com.ashviper.parainfection.entity.renderer.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.WretchspawnEntity;
import com.ashviper.parainfection.entity.model.Purebred.WretchspawnModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WretchspawnRenderer extends GeoEntityRenderer<WretchspawnEntity> {
    public WretchspawnRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WretchspawnModel());
        this.shadowRadius = 0.5f;
        float Scale = 1.0f;
        this.scaleHeight = Scale;
        this.scaleWidth = Scale;
    }
}
