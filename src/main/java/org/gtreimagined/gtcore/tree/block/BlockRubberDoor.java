package org.gtreimagined.gtcore.tree.block;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;

public class BlockRubberDoor extends DoorBlock implements IAntimatterObject, IModelProvider {
    public BlockRubberDoor() {
        super(Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion());
        GTAPI.register(BlockRubberDoor.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_door";
    }

    @Override
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.tex(item, new Texture(GTCore.ID, "item/basic/rubber_door"));
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.doorBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_door_bottom"), new ResourceLocation(GTCore.ID, "block/tree/rubber_door_top"));
    }
}
