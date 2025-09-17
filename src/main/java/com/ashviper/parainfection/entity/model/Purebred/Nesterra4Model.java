package com.ashviper.parainfection.entity.model.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.Nesterra4Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class Nesterra4Model extends GeoModel<Nesterra4Entity> {

    @Override
    public ResourceLocation getModelResource(Nesterra4Entity animatable) {
        return new ResourceLocation("parainfection", "geo/purebred/nesterra_phase4.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Nesterra4Entity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/purebred/nesterra_phase4.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Nesterra4Entity animatable) {
        return new ResourceLocation("parainfection", "animations/purebred/nesterra_phase4.animation.json");
    }
}
