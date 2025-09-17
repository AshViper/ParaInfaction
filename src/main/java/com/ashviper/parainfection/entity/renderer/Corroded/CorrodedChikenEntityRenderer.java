package com.ashviper.parainfection.entity.renderer.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedChikenEntity;
import com.ashviper.parainfection.entity.model.Corroded.CorrodedChikenModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CorrodedChikenEntityRenderer extends GeoEntityRenderer<CorrodedChikenEntity> {
    public CorrodedChikenEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CorrodedChikenModel());
        this.shadowRadius = 0.5f;
    }
}
