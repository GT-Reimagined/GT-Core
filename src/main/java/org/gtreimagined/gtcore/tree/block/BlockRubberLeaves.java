package org.gtreimagined.gtcore.tree.block;

import net.minecraft.world.level.block.LeavesBlock;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.block.BlockPropertiesHelper;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IModelProvider;
import org.gtreimagined.gtlib.registration.ITextureProvider;
import org.gtreimagined.gtlib.texture.Texture;

public class BlockRubberLeaves extends LeavesBlock implements IGTObject, IModelProvider, ITextureProvider {

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
