package com.ashviper.parainfection.entity.custom.Purebred;

import com.ashviper.parainfection.entity.custom.Class.NesterraBaseEntity;
import com.ashviper.parainfection.regi.ModSoundEvents;
import com.ashviper.parainfection.regi.ParaInfectionMobs;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.List;

public class Nesterra3Entity extends NesterraBaseEntity {
    public Nesterra3Entity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }
    private int nextPhaseDelay = 0;

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes()
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.ARMOR, 6.0D);
    }
    @Override
    protected int getMaxCorruptionRadius() {
        return 48;
    }
    @Override
    protected int getMaxCorruptionTime() {
        return 750;
    }
    @Override
    public void tick(){
        super.tick();
        if (!this.level().isClientSide) {
            nextPhaseDelay++;
            if (nextPhaseDelay > 40000) {
                if (this.random.nextFloat() < 0.1f) {
                    transformToPhaseUp();
                }
                nextPhaseDelay = 0;
            }
        }
    }

    @Override
    protected List<EntityType<? extends Monster>> getSummonMobTypes() {
        return List.of(
                ParaInfectionMobs.CORRODEDZOMBIE.get(),
                ParaInfectionMobs.CORRODEDCOW.get(),
                ParaInfectionMobs.CORRODEDCHIKEN.get(),
                ParaInfectionMobs.CORRODEDSHEEP.get(),
                ParaInfectionMobs.CORRODEDPIG.get()
        );
    }

    @Override
    protected int getMobsPerSummon() {
        return 4;
    }

    @Override
    protected int getSummonInterval() {
        return 360; // 4秒間隔
    }

    @Override
    protected int getSummonRange() {
        return 20;
    }

    private void transformToPhaseUp() {
        var newEntity = ParaInfectionMobs.NESTERRA4.get().create(this.level());

        if (newEntity != null) {
            newEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            this.level().addFreshEntity(newEntity);
            this.discard(); // 自身を削除（変化）
            this.level().playSound(
                    null,  // 全プレイヤーに聞こえる
                    this.getX(), this.getY(), this.getZ(),
                    ModSoundEvents.NESTERRA1SPAWN.get(),
                    SoundSource.HOSTILE,
                    3.0F, // 音量
                    1.3F  // ピッチ
            );
        }
    }
}