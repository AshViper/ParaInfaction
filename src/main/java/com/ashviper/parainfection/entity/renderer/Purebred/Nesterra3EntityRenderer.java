package com.ashviper.parainfection.entity.renderer.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.Nesterra3Entity;
import com.ashviper.parainfection.entity.model.Purebred.Nesterra3Model;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Nesterra3EntityRenderer extends GeoEntityRenderer<Nesterra3Entity> {
    public Nesterra3EntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Nesterra3Model());
        this.shadowRadius = 0.5f;
        float Scale = 2.0f;
        this.scaleHeight = Scale;
        this.scaleWidth = Scale;
    }
}
