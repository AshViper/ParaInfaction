package com.ashviper.parainfection.entity.renderer.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedCreeperEntity;
import com.ashviper.parainfection.entity.model.Corroded.CorrodedCreeperModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CorrodedCreeperEntityRenderer extends GeoEntityRenderer<CorrodedCreeperEntity> {
    public CorrodedCreeperEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CorrodedCreeperModel());
        this.shadowRadius = 0.5f;
    }
}
