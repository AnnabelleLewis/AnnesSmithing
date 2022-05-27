package com.annabelle.annessmithing.item.custom;

import com.annabelle.annessmithing.materials.Material;
import com.annabelle.annessmithing.materials.ModMaterials;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;


public class CustomToolItem extends DiggerItem {

    private final TagKey<Block> blocks;

    public CustomToolItem(TagKey breakableBlocks, Properties p_204112_) {
        super(0.0f, 0.0f, Tiers.WOOD, breakableBlocks, p_204112_);
        this.blocks = breakableBlocks;
    }

    public void setupToolMaterials(ItemStack itemStack, String headMaterial, String binderMaterial, String rodMaterial){
        // Apply material tags
        itemStack.getTag().putString("annessmithing.head_material",headMaterial);
        itemStack.getTag().putString("annessmithing.binder_material",binderMaterial);
        itemStack.getTag().putString("annessmithing.rod_material",rodMaterial);

        // Get material objects
        Material headMat = ModMaterials.MATERIALS.get(itemStack.getTag().getString("annessmithing.head_material"));
        Material binderMat = ModMaterials.MATERIALS.get(itemStack.getTag().getString("annessmithing.binder_material"));
        Material rodMat = ModMaterials.MATERIALS.get(itemStack.getTag().getString("annessmithing.rod_material"));

        // Initialize base stats from head (Damage, Mining speed, Mining level, Durability)...
        // and apply modifiers from handle
        itemStack.getTag().putFloat("annessmithing.break_speed", headMat.getDestroySpeed() * rodMat.getDestroySpeedMultiplier());
        itemStack.getTag().putInt("annessmithing.Durability", (int)(headMat.getBaseDurability() * rodMat.getDurabilityModifier()));

        // Set color tags
        itemStack.getTag().putLong("annessmithing.head_color", headMat.getColor());
        itemStack.getTag().putLong("annessmithing.binder_color", binderMat.getColor());
        itemStack.getTag().putLong("annessmithing.rod_color", rodMat.getColor());

        // Apply enchantments from all three

        Map<Enchantment, Integer> itemEnchantments = Map.of();
        for(Enchantment ench:headMat.getEnchantments().keySet()){
            if(itemEnchantments.containsKey(ench)){
                Integer level = itemEnchantments.get(ench) + headMat.getEnchantments().get(ench);
                itemEnchantments.put(ench, level);
            }else{
                itemEnchantments.put(ench,headMat.getEnchantments().get(ench));
            }
        }
        for(Enchantment ench:binderMat.getEnchantments().keySet()){
            if(itemEnchantments.containsKey(ench)){
                Integer level = itemEnchantments.get(ench) + binderMat.getEnchantments().get(ench);
                itemEnchantments.put(ench, level);
            }else{
                itemEnchantments.put(ench,binderMat.getEnchantments().get(ench));
            }
        }
        for(Enchantment ench:rodMat.getEnchantments().keySet()){
            if(itemEnchantments.containsKey(ench)){
                Integer level = itemEnchantments.get(ench) + rodMat.getEnchantments().get(ench);
                itemEnchantments.put(ench, level);
            }else{
                itemEnchantments.put(ench,rodMat.getEnchantments().get(ench));
            }
        }

        EnchantmentHelper.setEnchantments(itemEnchantments,itemStack);


    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        // do not call super
        if (this.allowdedIn(pCategory)) { // yes, it's spelled this way
            pItems.get(0).setTag(new CompoundTag());
            pItems.get(0).getTag().putString("annessmithing.head_material","flint");
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if(!pPlayer.getItemInHand(pUsedHand).hasTag()){
            pPlayer.getItemInHand(pUsedHand).setTag(new CompoundTag());
        }
        setupToolMaterials(pPlayer.getItemInHand(pUsedHand), "flint", "string", "wood");
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        //return super.getDestroySpeed(pStack, pState);
        float destroySpeed = pStack.getTag().getFloat("annessmithing.break_speed");
        return pState.is(this.blocks) ? destroySpeed : 1.0f;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        Tier tier = ModMaterials.MATERIALS.get(stack.getTag().getString("annessmithing.head_material")).getToolHeadTier();
        return state.is(blocks) && net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(tier, state);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return stack.getTag().getInt("annessmithing.Durability");
    }


}
