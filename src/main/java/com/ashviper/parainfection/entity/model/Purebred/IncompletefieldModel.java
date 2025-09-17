package com.ashviper.parainfection.entity.model.Purebred;


import com.ashviper.parainfection.entity.custom.Purebred.IncompletefieldEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class IncompletefieldModel extends GeoModel<IncompletefieldEntity> {
    @Override
    public ResourceLocation getModelResource(IncompletefieldEntity animatable) {
        return new ResourceLocation("parainfection", "geo/purebred/incompletefield.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IncompletefieldEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/purebred/incompletefield.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IncompletefieldEntity animatable) {
        return new ResourceLocation("parainfection", "animations/purebred/incompletefield.animation.json");
    }
}