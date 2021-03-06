package com.annabelle.annessmithing.item.custom;

import com.annabelle.annessmithing.materials.Material;
import com.annabelle.annessmithing.materials.ModMaterials;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class CustomToolItem extends DiggerItem {

    protected static final Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> TILLABLES = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.DIRT_PATH, Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.DIRT, Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.COARSE_DIRT, Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.DIRT.defaultBlockState())), Blocks.ROOTED_DIRT, Pair.of((p_150861_) -> {
        return true;
    }, changeIntoStateAndDropItem(Blocks.DIRT.defaultBlockState(), Items.HANGING_ROOTS))));

    private final String nameSuffix;
    private final TagKey<Block> blocks;
    private final Boolean isHoe;

    public CustomToolItem(TagKey breakableBlocks, String nameSuffix, Properties p_204112_) {
        super(0.0f, 0.0f, Tiers.WOOD, breakableBlocks, p_204112_);
        this.nameSuffix = nameSuffix;
        this.blocks = breakableBlocks;
        this.isHoe = false;
    }

    public CustomToolItem(TagKey breakableBlocks, String nameSuffix, Properties p_204112_, Boolean is_hoe) {
        super(0.0f, 0.0f, Tiers.WOOD, breakableBlocks, p_204112_);
        this.nameSuffix = nameSuffix;
        this.blocks = breakableBlocks;
        this.isHoe = is_hoe;
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
    public InteractionResult useOn(UseOnContext pContext) {
        if(isHoe){
            return hoeUse(pContext);
        }
        return super.useOn(pContext);
    }

    @Override
    public Component getName(ItemStack pStack) {
        return new TranslatableComponent(pStack.getTag().getString("annessmithing.name_prefix")).append(
                new TranslatableComponent(nameSuffix));
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        // do not call super
        if (this.allowdedIn(pCategory)) { // yes, it's spelled this way
            pItems.get(0).setTag(new CompoundTag());
            pItems.get(0).getTag().putString("annessmithing.head_material","flint");
        }
    }
    /*
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if(!pPlayer.getItemInHand(pUsedHand).hasTag()){
            pPlayer.getItemInHand(pUsedHand).setTag(new CompoundTag());
        }
        setupToolMaterials(pPlayer.getItemInHand(pUsedHand), "flint", "string", "wood");
        return super.use(pLevel, pPlayer, pUsedHand);
    }*/

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        //return super.getDestroySpeed(pStack, pState);
        if(pStack.getDamageValue() == getMaxDamage(pStack) - 1){return 0f;}
        float destroySpeed = pStack.getTag().getFloat("annessmithing.break_speed");
        float destroySpeedMod = 1.0f + ((float)pStack.getTag().getInt("annessmithing.break_speed_upgrades") * 0.1f);
        return pState.is(this.blocks) ? destroySpeed * destroySpeedMod : 1.0f;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        Tier tier = ModMaterials.MATERIALS.get(stack.getTag().getString("annessmithing.head_material")).getToolHeadTier();
        boolean tierFlag = net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(tier, state);
        boolean copperFlag = state.is(Tags.Blocks.ORES_COPPER);
        return state.is(blocks) && (tierFlag || copperFlag);
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
        if(pStack.getTag().contains("annessmithing.break_speed")){
            float destroySpeed = pStack.getTag().getFloat("annessmithing.break_speed");
            float destroySpeedMod = 1.0f + ((float)pStack.getTag().getInt("annessmithing.break_speed_upgrades") * 0.1f);

            TextComponent breakSpeedComponent = new TextComponent("Mining speed: " + (destroySpeed * destroySpeedMod));
            pTooltipComponents.add(breakSpeedComponent);
        }
        pTooltipComponents.add(new TextComponent(
                "Repair level: " +
                        ModMaterials.MATERIALS.get(pStack.getTag().getString("annessmithing.head_material")).getRepairTier()
        ));
        TextComponent damageComponent = new TextComponent("Durability: " + (pStack.getMaxDamage()-1 - pStack.getDamageValue()) + "/" + (pStack.getMaxDamage()-1));
        pTooltipComponents.add(damageComponent);
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
        float durrabilityMod = 1.0f + (stack.getTag().getInt("annessmithing.durability_upgrades") * 0.1f);
        return Math.round(stack.getTag().getInt("annessmithing.durability") * durrabilityMod) + 1;
    }

    // Hoe exclusive actions

    public InteractionResult hoeUse(UseOnContext pContext){
        //int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(pContext);
        //if (hook != 0) return hook > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(pContext, net.minecraftforge.common.ToolActions.HOE_TILL, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();
            if (predicate.test(pContext)) {
                Player player = pContext.getPlayer();
                level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    consumer.accept(pContext);
                    if (player != null) {
                        pContext.getItemInHand().hurtAndBreak(1, player, (p_150845_) -> {
                            p_150845_.broadcastBreakEvent(pContext.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static Consumer<UseOnContext> changeIntoState(BlockState pState) {
        return (p_150848_) -> {
            p_150848_.getLevel().setBlock(p_150848_.getClickedPos(), pState, 11);
        };
    }

    public static Consumer<UseOnContext> changeIntoStateAndDropItem(BlockState pState, ItemLike pItemToDrop) {
        return (p_150855_) -> {
            p_150855_.getLevel().setBlock(p_150855_.getClickedPos(), pState, 11);
            Block.popResourceFromFace(p_150855_.getLevel(), p_150855_.getClickedPos(), p_150855_.getClickedFace(), new ItemStack(pItemToDrop));
        };
    }

    public static boolean onlyIfAirAbove(UseOnContext p_150857_) {
        return p_150857_.getClickedFace() != Direction.DOWN && p_150857_.getLevel().getBlockState(p_150857_.getClickedPos().above()).isAir();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction);
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
