package com.annabelle.annessmithing.item.custom;

import com.annabelle.annessmithing.materials.ModMaterials;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.HashMap;

public class CustomSwordItem extends TieredItem implements Vanishable {

    public CustomSwordItem(Item.Properties pProperties) {
        super(Tiers.WOOD, pProperties);

    }

    public void setupToolMaterials(ItemStack itemStack, String headMaterial, String binderMaterial, String rodMaterial){
        // Apply material tags
        itemStack.getTag().putString("annessmithing.head_material",headMaterial);
        itemStack.getTag().putString("annessmithing.binder_material",binderMaterial);
        itemStack.getTag().putString("annessmithing.rod_material",rodMaterial);

        // Get material objects
        com.annabelle.annessmithing.materials.Material headMat = ModMaterials.MATERIALS.get(itemStack.getTag().getString("annessmithing.head_material"));
        com.annabelle.annessmithing.materials.Material binderMat = ModMaterials.MATERIALS.get(itemStack.getTag().getString("annessmithing.binder_material"));
        com.annabelle.annessmithing.materials.Material rodMat = ModMaterials.MATERIALS.get(itemStack.getTag().getString("annessmithing.rod_material"));

        // Initialize base stats from head (Damage, Mining speed, Mining level, Durability)...
        // and apply modifiers from handle
        itemStack.getTag().putFloat("annessmithing.break_speed", headMat.getDestroySpeed() * rodMat.getDestroySpeedMultiplier());
        itemStack.getTag().putInt("annessmithing.durability", (int)(headMat.getBaseDurability() * rodMat.getDurabilityModifier()));
        itemStack.getTag().putString("annessmithing.name_prefix", headMat.getNamePrefix());
        itemStack.getTag().putInt("annessmithing.attack_damage", (int)(headMat.getBaseDamage() * rodMat.getDamageModifier()));

        // Set color tags
        itemStack.getTag().putLong("annessmithing.head_color", headMat.getColor());
        itemStack.getTag().putLong("annessmithing.binder_color", binderMat.getColor());
        itemStack.getTag().putLong("annessmithing.rod_color", rodMat.getColor());

        // Apply enchantments from all three

        HashMap<Enchantment, Integer> itemEnchantments = new HashMap<Enchantment, Integer>();
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
    public int getDamage(ItemStack stack) {
        float damageMod = 1.0f + (stack.getTag().getInt("annessmithing.attack_damage_upgrades") * 0.1f);
        return Math.round(stack.getTag().getInt("annessmithing.attack_damage") * damageMod);
    }

    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (pState.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = pState.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !pState.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        addToolXP(pStack, 1);
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
            pStack.hurtAndBreak(2, pEntityLiving, (p_43276_) -> {
                p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return pBlock.is(Blocks.COBWEB);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        float durrabilityMod = 1.0f + (stack.getTag().getInt("annessmithing.durrability_upgrades") * 0.1f);
        return Math.round(stack.getTag().getInt("annessmithing.durability") * durrabilityMod);
    }

    public boolean addToolXP(ItemStack stack, int xp){
        // Test if tool has level/xp if not, initialize to level 0, 100 xp to next level
        if(!stack.getTag().contains("annessmithing.tool_level")){
            stack.getTag().putInt("annessmithing.tool_level", 0);
            setXPToNextLevel(stack);
        }
        // Decrement xp to next level
        int xpToNext = stack.getTag().getInt("annessmithing.xp_to_next_level");
        xpToNext -= xp;

        // If XP to next level <= 0, increment level, add modifier slot, reset xp to next level
        if(xpToNext <= 0){
            stack.getTag().putInt("annessmithing.tool_level",
                    stack.getTag().getInt("annessmithing.tool_level") + 1);
            setXPToNextLevel(stack);
            if(!stack.getTag().contains("annessmithing.open_mod_slots")){
                stack.getTag().putInt("annessmithing.open_mod_slots", 1);
            }else{
                stack.getTag().putInt("annessmithing.open_mod_slots",
                        stack.getTag().getInt("annessmithing.open_mod_slots") + 1);
            }
            return true;
        }else{
            stack.getTag().putInt("annessmithing.xp_to_next_level", xpToNext);
        }
        return false;
    }


    public void setXPToNextLevel(ItemStack stack){
        int level = stack.getTag().getInt("annessmithing.tool_level");
        int xpToNext = (int) Math.round(80.0 * Math.pow((double)level, 1.1));
        xpToNext += 100;
        stack.getTag().putInt("annessmithing.xp_to_next_level", xpToNext);
    }
}
