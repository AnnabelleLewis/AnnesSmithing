package com.annabelle.annessmithing.materials;

import net.minecraft.world.item.Tier;

public class Material {

    private Tier toolHeadTier;
    private float baseDestroySpeed = 0.0f;
    private float destroySpeedMultiplier = 0.0f;

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

    public float getDestroySpeed(){
        return baseDestroySpeed;
    }

    public Tier getToolHeadTier(){
        return this.toolHeadTier;
    }

}
