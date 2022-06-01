package com.annabelle.annessmithing.item.custom;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RepairKitItem extends Item {

    private final int baseDurability;
    private final int repairLevel;

    public RepairKitItem(int durability, int repairLevel, Properties props){
        super(props);
        this.baseDurability = durability;
        this.repairLevel = repairLevel;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.getTag().putInt("annessmithing.durability_left", baseDurability);
        return stack;
    }

    public void removeDurability(ItemStack stack, int durability){
        int currentDurability = stack.getTag().getInt("annessmithing.durability_left");
        currentDurability -= durability;
        if(currentDurability <= 0){
            stack.shrink(1);
        }
        stack.getTag().putInt("annessmithing.durability_left", currentDurability);
    }

}
