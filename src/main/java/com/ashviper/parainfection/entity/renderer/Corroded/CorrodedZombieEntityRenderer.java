package com.ashviper.parainfection.entity.renderer.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedZombieEntity;
import com.ashviper.parainfection.entity.model.Corroded.CorrodedZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CorrodedZombieEntityRenderer extends GeoEntityRenderer<CorrodedZombieEntity> {
    public CorrodedZombieEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CorrodedZombieModel());
        this.shadowRadius = 0.5f;
    }
}
