package com.ashviper.parainfection.regi.costom.baseClass;

import net.minecraft.world.item.Item;

public abstract class BaseItem extends Item {
    public BaseItem() {
        super(new Properties().stacksTo(64));
    }
}