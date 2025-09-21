package com.ashviper.parainfection.entity.model.Developed;

import com.ashviper.parainfection.entity.custom.Developed.Developed_PlaguelatchEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Developed_PlaguelatchModel extends GeoModel<Developed_PlaguelatchEntity> {
    @Override
    public ResourceLocation getModelResource(Developed_PlaguelatchEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("parainfection", "geo/developed/developed_plaguelatch.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Developed_PlaguelatchEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("parainfection", "textures/entity/developed/developed_plaguelatch.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Developed_PlaguelatchEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("parainfection", "animations/developed/developed_plaguelatch.animation.json");
    }
}