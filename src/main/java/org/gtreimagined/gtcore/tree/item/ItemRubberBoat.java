package org.gtreimagined.gtcore.tree.item;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.IRegistryEntryProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;

import java.util.function.Predicate;

public class ItemRubberBoat extends TerraformBoatItem implements IGTObject, ITextureProvider, IModelProvider, IRegistryEntryProvider {
    private static final Predicate<Entity> RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    static TerraformBoatType RUBBER_BOAT_TYPE;
    private final boolean chest;

    public ItemRubberBoat(boolean chest) {
        super(() -> RUBBER_BOAT_TYPE, chest, new Properties().tab(Ref.TAB_ITEMS).stacksTo(1));
        this.chest = chest;
        GTAPI.register(ItemRubberBoat.class, this);
        TerraformBoatItemHelper.registerBoatDispenserBehavior(this, () -> RUBBER_BOAT_TYPE, false);
    }

    public static void initBoatType(){
        RUBBER_BOAT_TYPE = new TerraformBoatType.Builder().item(GTCoreItems.RubberBoat).chestItem(GTCoreItems.RubberChestBoat).planks(GTCoreBlocks.RUBBER_PLANKS.asItem()).build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new ResourceLocation(GTCore.ID, "rubber"), RUBBER_BOAT_TYPE);
    }

    @Override
    public String getId() {
        return "rubber" + (chest ? "_chest" : "") + "_boat";
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(GTCore.ID, "item/basic/rubber" + (chest ? "_chest" : "") + "_boat")};
    }

    @Override
    public void onRegistryBuild(ResourceKey<? extends Registry<?>> resourceKey) {
        if (resourceKey == Keys.ITEMS && RUBBER_BOAT_TYPE == null){
            ItemRubberBoat.initBoatType();
        }
    }
}
