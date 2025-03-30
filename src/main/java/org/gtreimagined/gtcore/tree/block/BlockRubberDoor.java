package org.gtreimagined.gtcore.tree.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class BlockRubberDoor extends DoorBlock implements IGTObject, IModelProvider {
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
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        prov.tex(item, new Texture(GTCore.ID, "item/basic/rubber_door"));
    }

    @Override
    public void onBlockModelBuild(Block block, GTBlockStateProvider prov) {
        prov.doorBlock(this, new ResourceLocation(GTCore.ID, "block/tree/rubber_door_bottom"), new ResourceLocation(GTCore.ID, "block/tree/rubber_door_top"));
    }
}
