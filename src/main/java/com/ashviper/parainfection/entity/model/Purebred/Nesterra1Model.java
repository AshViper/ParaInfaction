package com.ashviper.parainfection.entity.model.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.Nesterra1Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class Nesterra1Model extends GeoModel<Nesterra1Entity> {

    @Override
    public ResourceLocation getModelResource(Nesterra1Entity animatable) {
        return new ResourceLocation("parainfection", "geo/purebred/nesterra_phase1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Nesterra1Entity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/purebred/nesterra_phase1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Nesterra1Entity animatable) {
        return new ResourceLocation("parainfection", "animations/purebred/nesterra_phase1.animation.json");
    }
}
