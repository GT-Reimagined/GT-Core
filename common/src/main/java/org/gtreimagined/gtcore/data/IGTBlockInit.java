package org.gtreimagined.gtcore.data;

import java.util.ServiceLoader;

public interface IGTBlockInit {
    IGTBlockInit INSTANCE =  ServiceLoader.load(IGTBlockInit.class).findFirst().orElse(null);
    void init();
}
