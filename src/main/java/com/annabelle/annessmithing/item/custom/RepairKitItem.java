package com.annabelle.annessmithing.item.custom;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
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

    public static void removeDurability(ItemStack stack, int durability){
        int currentDurability = stack.getTag().getInt("annessmithing.durability_left");
        currentDurability -= durability;
        if(currentDurability <= 0){
            stack.shrink(1);
        }
        stack.getTag().putInt("annessmithing.durability_left", currentDurability);
    }

    public int getRepairLevel(){return repairLevel;}
    public static int getDurability(ItemStack stack){
        if(!stack.hasTag()){
            stack.setTag(new CompoundTag());
            stack.getTag().putInt("annessmithing.durability_left", 100);
        }
        return stack.getTag().getInt("annessmithing.durability_left");}

    @Override
    public int getBarWidth(ItemStack pStack) {
        float width = 32.0f;
        if(pStack.hasTag()){
            width = (float)pStack.getTag().getInt("annessmithing.durability_left") / (float)baseDurability;

        }
        return (int)(width*32f);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }
}
