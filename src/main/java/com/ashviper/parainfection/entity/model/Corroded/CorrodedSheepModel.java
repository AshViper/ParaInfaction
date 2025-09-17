package com.ashviper.parainfection.entity.model.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedSheepEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class CorrodedSheepModel extends GeoModel<CorrodedSheepEntity> {
    @Override
    public ResourceLocation getModelResource(CorrodedSheepEntity animatable) {
        return new ResourceLocation("parainfection", "geo/corroded/corroded_sheep.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CorrodedSheepEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/corroded/corroded_sheep.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CorrodedSheepEntity animatable) {
        return new ResourceLocation("parainfection", "animations/corroded/corroded_sheep.animation.json");
    }
}
