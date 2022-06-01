package com.annabelle.annessmithing.datagen;

import com.annabelle.annessmithing.AnnesSmithing;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AnnesSmithing.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerMaterialToolHeads("flint");
        registerMaterialToolHeads("copper");
        registerMaterialToolHeads("iron");
        registerMaterialToolHeads("gold");
        registerMaterialToolHeads("diamond");
        registerMaterialToolHeads("netherite");
    }

    private void registerMaterialToolHeads(String material){
        toolPart("pickaxe_head",material);
        toolPart("axe_head",material);
        toolPart("hoe_head",material);
        toolPart("shovel_head",material);
        toolPart("sword_head",material);
    }

    private ItemModelBuilder toolPart(String part_name, String materialName){
        return withExistingParent(materialName + "_" + part_name,
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(AnnesSmithing.MOD_ID,"item/" + materialName + "/"+ part_name));
    }
}
