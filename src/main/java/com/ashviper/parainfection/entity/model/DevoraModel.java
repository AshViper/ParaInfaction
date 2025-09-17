package com.ashviper.parainfection.entity.model;

import com.ashviper.parainfection.entity.custom.DevoraEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class DevoraModel extends GeoModel<DevoraEntity> {
    @Override
    public ResourceLocation getModelResource(DevoraEntity animatable) {
        return new ResourceLocation("parainfection", "geo/devora.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DevoraEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/devora.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DevoraEntity animatable) {
        return new ResourceLocation("parainfection", "animations/devora.animation.json");
    }
}
