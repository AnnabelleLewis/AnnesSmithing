package com.annabelle.annessmithing.datagen;

import com.annabelle.annessmithing.item.ModItems;
import com.annabelle.annessmithing.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapedRecipeBuilder.shaped(ModItems.PRIMITIVE_REPAIR_KIT.get())
                .define('W', Tags.Items.RODS_WOODEN)
                .define('F', Items.FLINT)
                .pattern("WFW")
                .pattern("F F")
                .pattern("WFW")
                .unlockedBy("has_flint", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.FLINT).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.BASIC_REPAIR_KIT.get())
                .define('K', ModItems.PRIMITIVE_REPAIR_KIT.get())
                .define('C', Tags.Items.INGOTS_COPPER)
                .pattern(" C ")
                .pattern("CKC")
                .pattern(" C ")
                .unlockedBy("has_copper", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COPPER_INGOT).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.IMPROVED_REPAIR_KIT.get())
                .define('K', ModItems.BASIC_REPAIR_KIT.get())
                .define('C', Tags.Items.INGOTS_IRON)
                .pattern(" C ")
                .pattern("CKC")
                .pattern(" C ")
                .unlockedBy("has_iron", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_INGOT).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.ADVANCED_REPAIR_KIT.get())
                .define('K', ModItems.IMPROVED_REPAIR_KIT.get())
                .define('C', Tags.Items.GEMS_DIAMOND)
                .pattern(" C ")
                .pattern("CKC")
                .pattern(" C ")
                .unlockedBy("has_diamond", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.DIAMOND).build()))
                .save(pFinishedRecipeConsumer);

    }
}
