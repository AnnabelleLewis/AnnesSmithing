package com.annabelle.annessmithing.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ComponentCreativeModeTab {
    public static final CreativeModeTab COMPONENTS_TAB = new CreativeModeTab("componentstab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.WOOD_TOOL_ROD.get());
        }
    };
}
