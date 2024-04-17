package io.github.gregtechintergalactical.gtcore.data;

import io.github.gregtechintergalactical.gtcore.GTCore;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class GTCoreData {
    private static final String CAPE_PATH = "textures/capes/";
    public static final ResourceLocation[] CAPE_LOCATIONS = new ResourceLocation[] {new ResourceLocation(GTCore.ID,  CAPE_PATH + "braintech.png"), new ResourceLocation(GTCore.ID, CAPE_PATH + "silver.png"), new ResourceLocation(GTCore.ID, CAPE_PATH + "mrbrain.png"), new ResourceLocation(GTCore.ID, CAPE_PATH + "dev.png"), new ResourceLocation(GTCore.ID, CAPE_PATH + "gold.png"), new ResourceLocation(GTCore.ID, CAPE_PATH + "crazy.png"), new ResourceLocation(GTCore.ID, CAPE_PATH + "fake.png")};

    public static final Set<String> SupporterListSilver = new HashSet<>(), SupporterListGold = new HashSet<>();

    public static void init(){

    }
}
