package com.ashviper.parainfection.entity.model.Corroded;

import com.ashviper.parainfection.entity.custom.Corroded.CorrodedCreeperEntity;
import com.ashviper.parainfection.entity.custom.Corroded.CorrodedZombieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class CorrodedCreeperModel extends GeoModel<CorrodedCreeperEntity> {
    @Override
    public ResourceLocation getModelResource(CorrodedCreeperEntity animatable) {
        return new ResourceLocation("parainfection", "geo/corroded/corroded_creeper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CorrodedCreeperEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/corroded/corroded_creeper.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CorrodedCreeperEntity animatable) {
        return new ResourceLocation("parainfection", "animations/corroded/corroded_creeper.animation.json");
    }
}
