package com.annabelle.annessmithing.materials;

import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class ModMaterials {
    public static Map<String, Material> MATERIALS = Map.ofEntries(
            Map.entry("flint", new Material()
                    .destroySpeed(2.0f)
                    .tier(Tiers.WOOD)
                    .durability(50,0.8f)
                    .color(0xff222222)),
            Map.entry("string", new Material()
                    .addEnchantment("minecraft:fire_aspect",1)
                    .color(0xffeeeeee)),
            Map.entry("wood", new Material()
                    .destroySpeed(1.5f,0.8f)
                    .tier(Tiers.WOOD)
                    .durability(59, 0.8f)
                    .color(0xff875734))
    );

}
