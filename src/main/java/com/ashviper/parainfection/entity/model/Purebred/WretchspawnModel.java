package com.ashviper.parainfection.entity.model.Purebred;

import com.ashviper.parainfection.entity.custom.Purebred.WretchspawnEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WretchspawnModel extends GeoModel<WretchspawnEntity> {
    @Override
    public ResourceLocation getModelResource(WretchspawnEntity animatable) {
        return new ResourceLocation("parainfection", "geo/purebred/wretchspawn.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WretchspawnEntity animatable) {
        return new ResourceLocation("parainfection", "textures/entity/purebred/wretchspawn.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WretchspawnEntity animatable) {
        return new ResourceLocation("parainfection", "animations/purebred/wretchspawn.animation.json");
    }
}
