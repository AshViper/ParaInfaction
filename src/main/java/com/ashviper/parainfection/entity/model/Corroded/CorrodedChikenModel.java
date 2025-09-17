package com.ashviper.parainfection.entity.model.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedChikenEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class CorrodedChikenModel extends GeoModel<CorrodedChikenEntity> {
    @Override
    public ResourceLocation getModelResource(CorrodedChikenEntity animatable) {
        return new ResourceLocation("parainfection", "geo/corroded/corroded_chiken.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CorrodedChikenEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/corroded/corroded_chiken.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CorrodedChikenEntity animatable) {
        return new ResourceLocation("parainfection", "animations/corroded/corroded_chiken.animation.json");
    }
}
