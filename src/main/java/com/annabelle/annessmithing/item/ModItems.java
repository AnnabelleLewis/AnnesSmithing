package com.annabelle.annessmithing.item;

import com.annabelle.annessmithing.AnnesSmithing;
import com.annabelle.annessmithing.item.custom.CustomPickaxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AnnesSmithing.MOD_ID);

    public static final RegistryObject<Item> BLANK_PICK = ITEMS.register(
      "blank_pick",
            () -> new CustomPickaxeItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS))
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
