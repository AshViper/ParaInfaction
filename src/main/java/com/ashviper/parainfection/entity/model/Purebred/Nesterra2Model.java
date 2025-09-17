package com.ashviper.parainfection.entity.model.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.Nesterra2Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class Nesterra2Model extends GeoModel<Nesterra2Entity> {

    @Override
    public ResourceLocation getModelResource(Nesterra2Entity animatable) {
        return new ResourceLocation("parainfection", "geo/purebred/nesterra_phase2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Nesterra2Entity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/purebred/nesterra_phase2.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Nesterra2Entity animatable) {
        return new ResourceLocation("parainfection", "animations/purebred/nesterra_phase2.animation.json");
    }
}
