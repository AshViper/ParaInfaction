package com.ashviper.parainfection.entity.custom.Purebred;

import com.ashviper.parainfection.entity.ParainfectionEntities;
import com.ashviper.parainfection.entity.custom.Class.NesterraBaseEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.List;

public class Nesterra4Entity extends NesterraBaseEntity {
    public Nesterra4Entity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ARMOR, 8.0D);
    }
    @Override
    protected int getMaxCorruptionRadius() {
        return 64;
    }
    @Override
    protected int getMaxCorruptionTime() {
        return 700;
    }
    @Override
    protected List<EntityType<? extends Monster>> getSummonMobTypes() {
        return List.of(
                ParainfectionEntities.CORRODEDZOMBIE.get(),
                ParainfectionEntities.CORRODEDCOW.get(),
                ParainfectionEntities.CORRODEDCHIKEN.get(),
                ParainfectionEntities.CORRODEDSHEEP.get(),
                ParainfectionEntities.CORRODEDPIG.get()
        );
    }

    @Override
    protected int getMobsPerSummon() {
        return 3;
    }

    @Override
    protected int getSummonInterval() {
        return 400; // 4秒間隔
    }

    @Override
    protected int getSummonRange() {
        return 24;
    }
}