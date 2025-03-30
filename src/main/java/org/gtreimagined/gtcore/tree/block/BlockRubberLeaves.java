package org.gtreimagined.gtcore.tree.block;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.block.BlockPropertiesHelper;
import muramasa.antimatter.registration.IAntimatterObject;
import muramasa.antimatter.registration.IModelProvider;
import muramasa.antimatter.registration.ITextureProvider;
import muramasa.antimatter.texture.Texture;
import net.minecraft.world.level.block.LeavesBlock;
import org.gtreimagined.gtcore.GTCore;

public class BlockRubberLeaves extends LeavesBlock implements IAntimatterObject, IModelProvider, ITextureProvider {

    public BlockRubberLeaves() {
        super(BlockPropertiesHelper.leaves());
        GTAPI.register(BlockRubberLeaves.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_leaves";
    }

    @Override
    public Texture[] getTextures() {
        return new Texture[] { new Texture(getDomain(), "block/tree/" + getId()) };
    }
}
