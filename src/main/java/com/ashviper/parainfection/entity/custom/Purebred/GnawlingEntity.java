package com.ashviper.parainfection.entity.custom.Purebred;

import com.ashviper.parainfection.entity.ParainfectionEntities;
import com.ashviper.parainfection.entity.custom.Class.ParainfectionBaseEntity;
import com.ashviper.parainfection.regi.ModSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GnawlingEntity extends ParainfectionBaseEntity {

    private int killCount = 0;
    private static final int TRANSFORM_KILL_THRESHOLD = 10;

    public GnawlingEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    protected @NotNull SoundEvent getAmbientSound() {
        return ModSoundEvents.GNAWLINGMEOW.get();
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean success = super.doHurtTarget(target);

        if (!level().isClientSide && success && target instanceof LivingEntity living) {
            // 倒されたかチェック
            if (living.isDeadOrDying()) {
                killCount++;
                if (killCount >= TRANSFORM_KILL_THRESHOLD) {
                    transform();
                }
            }
        }
        return success;
    }

    private void transform() {
        var newEntity = ParainfectionEntities.DEVORA.get().create(level());
        if (newEntity != null) {
            newEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            level().addFreshEntity(newEntity);

            // エフェクト（爆発風）
            for (int i = 0; i < 20; i++) {
                double dx = random.nextGaussian() * 0.1;
                double dy = random.nextGaussian() * 0.1;
                double dz = random.nextGaussian() * 0.1;
                level().addParticle(net.minecraft.core.particles.ParticleTypes.EXPLOSION,
                        this.getX(), this.getY() + 0.5, this.getZ(), dx, dy, dz);
            }

            this.discard();
        }
    }

    // NBT保存
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("KillCount", killCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.killCount = tag.getInt("KillCount");
    }
}
