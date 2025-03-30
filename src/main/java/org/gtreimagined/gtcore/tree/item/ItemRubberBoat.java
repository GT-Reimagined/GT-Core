package org.gtreimagined.gtcore.tree.item;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.registration.ITextureProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import org.gtreimagined.gtcore.GTCore;

import java.util.function.Predicate;

public class ItemRubberBoat extends TerraformBoatItem implements IAntimatterObject, ITextureProvider, IModelProvider {
    private static final Predicate<Entity> RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    static TerraformBoatType RUBBER_BOAT_TYPE;

    public ItemRubberBoat() {
        super(() -> RUBBER_BOAT_TYPE, new Properties().tab(Ref.TAB_ITEMS).stacksTo(1));
        GTAPI.register(ItemRubberBoat.class, this);
        TerraformBoatItemHelper.registerBoatDispenserBehavior(this, () -> RUBBER_BOAT_TYPE);
        RUBBER_BOAT_TYPE = new TerraformBoatType.Builder().item(this).build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new ResourceLocation(GTCore.ID, "rubber"), RUBBER_BOAT_TYPE);
    }

    @Override
    public String getId() {
        return "rubber_boat";
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[]{new Texture(GTCore.ID, "item/basic/rubber_boat")};
    }
}
