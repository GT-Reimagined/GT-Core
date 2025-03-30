package org.gtreimagined.gtcore.tree.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;

public class BlockRubberTrapDoor extends TrapDoorBlock implements IGTObject, IModelProvider {
    public BlockRubberTrapDoor() {
        super(Properties.of(Material.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion());
        GTAPI.register(BlockRubberTrapDoor.class, this);
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
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.getBuilder(item).parent(new ResourceLocation(GTCore.ID, "block/rubber_trapdoor_bottom"));
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.trapdoorBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_trapdoor"), true);
    }
}
