package io.github.gregtechintergalactical.gtcore.data;

import tesseract.TesseractCapUtils;

import java.util.ServiceLoader;

public interface IGTBlockInit {
    IGTBlockInit INSTANCE =  ServiceLoader.load(IGTBlockInit.class).findFirst().orElse(null);
    void init();
}
