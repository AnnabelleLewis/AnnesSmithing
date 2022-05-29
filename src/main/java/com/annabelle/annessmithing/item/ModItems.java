package com.annabelle.annessmithing.item;

import com.annabelle.annessmithing.AnnesSmithing;
import com.annabelle.annessmithing.item.custom.CustomToolItem;
import com.annabelle.annessmithing.item.custom.ToolComponentItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AnnesSmithing.MOD_ID);

    public static final RegistryObject<Item> BLANK_PICK = ITEMS.register(
      "blank_pick",
            () -> new CustomToolItem(BlockTags.MINEABLE_WITH_PICKAXE, "annessmithing.tools.pickaxe", new Item.Properties().tab(CreativeModeTab.TAB_TOOLS))
    );

    public static final RegistryObject<Item> CUSTOM_HOE = ITEMS.register(
            "custom_hoe",
            () -> new CustomToolItem(BlockTags.MINEABLE_WITH_HOE, "annessmithing.tools.hoe", new Item.Properties(), true)
    );

    public static final RegistryObject<Item> WOOD_TOOL_ROD = ITEMS.register(
            "wood_tool_rod",
            () -> new ToolComponentItem("wood", new Item.Properties())
    );
    public static final RegistryObject<Item> STRING_BINDER = ITEMS.register(
            "string_binding",
            () -> new ToolComponentItem("string", new Item.Properties())
    );
    public static final RegistryObject<Item> FLINT_PICKAXE_HEAD = ITEMS.register(
            "flint_pickaxe_head",
            () -> new ToolComponentItem("flint", new Item.Properties())
    );
    public static final RegistryObject<Item> FLINT_HOE_HEAD = ITEMS.register(
            "flint_hoe_head",
            () -> new ToolComponentItem("flint", new Item.Properties())
    );
    public static final RegistryObject<Item> BLAZING_TOOL_ROD = ITEMS.register(
            "blazing_tool_rod",
            () -> new ToolComponentItem("blaze", new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
