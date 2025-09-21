package com.ashviper.parainfection.entity.custom.Corroded;

import com.ashviper.parainfection.entity.custom.Class.ParainfectionBaseEntity;
import com.ashviper.parainfection.regi.ParaInfectionMobs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class CorrodedCreeperEntity extends ParainfectionBaseEntity {
    public CorrodedCreeperEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }
    private boolean hasExploded = false;

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }
    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && !hasExploded && this.getTarget() != null) {
            double distance = this.distanceTo(this.getTarget());
            if (distance < 3.0D) {
                hasExploded = true;
                this.explodeAndSummon();
            }
        }
    }

    private void explodeAndSummon() {
        // ブロックを破壊する爆発（TNTと同じ）
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.5F, Level.ExplosionInteraction.BLOCK);

        // Mobを3体召喚（例としてゾンビ）
        for (int i = 0; i < 3; i++) {
            Monster summoned = ParaInfectionMobs.LARVAX.get().create(this.level());
            if (summoned != null) {
                summoned.moveTo(
                        this.getX() + this.random.nextGaussian(),
                        this.getY(),
                        this.getZ() + this.random.nextGaussian(),
                        this.getYRot(),
                        0.0F
                );
                this.level().addFreshEntity(summoned);
            }
        }

        // 自壊
        this.discard();
    }
}
