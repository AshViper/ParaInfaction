package com.ashviper.parainfection.phase;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class PhasePointData extends SavedData {
    private long lastPointTime = System.currentTimeMillis();

    private float points = 0;

    private int lastPhase = -1;

    public int getLastPhase() {
        return lastPhase;
    }

    public void setLastPhase(int phase) {
        this.lastPhase = phase;
    }

    public PhasePointData() {}

    public static PhasePointData load(CompoundTag nbt) {
        PhasePointData data = new PhasePointData();
        data.points = nbt.getFloat("Points");
        data.lastPhase = nbt.getInt("LastPhase");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putFloat("Points", points);
        nbt.putInt("LastPhase", lastPhase);
        return nbt;
    }

    public float getPoints() {
        return points;
    }

    public void tick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPointTime >= 4000) { // 20秒経過したか
            addPoints(1.0f); // 1ポイント加算
            lastPointTime = currentTime; // 最後にポイントを加算した時刻を更新
        }
    }

    public void addPoints(float amount) {
        points += amount;
        setDirty();  // 変更があったら保存をマーク
    }

    public void resetPoints() {
        points = 0f;
        setDirty();
    }

    // ポイントに応じたフェーズを判定
    public int getPhase() {
        if (points < 1000f) return 0;
        else if (points < 5000f) return 1;
        else if (points < 10000f) return 2;
        else if (points < 50000f) return 3;
        else if (points < 100000f) return 4;
        else if (points < 500000f) return 5;
        else if (points < 1000000f) return 6;
        else if (points < 5000000f) return 7;
        else if (points < 10000000f) return 8;
        else if (points < 50000000f) return 9;
        else if (points < 100000000f) return 10;
        else return 11;
    }
}

