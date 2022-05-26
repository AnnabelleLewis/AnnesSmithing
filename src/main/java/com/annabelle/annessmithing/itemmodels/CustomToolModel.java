package com.annabelle.annessmithing.itemmodels;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class CustomToolModel implements ItemColor {
    @Override
    public int getColor(ItemStack pStack, int pTintIndex) {
        switch (pTintIndex){
            case 0:
                return pStack.getTag().getInt("annessmithing.rod_color");
            case 1:
                return pStack.getTag().getInt("annessmithing.head_color");
            case 2:
                return pStack.getTag().getInt("annessmithing.binder_color");
        }
        return 0xffff00ff;
    }
}
