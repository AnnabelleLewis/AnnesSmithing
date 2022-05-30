package com.annabelle.annessmithing;

import com.annabelle.annessmithing.item.ModItems;
import com.annabelle.annessmithing.item.custom.CustomSwordItem;
import com.annabelle.annessmithing.itemmodels.CustomToolModel;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class ClientSetup {
    public static void registerItemColors(final ColorHandlerEvent.Item event) {
        CustomToolModel toolModel = new CustomToolModel();
        event.getItemColors().register(toolModel, ModItems.BLANK_PICK.get());
        event.getItemColors().register(toolModel, ModItems.CUSTOM_HOE.get());
        event.getItemColors().register(toolModel, ModItems.CUSTOM_SWORD.get());
        event.getItemColors().register(toolModel, ModItems.CUSTOM_AXE.get());
        event.getItemColors().register(toolModel, ModItems.CUSTOM_SHOVEL.get());
    }
}
