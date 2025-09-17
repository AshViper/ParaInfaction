package com.ashviper.parainfection.entity.renderer.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedCowEntity;
import com.ashviper.parainfection.entity.model.Corroded.CorrodedCowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CorrodedCowEntityRenderer extends GeoEntityRenderer<CorrodedCowEntity> {
    public CorrodedCowEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CorrodedCowModel());
        this.shadowRadius = 0.5f;
        this.scaleHeight = 1.5f;
        this.scaleWidth = 1.5f;
    }
}
