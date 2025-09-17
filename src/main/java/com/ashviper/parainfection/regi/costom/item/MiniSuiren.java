package com.ashviper.parainfection.regi.costom.item;

import net.minecraft.world.item.Item;

public class MiniSuiren extends Item {
    public static final String NBT_USES_LEFT_KEY = "UsesLeft";
    public static final int MAX_USES = 2;

    public MiniSuiren() {
        super(new Properties().stacksTo(1));
    }
}