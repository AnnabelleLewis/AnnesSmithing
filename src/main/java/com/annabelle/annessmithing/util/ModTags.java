package com.annabelle.annessmithing.util;

import com.annabelle.annessmithing.AnnesSmithing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> PICKAXE_HEADS = tag("tool_components/pickaxe_head");

        public static final TagKey<Item> HOE_HEADS = tag("tool_components/hoe_head");
        public static final TagKey<Item> TOOL_RODS = tag("tool_components/tool_rod");
        public static final TagKey<Item> TOOL_BINDERS = tag("tool_components/tool_binder");

        public static final  TagKey<Item> TOOLS = tag("tools");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(AnnesSmithing.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
