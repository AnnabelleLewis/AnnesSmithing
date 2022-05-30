package com.annabelle.annessmithing.integration;

import com.annabelle.annessmithing.AnnesSmithing;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIAnnesSmithingPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(AnnesSmithing.MOD_ID, "jei_plugin");
    }


}
