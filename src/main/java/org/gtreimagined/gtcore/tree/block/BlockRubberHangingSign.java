package org.gtreimagined.gtcore.tree.block;

import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.tree.item.ItemRubberHangingSign;
import org.gtreimagined.gtcore.tree.item.ItemRubberSign;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.registration.IGTObject;
import org.gtreimagined.gtlib.registration.IItemBlockProvider;

public class BlockRubberHangingSign extends TerraformHangingSignBlock implements IGTObject, IItemBlockProvider {

    public BlockRubberHangingSign() {
        super(Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).sound(SoundType.WOOD), GTCoreBlocks.RUBBER_WOOD_TYPE);
        GTAPI.register(BlockRubberHangingSign.class, this);
    }

    @Override
    public String getId() {
        return "rubber_hanging_sign";
    }
    @Override
    public String getDomain() {
        return GTCore.ID;
    }

    @Override
    public BlockItem getItemBlock() {
        return new ItemRubberHangingSign();
    }

    @Override
    public WoodType type() {
        return GTCoreBlocks.RUBBER_WOOD_TYPE;
    }
}
