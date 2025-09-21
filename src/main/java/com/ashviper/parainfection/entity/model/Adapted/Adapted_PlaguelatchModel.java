package com.ashviper.parainfection.entity.model.Adapted;

import com.ashviper.parainfection.entity.custom.Adapted.Adapted_PlaguelatchEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Adapted_PlaguelatchModel extends GeoModel<Adapted_PlaguelatchEntity> {
    @Override
    public ResourceLocation getModelResource(Adapted_PlaguelatchEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("parainfection", "geo/adapted/adapted_plaguelatch.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Adapted_PlaguelatchEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("parainfection", "textures/entity/adapted/adapted_plaguelatch.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Adapted_PlaguelatchEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("parainfection", "animations/adapted/adapted_plaguelatch.animation.json");
    }
}