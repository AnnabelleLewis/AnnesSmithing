package com.annabelle.annessmithing.materials;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Material {

    private Tier toolHeadTier;
    private float baseDestroySpeed = 0.0f;
    private float destroySpeedMultiplier = 0.0f;
    private int baseDurability = 1;
    private float durabilityModifier = 1.0f;
    private int color = 0xFFFFFFFF;
    private String namePrefix = "";


    private HashMap<String, Integer> enchantments = new HashMap<String, Integer>();

    private String enchantment = "";
    private int enchantmentLevel = 0;

    public Material tier(Tier toolHeadTier){
        this.toolHeadTier = toolHeadTier;
        return this;
    }

    public Material destroySpeed(float destroySpeed){
        this.baseDestroySpeed = destroySpeed;
        return this;
    }

    public Material destroySpeed(float destroySpeed, float destroySpeedMultiplier){
        this.baseDestroySpeed = destroySpeed;
        this.destroySpeedMultiplier = destroySpeedMultiplier;
        return this;
    }

    public Material durability(int baseDurability) {
        this.baseDurability = baseDurability;
        return this;
    }

    public Material durability(int baseDurability, float DurabilityModifier){
        this.baseDurability = baseDurability;
        this.durabilityModifier = DurabilityModifier;
        return this;
    }

    public Material color(int color){
        this.color = color;
        return this;
    }

    public Material addEnchantment(String enchantment, int level){
        this.enchantments.put(enchantment, level);
        return this;
    }

    public Material prefix(String prefix){
        this.namePrefix = prefix;
        return this;
    }

    public float getDestroySpeed(){
        return baseDestroySpeed;
    }

    public float getDestroySpeedMultiplier(){
        return destroySpeedMultiplier;
    }

    public Tier getToolHeadTier(){
        return this.toolHeadTier;
    }

    public float getBaseDurability(){
        return (float) baseDurability;
    }

    public float getDurabilityModifier(){
        return durabilityModifier;
    }

    public int getColor(){
        return  color;
    }

    public Map<Enchantment,Integer> getEnchantments(){

        HashMap<Enchantment, Integer> outputEnchantments = new HashMap<Enchantment, Integer>();
        /*for(int i = 0; i < enchTierList.size(); i++){
            Enchantment ench =  ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(enchIDList.get(i)));
            outputEnchantments.put(ench, this.enchantments.get(enchTierList.get(i)));
        }*/
        for(String enchID: enchantments.keySet()){
            Enchantment ench =  ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(enchID));
            outputEnchantments.put(ench, enchantments.get(enchID));
        }
        return outputEnchantments;
    }

    public String getNamePrefix(){
        return namePrefix;
    }
}
