package com.annabelle.annessmithing.materials;

import net.minecraft.world.item.Tiers;

import java.util.Map;

public class ModMaterials {
    public static Map<String, Material> MATERIALS = Map.ofEntries(
            Map.entry("flint", new Material()
                    .destroySpeed(2.0f)
                    .tier(Tiers.WOOD)
                    .durrability(50,0.8f)),
            Map.entry("string", new Material()),
            Map.entry("wood", new Material()
                    .destroySpeed(1.5f,0.8f)
                    .tier(Tiers.WOOD)
                    .durrability(59, 0.8f))
    );
}
