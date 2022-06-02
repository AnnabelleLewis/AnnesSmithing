package com.annabelle.annessmithing.item.custom;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
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
    public int getDurability(ItemStack stack){
        if(!stack.hasTag()){
            stack.setTag(new CompoundTag());
            stack.getTag().putInt("annessmithing.durability_left", baseDurability);
        }
        return stack.getTag().getInt("annessmithing.durability_left");}

    @Override
    public int getBarWidth(ItemStack pStack) {
        float width = 0f;
        if(pStack.hasTag()){
            width = (float)pStack.getTag().getInt("annessmithing.durability_left") / (float)baseDurability;

        }
        return (int)(width*13f);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        if(!pStack.hasTag()){return false;}
        return true;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        if(!pStack.hasTag()){return 0;}
        float stackMaxDamage = baseDurability;
        float f = Math.max(0.0F, ((float)pStack.getTag().getInt("annessmithing.durability_left")) / stackMaxDamage);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }
}
