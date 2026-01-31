package org.gtreimagined.gtcore.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTCreativeTabs;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.registration.ICreativeTabProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;
import org.jetbrains.annotations.Nullable;

public class ItemHazmatArmor extends ArmorItem implements IGTObject, ITextureProvider, IModelProvider, ICreativeTabProvider {
    static ArmorMaterial HAZMAT = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(Type slot) {
            return 128;
        }

        @Override
        public int getDefenseForType(Type slot) {
            return 1;
        }

        @Override
        public int getEnchantmentValue() {
            return 2;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_CHAIN;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return "hazmat";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };
    final String id;
    public ItemHazmatArmor(Type slot, String id) {
        super(HAZMAT, slot, new Properties());
        this.id = id;
        GTAPI.register(ItemHazmatArmor.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(getDomain(), "item/hazmat/" + getId())};
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String extra = "";
        if (slot == EquipmentSlot.HEAD && type != null) {
            CompoundTag nbt = stack.getTag();
            if (nbt != null && nbt.contains("theoneprobe") && nbt.getBoolean("theoneprobe")) extra = "_probe";
        }
        return GTCore.ID + ":textures/model/hazmat_" + (slot == EquipmentSlot.LEGS ? 2 : 1) + (type == null ? "" : "_" + type + extra) + ".png";
    }

    @Override
    public boolean allowedIn(ResourceKey<CreativeModeTab> creativeModeTab) {
        return creativeModeTab == GTCreativeTabs.ITEMS.getKey();
    }
}
