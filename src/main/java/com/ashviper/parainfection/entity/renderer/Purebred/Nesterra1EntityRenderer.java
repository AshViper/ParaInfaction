package com.ashviper.parainfection.entity.renderer.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.Nesterra1Entity;
import com.ashviper.parainfection.entity.model.Purebred.Nesterra1Model;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Nesterra1EntityRenderer extends GeoEntityRenderer<Nesterra1Entity> {
    public Nesterra1EntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new Nesterra1Model());
        this.shadowRadius = 0.5f;
        float Scale = 1.0f;
        this.scaleHeight = Scale;
        this.scaleWidth = Scale;
    }
}
