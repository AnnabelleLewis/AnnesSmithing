package com.annabelle.annessmithing.recipie;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;

public class ToolUpgradeRecipe extends UpgradeRecipe {

    final String modifier;
    final int modLevel;
    final ItemStack result;

    final Ingredient base;
    final Ingredient addition;

    public ToolUpgradeRecipe(ResourceLocation pId, Ingredient pBase, Ingredient pAddition, ItemStack pResult, String modifier, int level) {
        super(pId, pBase, pAddition, pResult);
        this.modifier = modifier;
        this.modLevel = level;
        this.result = pResult;
        this.base = pBase;
        this.addition = pAddition;
    }

    @Override
    public boolean matches(Container pInv, Level pLevel) {
        return super.matches(pInv, pLevel) && pInv.getItem(0).getTag().getInt("annessmithing.open_mod_slots") > 0;
    }

    @Override
    public ItemStack assemble(Container pInv) {
        ItemStack itemstack = pInv.getItem(0).copy();
        CompoundTag compoundtag = pInv.getItem(0).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }
        if(itemstack.getTag().contains(modifier)){
            itemstack.getTag().putInt(modifier,
                    itemstack.getTag().getInt(modifier) + modLevel);
        }else{
            itemstack.getTag().putInt(modifier, modLevel);
        }

        itemstack.getTag().putInt("annessmithing.open_mod_slots",
                itemstack.getTag().getInt("annessmithing.open_mod_slots") - 1);

        return itemstack;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ToolUpgradeRecipe> {
        public ToolUpgradeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "base"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "addition"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
            String modifier = GsonHelper.getAsString(pJson, "modifier");
            int modLevel = GsonHelper.getAsInt(pJson, "modifier_level");
            return new ToolUpgradeRecipe(pRecipeId, ingredient, ingredient1, itemstack,modifier, modLevel);
        }

        public ToolUpgradeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(pBuffer);
            ItemStack itemstack = pBuffer.readItem();
            String modifier = pBuffer.readUtf();
            int level = pBuffer.readInt();
            return new ToolUpgradeRecipe(pRecipeId, ingredient, ingredient1, itemstack, modifier, level);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, ToolUpgradeRecipe pRecipe) {
            pRecipe.base.toNetwork(pBuffer);
            pRecipe.addition.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeUtf(pRecipe.modifier);
            pBuffer.writeInt(pRecipe.modLevel);
        }
    }
}
