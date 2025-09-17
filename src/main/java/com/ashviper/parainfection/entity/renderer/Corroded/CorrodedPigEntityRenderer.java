package com.ashviper.parainfection.entity.renderer.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedPigEntity;
import com.ashviper.parainfection.entity.model.Corroded.CorrodedPigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CorrodedPigEntityRenderer extends GeoEntityRenderer<CorrodedPigEntity> {
    public CorrodedPigEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CorrodedPigModel());
        this.shadowRadius = 0.5f;
        this.scaleHeight = 2.5f;
        this.scaleWidth = 2.5f;
    }
}
