package com.annabelle.annessmithing.item.custom;

import com.annabelle.annessmithing.materials.Material;
import com.annabelle.annessmithing.materials.ModMaterials;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


public class CustomPickaxeItem extends DiggerItem {

    private final TagKey<Block> blocks;

    public CustomPickaxeItem(Properties p_204112_) {
        super(0.0f, 0.0f, Tiers.WOOD, BlockTags.MINEABLE_WITH_PICKAXE, p_204112_);
        this.blocks = BlockTags.MINEABLE_WITH_PICKAXE;
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
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if(!stack.hasTag()){
            stack.setTag(new CompoundTag());
        }
        stack.getTag().putString("annessmithing.head_material","flint");
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if(!pPlayer.getItemInHand(pUsedHand).hasTag()){
            pPlayer.getItemInHand(pUsedHand).setTag(new CompoundTag());
        }
        pPlayer.getItemInHand(pUsedHand).getTag().putString("annessmithing.head_material","flint");
        pPlayer.sendMessage(new TextComponent("Added flint head"), pPlayer.getUUID());
        pPlayer.sendMessage(new TextComponent("Head material is..."), pPlayer.getUUID());
        pPlayer.sendMessage(new TextComponent(pPlayer.getItemInHand(pUsedHand).getTag().getString("annessmithing.head_material")), pPlayer.getUUID());
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        //return super.getDestroySpeed(pStack, pState);
        Material headMat = ModMaterials.MATERIALS.get(pStack.getTag().getString("annessmithing.head_material"));

        return pState.is(this.blocks) ? headMat.getDestroySpeed() : 1.0f;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        Tier tier = ModMaterials.MATERIALS.get(stack.getTag().getString("annessmithing.head_material")).getToolHeadTier();
        return state.is(blocks) && net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(tier, state);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        Material headMat = ModMaterials.MATERIALS.get(stack.getTag().getString("annessmithing.head_material"));
        return headMat.getBaseDurrability();
    }
}
