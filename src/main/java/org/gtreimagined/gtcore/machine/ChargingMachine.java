package org.gtreimagined.gtcore.machine;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.texture.Texture;

import static org.gtreimagined.gtlib.machine.MachineFlag.EU;


public class ChargingMachine extends MaterialMachine{
    public ChargingMachine(String domain, Material material, String suffix, boolean charge) {
        super(domain, material.getId() + (charge ? "_charging" : "") + "_" + suffix, material);
        if (charge) this.addFlags(EU);
        this.setTiers(charge ? Tier.HV : Tier.NONE);
        this.baseTexture((m, t, s) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/base/" + suffix + "/bottom"),
                new Texture(GTCore.ID, "block/machine/base/" + suffix + "/top"),
                new Texture(GTCore.ID, "block/machine/base/" + suffix + "/back"),
                new Texture(GTCore.ID, "block/machine/base/" + suffix + "/front"),
                new Texture(GTCore.ID, "block/machine/base/" + suffix + "/side"),
                new Texture(GTCore.ID, "block/machine/base/" + suffix + "/side"),
        });
        this.overlayTexture((type, state, tier, i) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/overlay/" + (charge ? "charging_" : "") + suffix + "/bottom"),
                new Texture(GTCore.ID, "block/machine/overlay/" + (charge ? "charging_" : "") + suffix + "/top"),
                new Texture(GTCore.ID, "block/machine/overlay/" + (charge ? "charging_" : "") + suffix + "/back"),
                new Texture(GTCore.ID, "block/machine/overlay/" + (charge ? "charging_" : "") + suffix + "/front"),
                new Texture(GTCore.ID, "block/machine/overlay/" + (charge ? "charging_" : "") + suffix + "/side"),
                new Texture(GTCore.ID, "block/machine/overlay/" + (charge ? "charging_" : "") + suffix + "/side"),
        });
    }
}
