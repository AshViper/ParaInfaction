package com.ashviper.parainfection.entity.model.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.Nesterra3Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class Nesterra3Model extends GeoModel<Nesterra3Entity> {

    @Override
    public ResourceLocation getModelResource(Nesterra3Entity animatable) {
        return new ResourceLocation("parainfection", "geo/purebred/nesterra_phase3.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Nesterra3Entity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/purebred/nesterra_phase3.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Nesterra3Entity animatable) {
        return new ResourceLocation("parainfection", "animations/purebred/nesterra_phase3.animation.json");
    }
}
