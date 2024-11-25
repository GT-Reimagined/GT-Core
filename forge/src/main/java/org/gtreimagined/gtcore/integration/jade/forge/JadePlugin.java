package org.gtreimagined.gtcore.integration.jade.forge;

import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaCommonRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    public static ResourceLocation MASS_STORAGE = new ResourceLocation(GTCore.ID, "mass_storage");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.addConfig(MASS_STORAGE, true);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerComponentProvider(MassStorageProvider.INSTANCE, TooltipPosition.BODY, Block.class);
    }
}
