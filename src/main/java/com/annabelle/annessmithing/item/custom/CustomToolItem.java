package com.annabelle.annessmithing.item.custom;

import com.annabelle.annessmithing.materials.Material;
import com.annabelle.annessmithing.materials.ModMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
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
        itemStack.getTag().putInt("annessmithing.durability", (int)(headMat.getBaseDurability() * rodMat.getDurabilityModifier()));
        itemStack.getTag().putString("annessmithing.name_prefix", headMat.getNamePrefix());

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
    public Component getName(ItemStack pStack) {
        return new TranslatableComponent(pStack.getTag().getString("annessmithing.name_prefix")).append(
                new TranslatableComponent("annessmithing.tools.pickaxe"));
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
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        boolean levelUp = addToolXP(pStack, 10);
        if(levelUp){
            pEntityLiving.sendMessage(
                    new TextComponent("Tool level up"),
                    pEntityLiving.getUUID()
            );
            pEntityLiving.playSound(SoundEvents.PLAYER_LEVELUP,1.0f,1.0f);
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if(pStack.getTag().contains("annessmithing.tool_level")){
            TextComponent levelComponent = new TextComponent("Tool level: " + pStack.getTag().getInt("annessmithing.tool_level"));
            TextComponent xpComponent = new TextComponent("XP to next level: " + pStack.getTag().getInt("annessmithing.xp_to_next_level"));
            pTooltipComponents.add(levelComponent);
            pTooltipComponents.add(xpComponent);
        }
        if(pStack.getTag().contains("annessmithing.open_mod_slots")){
            TextComponent modSlotComponent = new TextComponent("Upgrade slots: " + pStack.getTag().getInt("annessmithing.open_mod_slots"));
            pTooltipComponents.add(modSlotComponent);
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return stack.getTag().getInt("annessmithing.durability");
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
