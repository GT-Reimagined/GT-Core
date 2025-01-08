package org.gtreimagined.gtcore.tree.block;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.datagen.providers.AntimatterBlockStateProvider;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;

public class BlockRubberTrapDoor extends TrapDoorBlock implements IAntimatterObject, IModelProvider {
    public BlockRubberTrapDoor() {
        super(Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion());
        AntimatterAPI.register(BlockRubberTrapDoor.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_trapdoor";
    }

    @Override
    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        prov.getBuilder(item).parent(new ResourceLocation(GTCore.ID, "block/rubber_trapdoor_bottom"));
    }

    @Override
    public void onBlockModelBuild(Block block, AntimatterBlockStateProvider prov) {
        prov.trapdoorBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_trapdoor"), true);
    }
}
