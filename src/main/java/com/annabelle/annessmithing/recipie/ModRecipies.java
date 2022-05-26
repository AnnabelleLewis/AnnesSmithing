package com.annabelle.annessmithing.recipie;

import com.annabelle.annessmithing.AnnesSmithing;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipies {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AnnesSmithing.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ToolCraftingRecipe>> TOOL_CRAFTING_SERIALIZER =
            SERIALIZERS.register("tool_crafting",
                    ToolCraftingRecipe.Serializer::new);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
