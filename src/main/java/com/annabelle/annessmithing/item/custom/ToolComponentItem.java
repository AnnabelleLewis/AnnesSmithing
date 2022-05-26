package com.annabelle.annessmithing.item.custom;

import net.minecraft.world.item.Item;

public class ToolComponentItem extends Item {

    private static String material;

    public ToolComponentItem(String material, Properties pProperties) {
        super(pProperties);
        this.material = material;
    }

    public static String getMaterial(){
        return material;
    }
}
