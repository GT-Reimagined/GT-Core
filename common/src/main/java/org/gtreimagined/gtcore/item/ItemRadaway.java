package org.gtreimagined.gtcore.item;

import muramasa.antimatter.Ref;
import muramasa.antimatter.entity.IRadiationEntity;
import muramasa.antimatter.item.ItemBasic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.gtreimagined.gtcore.GTCore;

public class ItemRadaway extends ItemBasic<ItemRadaway>  {
    public ItemRadaway() {
        super(GTCore.ID, "radaway", new Properties().food(new FoodProperties.Builder().nutrition(0).saturationMod(0).alwaysEat().build()).tab(Ref.TAB_ITEMS));
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ((IRadiationEntity)livingEntity).changeRadiation(-50);
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
