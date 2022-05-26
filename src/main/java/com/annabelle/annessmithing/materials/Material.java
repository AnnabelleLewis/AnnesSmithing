package com.annabelle.annessmithing.materials;

import net.minecraft.world.item.Tier;

public class Material {

    private Tier toolHeadTier;
    private float baseDestroySpeed = 0.0f;
    private float destroySpeedMultiplier = 0.0f;
    private int baseDurability = 1;
    private float durabilityModifier = 1.0f;
    private int color = 0xFFFFFFFF;

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
}
