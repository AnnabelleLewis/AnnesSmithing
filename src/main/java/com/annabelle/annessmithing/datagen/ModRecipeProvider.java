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

        ShapedRecipeBuilder.shaped(ModItems.BASIC_DAMAGE_UPGRADE.get())
                .define('Q', Tags.Items.GEMS_QUARTZ)
                .define('C', Tags.Items.INGOTS_COPPER)
                .pattern(" Q ")
                .pattern("QCQ")
                .pattern(" Q ")
                .unlockedBy("has_quartz", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.QUARTZ).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.IMPROVED_DAMAGE_UPGRADE.get())
                .define('Q', Tags.Items.GEMS_QUARTZ)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('U', ModItems.BASIC_DAMAGE_UPGRADE.get())
                .pattern("QUQ")
                .pattern("QIQ")
                .pattern("QUQ")
                .unlockedBy("has_quartz", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.QUARTZ).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.SUPREME_DAMAGE_UPGRADE.get())
                .define('Q', Tags.Items.STORAGE_BLOCKS_QUARTZ)
                .define('D', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('U', ModItems.IMPROVED_DAMAGE_UPGRADE.get())
                .pattern("QUQ")
                .pattern("QDQ")
                .pattern("QUQ")
                .unlockedBy("has_quartz", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.QUARTZ).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.BASIC_SPEED_UPGRADE.get())
                .define('Q', Tags.Items.DUSTS_REDSTONE)
                .define('C', Tags.Items.INGOTS_COPPER)
                .pattern(" Q ")
                .pattern("QCQ")
                .pattern(" Q ")
                .unlockedBy("has_redstone", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.REDSTONE).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.IMPROVED_SPEED_UPGRADE.get())
                .define('Q', Tags.Items.DUSTS_REDSTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('U', ModItems.BASIC_SPEED_UPGRADE.get())
                .pattern("QUQ")
                .pattern("QIQ")
                .pattern("QUQ")
                .unlockedBy("has_redstone", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.REDSTONE).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.SUPREME_SPEED_UPGRADE.get())
                .define('Q', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('D', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('U', ModItems.IMPROVED_SPEED_UPGRADE.get())
                .pattern("QUQ")
                .pattern("QDQ")
                .pattern("QUQ")
                .unlockedBy("has_redstone", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.REDSTONE).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.BASIC_DURABILITY_UPGRADE.get())
                .define('Q', Tags.Items.OBSIDIAN)
                .define('C', Tags.Items.INGOTS_COPPER)
                .pattern(" Q ")
                .pattern("QCQ")
                .pattern(" Q ")
                .unlockedBy("has_obsidian", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.OBSIDIAN).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.IMPROVED_DURABILITY_UPGRADE.get())
                .define('Q', Tags.Items.OBSIDIAN)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('U', ModItems.BASIC_DURABILITY_UPGRADE.get())
                .pattern("QUQ")
                .pattern("QIQ")
                .pattern("QUQ")
                .unlockedBy("has_obsidian", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.OBSIDIAN).build()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ModItems.SUPREME_DURABILITY_UPGRADE.get())
                .define('Q', Tags.Items.OBSIDIAN)
                .define('D', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('U', ModItems.IMPROVED_DURABILITY_UPGRADE.get())
                .pattern("QUQ")
                .pattern("QDQ")
                .pattern("QUQ")
                .unlockedBy("has_obsidian", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.OBSIDIAN).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.TWINE_BINDER.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("S S")
                .pattern(" S ")
                .unlockedBy("has_sticks", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK).build()))
                .save(pFinishedRecipeConsumer);

    }
}
