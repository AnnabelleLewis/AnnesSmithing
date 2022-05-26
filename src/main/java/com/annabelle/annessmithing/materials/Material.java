package com.annabelle.annessmithing.materials;

import net.minecraft.world.item.Tier;

public class Material {

    private Tier toolHeadTier;
    private float baseDestroySpeed = 0.0f;
    private float destroySpeedMultiplier = 0.0f;

    private int baseDurrability = 1;
    private float durrabilityModifier = 1.0f;

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

    public Material durrability(int baseDurrability){
        this.baseDurrability = baseDurrability;
        return this;
    }
    public Material durrability(int baseDurrability, float durrabilityModifier){
        this.baseDurrability = baseDurrability;
        this.durrabilityModifier = durrabilityModifier;
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

    public float getBaseDurrability(){
        return (float)baseDurrability;
    }

    public float getDurrabilityModifier(){
        return durrabilityModifier;
    }
}
