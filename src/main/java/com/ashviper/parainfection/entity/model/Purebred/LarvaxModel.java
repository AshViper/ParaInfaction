package com.ashviper.parainfection.entity.model.Purebred;


import com.ashviper.parainfection.entity.custom.Purebred.GnawlingEntity;
import com.ashviper.parainfection.entity.custom.Purebred.LarvaxEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings("removal")
public class LarvaxModel extends GeoModel<LarvaxEntity> {

	@Override
	public ResourceLocation getModelResource(LarvaxEntity animatable) {
		return new ResourceLocation("parainfection", "geo/purebred/larvax.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LarvaxEntity animatable) {
		return new ResourceLocation("parainfection", "textures/entity/purebred/larvax.png");
	}

	@Override
	public ResourceLocation getAnimationResource(LarvaxEntity animatable) {
		return new ResourceLocation("parainfection", "animations/purebred/larvax.animation.json");
	}
}