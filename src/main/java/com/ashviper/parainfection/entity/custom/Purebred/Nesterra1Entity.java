package com.ashviper.parainfection.entity.custom.Purebred;

import com.ashviper.parainfection.entity.ParainfectionEntities;
import com.ashviper.parainfection.entity.custom.Class.NesterraBaseEntity;
import com.ashviper.parainfection.regi.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.List;

public class Nesterra1Entity extends NesterraBaseEntity {
    public Nesterra1Entity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }
    private int nextPhaseDelay = 0;

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ARMOR, 2.0D);
    }
    @Override
    protected int getMaxCorruptionRadius() {
        return 16;
    }
    @Override
    protected int getMaxCorruptionTime() {
        return 1000;
    }

    @Override
    public void tick(){
        super.tick();
        if (!this.level().isClientSide) {
            nextPhaseDelay++;
            if (nextPhaseDelay > 10000) {
                if (this.random.nextFloat() < 0.3f) {
                    transformToPhaseUp();
                }
                nextPhaseDelay = 0;
            }
        }
    }

    @Override
    protected List<EntityType<? extends Monster>> getSummonMobTypes() {
        return List.of(
                ParainfectionEntities.GNAWLING.get()
        );
    }

    @Override
    protected int getMobsPerSummon() {
        return 3;
    }

    @Override
    protected int getSummonInterval() {
        return 160; // 4秒間隔
    }

    @Override
    protected int getSummonRange() {
        return 12;
    }

    private void transformToPhaseUp() {
        var newEntity = ParainfectionEntities.NESTERRA2.get().create(this.level());

        if (newEntity != null) {
            newEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            this.level().addFreshEntity(newEntity);
            this.discard(); // 自身を削除（変化）
            this.level().playSound(
                    null,  // 全プレイヤーに聞こえる
                    this.getX(), this.getY(), this.getZ(),
                    ModSoundEvents.NESTERRA1SPAWN.get(),
                    SoundSource.HOSTILE,
                    2.0F, // 音量
                    1.0F  // ピッチ
            );
        }
    }
}