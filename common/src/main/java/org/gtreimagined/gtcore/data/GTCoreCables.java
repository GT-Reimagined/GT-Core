package org.gtreimagined.gtcore.data;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.RedstoneWire;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.SubTag;
import muramasa.antimatter.pipe.types.Cable;
import muramasa.antimatter.pipe.types.Wire;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.RedstoneWire;

import static org.gtreimagined.gtcore.data.GTCoreMaterials.*;
import static muramasa.antimatter.material.MaterialTags.CABLE;
import static muramasa.antimatter.material.MaterialTags.WIRE;

public class GTCoreCables {

    public static final SubTag TIN_WIRE = new SubTag("tin_wire");

    public static final SubTag TIN_CABLE = new SubTag("tin_cable");
    public static final RedstoneWire<?> WIRE_RED_ALLOY = AntimatterAPI.register(RedstoneWire.class, new RedstoneWire<>(GTCore.ID, GTCoreMaterials.RedAlloy, 0xd00000)).range(16);
    public static final RedstoneWire<?> WIRE_LEADED_REDSTONE = AntimatterAPI.register(RedstoneWire.class, new RedstoneWire<>(GTCore.ID, GTCoreMaterials.LeadedRedstone, 0xd00000)).range(1);

    public static void init(){
        WIRE.subTag(TIN_WIRE, GTCoreMaterials.Tin);
        CABLE.subTag(TIN_CABLE, GTCoreMaterials.Tin);
    }
}
