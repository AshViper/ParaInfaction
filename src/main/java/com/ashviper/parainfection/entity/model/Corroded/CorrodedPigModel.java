package com.ashviper.parainfection.entity.model.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedPigEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class CorrodedPigModel extends GeoModel<CorrodedPigEntity> {
    @Override
    public ResourceLocation getModelResource(CorrodedPigEntity animatable) {
        return new ResourceLocation("parainfection", "geo/corroded/corroded_pig.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CorrodedPigEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/corroded/corroded_pig.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CorrodedPigEntity animatable) {
        return new ResourceLocation("parainfection", "animations/corroded/corroded_pig.animation.json");
    }
}
