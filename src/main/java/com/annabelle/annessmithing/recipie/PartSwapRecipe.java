package com.annabelle.annessmithing.recipie;

import com.annabelle.annessmithing.AnnesSmithing;
import com.annabelle.annessmithing.item.custom.CustomSwordItem;
import com.annabelle.annessmithing.item.custom.CustomToolItem;
import com.annabelle.annessmithing.item.custom.ToolComponentItem;
import com.annabelle.annessmithing.util.ModTags;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class PartSwapRecipe extends ShapelessRecipe {

    final ItemStack result;
    
    public PartSwapRecipe(ResourceLocation pId, String pGroup, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pGroup, pResult, pIngredients);
        result = pResult;
    }

    public ItemStack assemble(CraftingContainer pInv) {
        ItemStack out = this.result.copy();
        NonNullList<ItemStack> input_items = NonNullList.create();
        
        // Add all actual items to the input items
        for (int i = 0; i < pInv.getContainerSize(); i++) {
            if (!pInv.getItem(i).isEmpty()) {
                input_items.add(pInv.getItem(i));
            }
        }
        
        // Identify input tool and input part
        ItemStack inTool;
        ItemStack inPart;
        
        if(input_items.get(0).is(ModTags.Items.TOOLS)){
            inTool = input_items.get(0);
            inPart = input_items.get(1);
        }else{
            inTool = input_items.get(1);
            inPart = input_items.get(0);
        }
        
        // Get the tool materials of the input tool
        String headMaterial = inTool.getTag().getString("annessmithing.head_material");
        String binderMaterial = inTool.getTag().getString("annessmithing.binder_material");
        String rodMaterial = inTool.getTag().getString("annessmithing.rod_material");
        
        // Overwrite one material based on the input part
        Item partItem = inPart.getItem();
        if(partItem instanceof ToolComponentItem){
            // If item is a rod, overwrite rod mat
            if(inPart.is(ModTags.Items.TOOL_RODS)){
                rodMaterial = ((ToolComponentItem) partItem).getMaterial();
            } else if (inPart.is(ModTags.Items.TOOL_BINDERS)) { // If not a rod, ckeck if is a binder
                binderMaterial = ((ToolComponentItem) partItem).getMaterial();
            }else{ // Otherwise, we know it is a head
                headMaterial = ((ToolComponentItem) partItem).getMaterial();
            }
        }
        
        // Copy over nbt data from tool
        out.setTag(inTool.getTag());
        
        // Overwrite material data
        Item output_item = out.getItem();
        if(output_item instanceof CustomToolItem){
            ((CustomToolItem)output_item).setupToolMaterials(out,headMaterial,binderMaterial,rodMaterial);
        }
        if(output_item instanceof CustomSwordItem){
            ((CustomSwordItem)output_item).setupToolMaterials(out,headMaterial,binderMaterial,rodMaterial);
        }
        
        return out;
    }

    public RecipeSerializer<PartSwapRecipe> getSerializer() {
        return new PartSwapRecipe.Serializer();
    }


    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<PartSwapRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation(AnnesSmithing.MOD_ID, "tool_crafting");
        public PartSwapRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            String s = GsonHelper.getAsString(pJson, "group", "");
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(pJson, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }  else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
                return new PartSwapRecipe(pRecipeId, s, itemstack, nonnulllist);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < pIngredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
                if (net.minecraftforge.common.ForgeConfig.SERVER.skipEmptyShapelessCheck.get() || !ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        public PartSwapRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack itemstack = pBuffer.readItem();
            return new PartSwapRecipe(pRecipeId, s, itemstack, nonnulllist);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, PartSwapRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pBuffer.writeVarInt(pRecipe.getIngredients().size());

            for(Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.result);
        }
    }
}
