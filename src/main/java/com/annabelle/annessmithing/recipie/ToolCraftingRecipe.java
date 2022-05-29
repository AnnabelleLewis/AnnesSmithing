package com.annabelle.annessmithing.recipie;

import com.annabelle.annessmithing.AnnesSmithing;
import com.annabelle.annessmithing.item.custom.CustomSwordItem;
import com.annabelle.annessmithing.item.custom.CustomToolItem;
import com.annabelle.annessmithing.item.custom.ToolComponentItem;
import com.annabelle.annessmithing.materials.Material;
import com.annabelle.annessmithing.util.ModTags;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import cpw.mods.niofs.pathfs.PathFileSystemProvider;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class ToolCraftingRecipe extends ShapelessRecipe{

    final ItemStack result;

    public ToolCraftingRecipe(ResourceLocation pId, String pGroup, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pGroup, pResult, pIngredients);
        result = pResult;
    }

    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        boolean matches = super.matches(pInv, pLevel);
        return matches;
    }

    @Override
    public ItemStack assemble(CraftingContainer pInv) {
        ItemStack out = this.result.copy();
        NonNullList<ItemStack> input_items = NonNullList.create();
        // Add all actual items to the input items
        for(int i = 0; i < pInv.getContainerSize(); i++){
            if(!pInv.getItem(i).isEmpty()){
                input_items.add(pInv.getItem(i));
            }
        }

        // Loop through stack, and find the items in the rod and binder tags, assign the odd one out to head
        ItemStack rod = ItemStack.EMPTY;
        ItemStack binder = ItemStack.EMPTY;
        ItemStack head = ItemStack.EMPTY;
        for (int i = 0; i < input_items.size(); i++) {
            if(input_items.get(i).is(ModTags.Items.TOOL_BINDERS)){
                binder = input_items.get(i);
                System.out.println("Binder item is..." + input_items.get(i).toString());
            } else if (input_items.get(i).is(ModTags.Items.TOOL_RODS)) {
                rod = input_items.get(i);
                System.out.println("Rod item is..." + input_items.get(i).toString());
            } else {
                head = input_items.get(i);
                System.out.println("Head item is..." + input_items.get(i).toString());
            }
        }

        String rod_material = "";
        String binder_material = "";
        String head_material = "";

        Item rod_item = rod.getItem();
        if(rod_item instanceof ToolComponentItem){
            rod_material = ((ToolComponentItem) rod_item).getMaterial();
        }

        Item binder_item = binder.getItem();
        if(rod_item instanceof ToolComponentItem){
            binder_material = ((ToolComponentItem) binder_item).getMaterial();
        }
        Item head_item = head.getItem();
        if(head_item instanceof ToolComponentItem){
            head_material = ((ToolComponentItem) head_item).getMaterial();
        }

        Item output_item = out.getItem();
        if(output_item instanceof CustomToolItem){
            ((CustomToolItem)output_item).setupToolMaterials(out,head_material,binder_material,rod_material);
        } else if (output_item instanceof CustomSwordItem) {
            ((CustomSwordItem) output_item).setupToolMaterials(out,head_material,binder_material,rod_material);
        }

        return out;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    public RecipeSerializer<ToolCraftingRecipe> getSerializer() {
        return new Serializer();
    }


    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ToolCraftingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation(AnnesSmithing.MOD_ID, "tool_crafting");
        public ToolCraftingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            String s = GsonHelper.getAsString(pJson, "group", "");
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(pJson, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }  else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
                return new ToolCraftingRecipe(pRecipeId, s, itemstack, nonnulllist);
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

        public ToolCraftingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack itemstack = pBuffer.readItem();
            return new ToolCraftingRecipe(pRecipeId, s, itemstack, nonnulllist);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, ToolCraftingRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pBuffer.writeVarInt(pRecipe.getIngredients().size());

            for(Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.result);
        }
    }
}
