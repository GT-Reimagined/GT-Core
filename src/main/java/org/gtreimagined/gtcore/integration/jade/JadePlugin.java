package org.gtreimagined.gtcore.integration.jade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityRedstoneWire;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.TooltipPosition;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(RedstoneWireProvider.INSTANCE, BlockEntityRedstoneWire.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(MassStorageProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(RedstoneWireProvider.INSTANCE, Block.class);
    }
}
