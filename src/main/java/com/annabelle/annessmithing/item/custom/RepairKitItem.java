package com.annabelle.annessmithing.item.custom;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
        stack.setTag(new CompoundTag());
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
        float width = 1f;
        if(pStack.hasTag()){
            width = (float)pStack.getTag().getInt("annessmithing.durability_left") / (float)baseDurability;

        }
        return (int)((width)*13f);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        if(!pStack.hasTag()){return false;}
        return true;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        if(!pStack.hasTag()){return Mth.hsvToRgb(1f / 3.0F, 1.0F, 1.0F);}
        float stackMaxDamage = baseDurability;
        float f = Math.max(0.0F, ((float)pStack.getTag().getInt("annessmithing.durability_left")) / stackMaxDamage);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(new TextComponent(
                "Repair level: " + repairLevel
        ));
        if(!pStack.hasTag()){
            pTooltipComponents.add(new TextComponent(
                    "Durability remaining: " + baseDurability
            ));
            return;}
        pTooltipComponents.add(new TextComponent(
                "Durability remaining: " + pStack.getTag().getInt("annessmithing.durability_left")
        ));
    }
}
