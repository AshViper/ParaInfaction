package com.ashviper.parainfection.entity.renderer;

import com.ashviper.parainfection.entity.custom.DevoraEntity;
import com.ashviper.parainfection.entity.model.DevoraModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@SuppressWarnings("removal")
public class DevoraEntityRenderer extends GeoEntityRenderer<DevoraEntity> {
    public DevoraEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DevoraModel());
        this.shadowRadius = 0.5f;
    }
}
