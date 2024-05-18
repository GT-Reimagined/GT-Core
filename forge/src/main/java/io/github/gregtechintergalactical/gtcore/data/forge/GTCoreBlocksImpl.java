package io.github.gregtechintergalactical.gtcore.data.forge;


import io.github.gregtechintergalactical.gtcore.data.IGTBlockInit;
import io.github.gregtechintergalactical.gtcore.integration.tfc.TFCRubberData;

public class GTCoreBlocksImpl implements IGTBlockInit {
    public void init(){
        TFCRubberData.init();
    }
}
