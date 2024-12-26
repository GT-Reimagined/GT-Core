package org.gtreimagined.gtcore.integration.jade.forge;

import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaCommonRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    public static ResourceLocation MASS_STORAGE = new ResourceLocation(GTCore.ID, "mass_storage");
    public static ResourceLocation REDSTONE_WIRE = new ResourceLocation(GTCore.ID, "redstone_wire");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.addConfig(MASS_STORAGE, true);
        registration.addConfig(REDSTONE_WIRE, true);
        registration.registerBlockDataProvider(RedstoneWireProvider.INSTANCE, BlockEntityRedstoneWire.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerComponentProvider(MassStorageProvider.INSTANCE, TooltipPosition.BODY, Block.class);
        registration.registerComponentProvider(RedstoneWireProvider.INSTANCE, TooltipPosition.BODY, Block.class);
    }
}
