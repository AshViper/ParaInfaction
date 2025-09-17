package com.ashviper.parainfection.entity.model.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedCowEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class CorrodedCowModel extends GeoModel<CorrodedCowEntity> {
    @Override
    public ResourceLocation getModelResource(CorrodedCowEntity animatable) {
        return new ResourceLocation("parainfection", "geo/corroded/corroded_cow.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CorrodedCowEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/corroded/corroded_cow.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CorrodedCowEntity animatable) {
        return new ResourceLocation("parainfection", "animations/corroded/corroded_cow.animation.json");
    }
}
