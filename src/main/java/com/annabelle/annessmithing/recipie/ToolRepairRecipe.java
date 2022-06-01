package com.annabelle.annessmithing.recipie;

import com.annabelle.annessmithing.item.custom.RepairKitItem;
import com.annabelle.annessmithing.materials.Material;
import com.annabelle.annessmithing.materials.ModMaterials;
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

public class ToolRepairRecipe extends ShapelessRecipe {

    private final ResourceLocation id;
    final String group;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;

    public ToolRepairRecipe(ResourceLocation pId, String pGroup, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pGroup, pResult, pIngredients);
        this.id = pId;
        this.group = pGroup;
        this.result = pResult;
        this.ingredients = pIngredients;
    }

    public ItemStack assemble(CraftingContainer pInv) {
        ItemStack repairKit = ItemStack.EMPTY;
        ItemStack tool = ItemStack.EMPTY;
        for (int i = 0; i < pInv.getContainerSize(); i++) {
            if (!pInv.getItem(i).isEmpty()) {
                if(pInv.getItem(i).is(ModTags.Items.TOOLS)){
                    tool = pInv.getItem(i);
                }
                if(pInv.getItem(i).is(ModTags.Items.REPAIR_KITS)){
                    repairKit = pInv.getItem(i);
                }
            }
        }

        // Test repair kit level against tool head
        String headMatID = tool.getTag().getString("annessmithing.head_material");
        Material headMaterial = ModMaterials.MATERIALS.get(headMatID);
        int materialTier = headMaterial.getRepairTier();

        int repairKitTier = 0;
        Item repairKitItem = repairKit.getItem();
        if(repairKitItem instanceof RepairKitItem){
            repairKitTier = ((RepairKitItem) repairKitItem).getRepairLevel();
        }

        if(repairKitTier > materialTier){return ItemStack.EMPTY;}

        // Find how much durability the tool wants
        int toolDamage = tool.getDamageValue();

        // Find how much durability the repair kit can give
        int repairKitPower = RepairKitItem.getDurability(repairKit);

        // Take the lesser, add to tool, remove from kit
        int durabilityChange = Math.min(toolDamage, repairKitPower);

        RepairKitItem.removeDurability(repairKit,durabilityChange);
        tool.setDamageValue(tool.getDamageValue() - durabilityChange);

        return tool.copy();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer pInv) {
        NonNullList<ItemStack> results = NonNullList.create();
        for (int i = 0; i < pInv.getContainerSize(); i++) {
            if (!pInv.getItem(i).isEmpty()) {
                if(pInv.getItem(i).is(ModTags.Items.REPAIR_KITS)){
                    results.add(pInv.getItem(i));
                }
            }
        }
        return results;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ToolRepairRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("minecraft", "crafting_tool_repair");
        public ToolRepairRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            String s = GsonHelper.getAsString(pJson, "group", "");
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(pJson, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else  {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
                return new ToolRepairRecipe(pRecipeId, s, itemstack, nonnulllist);
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

        public ToolRepairRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack itemstack = pBuffer.readItem();
            return new ToolRepairRecipe(pRecipeId, s, itemstack, nonnulllist);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, ToolRepairRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeVarInt(pRecipe.ingredients.size());

            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.result);
        }
    }
}
