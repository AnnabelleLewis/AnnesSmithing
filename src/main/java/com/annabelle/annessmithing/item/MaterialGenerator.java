package com.annabelle.annessmithing.item;

import com.annabelle.annessmithing.item.custom.ToolComponentItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;

import java.util.EnumSet;

public class MaterialGenerator {
    public static enum GenFlags {
        PICKAXE_HEAD,
        AXE_HEAD,
        HOE_HEAD,
        SWORD_HEAD,
        TOOL_BINDING,
        TOOL_ROD
    }

    public static void generateMaterials(String material, EnumSet<GenFlags> flags, DeferredRegister<Item> register){
        if(flags.contains(GenFlags.PICKAXE_HEAD)){
            register.register(material + "_pickaxe_head",
                    () -> new ToolComponentItem(material, new Item.Properties())
                    );
        }
        if(flags.contains(GenFlags.AXE_HEAD)){
            register.register(material + "_axe_head",
                    () -> new ToolComponentItem(material, new Item.Properties())
            );
        }
        if(flags.contains(GenFlags.HOE_HEAD)){
            register.register(material + "_hoe_head",
                    () -> new ToolComponentItem(material, new Item.Properties())
            );
        }
        if(flags.contains(GenFlags.SWORD_HEAD)){
            register.register(material + "_sword_head",
                    () -> new ToolComponentItem(material, new Item.Properties())
            );
        }
        if(flags.contains(GenFlags.TOOL_BINDING)){
            register.register(material + "_tool_binding",
                    () -> new ToolComponentItem(material, new Item.Properties())
            );
        }
        if(flags.contains(GenFlags.TOOL_ROD)){
            register.register(material + "_tool_rod",
                    () -> new ToolComponentItem(material, new Item.Properties())
            );
        }
    }
}
