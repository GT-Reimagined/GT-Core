package org.gtreimagined.gtcore.datagen;

import org.gtreimagined.gtcore.data.GTCoreFluids;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTLibTags;
import org.gtreimagined.gtlib.datagen.providers.GTFluidTagProvider;

public class GTCoreFluidTagProvider extends GTFluidTagProvider {
    public GTCoreFluidTagProvider(String providerDomain, String providerName, boolean replace) {
        super(providerDomain, providerName, replace);
    }

    @Override
    protected void processTags(String domain) {
        super.processTags(domain);
        if (GTAPI.isModLoaded(Ref.MOD_TWILIGHT)) {
            this.tag(GTLibTags.MAGIC).add(GTCoreFluids.FIERY_BLOOD.getFluid(), GTCoreFluids.FIERY_TEARS.getFluid());
        }
    }
}
