package com.ashviper.parainfection.entity.model.Purebred;


import com.ashviper.parainfection.entity.custom.Purebred.GnawlingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class GnawlingModel extends GeoModel<GnawlingEntity> {

	@Override
	public ResourceLocation getModelResource(GnawlingEntity animatable) {
		return new ResourceLocation("parainfection", "geo/purebred/gnawling.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GnawlingEntity animatable) {
		return new ResourceLocation("parainfection", "textures/entity/purebred/gnawling.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GnawlingEntity animatable) {
		return new ResourceLocation("parainfection", "animations/purebred/gnawling.animation.json");
	}
}