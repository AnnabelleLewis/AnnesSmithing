package com.annabelle.annessmithing.item.custom;

import com.annabelle.annessmithing.materials.ModMaterials;
import com.annabelle.annessmithing.util.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToolComponentItem extends Item {

    private String material;

    public ToolComponentItem(String material, Properties pProperties) {
        super(pProperties);
        this.material = material;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if(pStack.is(ModTags.Items.TOOL_BINDERS)){return;}
        if(pStack.is(ModTags.Items.TOOL_RODS)){
            pTooltipComponents.add(new TextComponent(
                    "Mining speed modifier: x" + ModMaterials.MATERIALS.get(material).getDestroySpeedMultiplier()
            ));
            pTooltipComponents.add(new TextComponent(
                    "Durability modifier: x" + ModMaterials.MATERIALS.get(material).getDurabilityModifier()
            ));
            pTooltipComponents.add(new TextComponent(
                    "Damage modifier: x" + ModMaterials.MATERIALS.get(material).getDamageModifier()
            ));
            return;
        }
        if(pStack.is(ModTags.Items.SWORD_HEADS)){
            pTooltipComponents.add(new TextComponent(
                    "Base damage: " + ModMaterials.MATERIALS.get(material).getBaseDamage()
                    )
            );
            pTooltipComponents.add(new TextComponent(
                            "Base durability: " + ModMaterials.MATERIALS.get(material).getBaseDurability()
                    )
            );
            return;
        }
        pTooltipComponents.add(new TextComponent(
                        "Base mining speed: " + ModMaterials.MATERIALS.get(material).getDestroySpeed()
                )
        );
        pTooltipComponents.add(new TextComponent(
                        "Base durability: " + ModMaterials.MATERIALS.get(material).getBaseDurability()
                )
        );
    }

    public String getMaterial(){return this.material;}

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }


}
