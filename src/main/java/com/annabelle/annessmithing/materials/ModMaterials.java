package com.annabelle.annessmithing.materials;

import net.minecraft.world.item.Tiers;

import java.util.Map;

public class ModMaterials {
    public static Map<String, Material> MATERIALS = Map.ofEntries(
            Map.entry("flint", new Material()
                    .destroySpeed(2.0f)
                    .tier(Tiers.WOOD))
    );
}
