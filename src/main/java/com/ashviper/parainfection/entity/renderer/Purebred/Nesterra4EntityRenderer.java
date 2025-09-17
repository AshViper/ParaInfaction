package com.ashviper.parainfection.entity.renderer.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.Nesterra4Entity;
import com.ashviper.parainfection.entity.model.Purebred.Nesterra4Model;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Nesterra4EntityRenderer extends GeoEntityRenderer<Nesterra4Entity> {
    public Nesterra4EntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Nesterra4Model());
        this.shadowRadius = 0.5f;
        float Scale = 3.0f;
        this.scaleHeight = Scale;
        this.scaleWidth = Scale;
    }
}
