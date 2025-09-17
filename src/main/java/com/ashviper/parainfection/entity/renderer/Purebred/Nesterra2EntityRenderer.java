package com.ashviper.parainfection.entity.renderer.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.Nesterra2Entity;
import com.ashviper.parainfection.entity.model.Purebred.Nesterra2Model;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Nesterra2EntityRenderer extends GeoEntityRenderer<Nesterra2Entity> {
    public Nesterra2EntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Nesterra2Model());
        this.shadowRadius = 0.5f;
        float Scale = 1.5f;
        this.scaleHeight = Scale;
        this.scaleWidth = Scale;
    }
}
