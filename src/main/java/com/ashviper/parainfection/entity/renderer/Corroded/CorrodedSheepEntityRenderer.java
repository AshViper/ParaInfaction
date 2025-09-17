package com.ashviper.parainfection.entity.renderer.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedSheepEntity;
import com.ashviper.parainfection.entity.model.Corroded.CorrodedSheepModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CorrodedSheepEntityRenderer extends GeoEntityRenderer<CorrodedSheepEntity> {
    public CorrodedSheepEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CorrodedSheepModel());
        this.shadowRadius = 0.5f;
        this.scaleHeight = 1.5f;
        this.scaleWidth = 1.5f;
    }
}
