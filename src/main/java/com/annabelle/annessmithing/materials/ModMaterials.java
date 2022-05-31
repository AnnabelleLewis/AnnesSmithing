package com.annabelle.annessmithing.materials;

import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class ModMaterials {
    public static Map<String, Material> MATERIALS = Map.ofEntries(
            Map.entry("flint", new Material()
                    .destroySpeed(2.0f)
                    .tier(Tiers.WOOD)
                    .durability(60,0.8f)
                    .color(0xff222222)
                    .prefix("annessmithing.materials.flint")
                    .damage(4,0.8f)),
            Map.entry("copper", new Material()
                    .destroySpeed(4f)
                    .durability(130)
                    .damage(5)
                    .prefix("annessmithing.materials.copper")
                    .tier(Tiers.STONE)
                    .color(0xffe77c56)
            ),
            Map.entry("string", new Material()
                    .color(0xffeeeeee)),
            Map.entry("wood", new Material()
                    .destroySpeed(1f,0.8f)
                    .tier(Tiers.WOOD)
                    .durability(59, 0.8f)
                    .color(0xff875734)
                    .damage(2,0.8f)),
            Map.entry("blaze", new Material()
                    .destroySpeed(1.0f, 1.2f)
                    .tier(Tiers.IRON)
                    .durability(100, 1.1f)
                    .color(0xffE47819))
    );

}
