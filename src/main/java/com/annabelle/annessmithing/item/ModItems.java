package com.annabelle.annessmithing.item;

import com.annabelle.annessmithing.AnnesSmithing;
import com.annabelle.annessmithing.item.custom.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.CallbackI;

import java.util.EnumSet;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AnnesSmithing.MOD_ID);

    public static final RegistryObject<Item> BLANK_PICK = ITEMS.register(
      "custom_pick",
            () -> new CustomToolItem(BlockTags.MINEABLE_WITH_PICKAXE, "annessmithing.tools.pickaxe", new Item.Properties().tab(CreativeModeTab.TAB_TOOLS))
    );
    public static final RegistryObject<Item> CUSTOM_HOE = ITEMS.register(
            "custom_hoe",
            () -> new CustomToolItem(BlockTags.MINEABLE_WITH_HOE, "annessmithing.tools.hoe", new Item.Properties(), true)
    );
    public static final RegistryObject<Item> CUSTOM_SWORD = ITEMS.register(
            "custom_sword",
            () -> new CustomSwordItem(new Item.Properties())
    );
    public static final RegistryObject<Item> CUSTOM_AXE = ITEMS.register(
            "custom_axe",
            () -> new CustomAxeItem(BlockTags.MINEABLE_WITH_AXE, "annessmithing.tools.axe",new Item.Properties())
    );
    public static final RegistryObject<Item> CUSTOM_SHOVEL = ITEMS.register(
            "custom_shovel",
            () -> new CustomShovelItem(BlockTags.MINEABLE_WITH_SHOVEL, "annessmithing.tools.shovel", new Item.Properties())
    );

    public static final RegistryObject<Item> WOOD_TOOL_ROD = ITEMS.register(
            "wood_tool_rod",
            () -> new ToolComponentItem("wood", new Item.Properties().tab(ComponentCreativeModeTab.COMPONENTS_TAB))
    );
    public static final RegistryObject<Item> STRING_BINDER = ITEMS.register(
            "string_binding",
            () -> new ToolComponentItem("string", new Item.Properties().tab(ComponentCreativeModeTab.COMPONENTS_TAB))
    );
    public static final RegistryObject<Item> BLAZING_TOOL_ROD = ITEMS.register(
            "blazing_tool_rod",
            () -> new ToolComponentItem("blaze", new Item.Properties().tab(ComponentCreativeModeTab.COMPONENTS_TAB))
    );
    public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register(
            "speed_upgrade",
            () -> new Item(new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        MaterialGenerator.generateMaterials(
                "flint",
                EnumSet.of(
                        MaterialGenerator.GenFlags.AXE_HEAD,
                        MaterialGenerator.GenFlags.HOE_HEAD,
                        MaterialGenerator.GenFlags.PICKAXE_HEAD,
                        MaterialGenerator.GenFlags.SWORD_HEAD,
                        MaterialGenerator.GenFlags.SHOVEL_HEAD),
                ITEMS);
        MaterialGenerator.generateMaterials(
                "copper",
                EnumSet.of(
                        MaterialGenerator.GenFlags.AXE_HEAD,
                        MaterialGenerator.GenFlags.HOE_HEAD,
                        MaterialGenerator.GenFlags.PICKAXE_HEAD,
                        MaterialGenerator.GenFlags.SWORD_HEAD,
                        MaterialGenerator.GenFlags.SHOVEL_HEAD),
                ITEMS);
        ITEMS.register(eventBus);
    }
}
