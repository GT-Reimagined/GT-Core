package org.gtreimagined.gtcore.data;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.block.RedstoneWire;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.SubTag;

import static org.gtreimagined.gtlib.material.MaterialTags.CABLE;
import static org.gtreimagined.gtlib.material.MaterialTags.WIRE;


public class GTCoreCables {

    public static final SubTag TIN_WIRE = new SubTag("tin_wire");

    public static final SubTag TIN_CABLE = new SubTag("tin_cable");
    public static final RedstoneWire<?> WIRE_RED_ALLOY = GTAPI.register(RedstoneWire.class, new RedstoneWire<>(GTCore.ID, GTCoreMaterials.RedAlloy, 0xd00000)).range(16);
    public static final RedstoneWire<?> WIRE_LEADED_REDSTONE = GTAPI.register(RedstoneWire.class, new RedstoneWire<>(GTCore.ID, GTCoreMaterials.LeadedRedstone, 0xa30000)).range(1);
    public static final RedstoneWire<?> WIRE_SIGNALUM = GTAPI.register(RedstoneWire.class, new RedstoneWire<>(GTCore.ID, GTCoreMaterials.Signalum, 0xff4506)).range(64);
    public static final RedstoneWire<?> WIRE_LUMIUM = GTAPI.register(RedstoneWire.class, new RedstoneWire<>(GTCore.ID, GTCoreMaterials.Lumium, 0xffff54)).range(16).emitsLight();

    public static void init(){
        WIRE.subTag(TIN_WIRE, GTCoreMaterials.Tin);
        CABLE.subTag(TIN_CABLE, GTCoreMaterials.Tin);
    }
}
