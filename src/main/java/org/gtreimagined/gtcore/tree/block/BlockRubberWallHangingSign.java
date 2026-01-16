package org.gtreimagined.gtcore.tree.block;

import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IItemBlockProvider;

public class BlockRubberWallHangingSign extends TerraformWallHangingSignBlock implements IGTObject, IItemBlockProvider {
    public BlockRubberWallHangingSign() {
        super(Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), GTCoreBlocks.RUBBER_WOOD_TYPE);
        GTAPI.register(BlockRubberWallHangingSign.class, this);
    }

    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public String getId() {
        return "rubber_wall_hanging_sign";
    }

    @Override
    public boolean generateItemBlock() {
        return false;
    }

    @Override
    public WoodType type() {
        return GTCoreBlocks.RUBBER_WOOD_TYPE;
    }
}
