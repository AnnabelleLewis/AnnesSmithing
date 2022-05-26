package com.annabelle.annessmithing;

import com.annabelle.annessmithing.item.ModItems;
import com.annabelle.annessmithing.itemmodels.CustomToolModel;
import net.minecraftforge.client.event.ColorHandlerEvent;

public class ClientSetup {
    public static void registerItemColors(final ColorHandlerEvent.Item event) {
        event.getItemColors().register(new CustomToolModel(), ModItems.BLANK_PICK.get());
    }
}
