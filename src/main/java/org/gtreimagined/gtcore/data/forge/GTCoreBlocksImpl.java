package org.gtreimagined.gtcore.data.forge;


import org.gtreimagined.gtcore.data.IGTBlockInit;
import org.gtreimagined.gtcore.integration.tfc.TFCRubberData;

public class GTCoreBlocksImpl implements IGTBlockInit {
    public void init(){
        TFCRubberData.init();
    }
}
