package com.annabelle.annessmithing.item.custom;

import net.minecraft.world.item.Item;

public class ToolComponentItem extends Item {

    private String material;

    public ToolComponentItem(String material, Properties pProperties) {
        super(pProperties);
        this.material = material;
    }

    public String getMaterial(){return this.material;}
}
