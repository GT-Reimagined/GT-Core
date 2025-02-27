package org.gtreimagined.gtcore.integration.curio;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.gtreimagined.gtcore.GTCore;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CurioPlugin {
    public static void loadIMC(InterModEnqueueEvent mod) {
        InterModComms.sendTo(GTCore.ID, "curios", "register_type", () -> SlotTypePreset.BELT.getMessageBuilder().build());
    }
}
