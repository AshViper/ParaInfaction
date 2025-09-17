package com.ashviper.parainfection.entity.model.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedZombieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class CorrodedZombieModel extends GeoModel<CorrodedZombieEntity> {
    @Override
    public ResourceLocation getModelResource(CorrodedZombieEntity animatable) {
        return new ResourceLocation("parainfection", "geo/corroded/corroded_zombie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CorrodedZombieEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/corroded/corroded_zombie.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CorrodedZombieEntity animatable) {
        return new ResourceLocation("parainfection", "animations/corroded/corroded_zombie.animation.json");
    }
}
