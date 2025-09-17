package com.ashviper.parainfection.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class InfectionEffect extends MobEffect {
    public InfectionEffect() {
        super(MobEffectCategory.HARMFUL, 0x5B2C6F); // カテゴリと色（紫系）
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 毎tick実行される処理（例：ダメージ）
        entity.hurt(entity.damageSources().magic(), 1.0F + amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0; // 毎秒（20tick）発動
    }
}
