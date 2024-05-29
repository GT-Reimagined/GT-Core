package io.github.gregtechintergalactical.gtcore.machine;

import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityMassStorage;
import io.github.gregtechintergalactical.gtcore.item.ItemBlockMassStorage;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

public class MassStorageMachine extends MaterialMachine{
    final int capacity;
    public MassStorageMachine(String domain, Material material, String suffix, int capacity) {
        super(domain, material.getId() + "_" + suffix, material);
        this.setTiers(Tier.NONE);
        this.setTile((m, p, s) -> new BlockEntityMassStorage(this, p, s));
        this.setItemBlock(ItemBlockMassStorage::new);
        this.addTooltipInfo((machine, stack, world, tooltip, flag) -> {
            CompoundTag nbt = stack.getTag();
            if (nbt != null){
                if (nbt.contains("inventories")){
                    CompoundTag inventories = nbt.getCompound("inventories");
                    if (inventories.contains("unlimited")){
                        CompoundTag unlimited = inventories.getCompound("unlimited");
                        ListTag items = unlimited.getList("Items", 10);
                        CompoundTag item = items.getCompound(0);
                        ItemStack contained = ItemStack.of(item);
                        contained.setCount(item.getInt("count"));
                        tooltip.add(Utils.translatable("machine.mass_storage.contains", item.getInt("count"), contained.getHoverName()));
                    }
                    if (inventories.contains("display")){
                        CompoundTag unlimited = inventories.getCompound("display");
                        ListTag items = unlimited.getList("Items", 10);
                        CompoundTag item = items.getCompound(0);
                        ItemStack contained = ItemStack.of(item);
                        tooltip.add(Utils.translatable("machine.mass_storage.display", contained.getHoverName()));
                    }
                }
                if (nbt.contains("output")){
                    tooltip.add(Utils.translatable("machine.mass_storage.output"));
                }
                if (nbt.contains("outputOverflow")){
                    tooltip.add(Utils.translatable("machine.mass_storage.output_overflow"));
                }
            }
        });
        this.capacity = capacity;
        tesr();
        setClientTicking();
        String barrel = material.has(MaterialTags.WOOD) ?  "item_storage" : material.has(MaterialTags.RUBBERTOOLS) ? "plastic_storage" : "mass_storage";
        baseTexture((m, t, s) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/bottom"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/top"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/side"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/side"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/side"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/side"),
        });
        overlayTexture((m, s, t, i) -> {
            s = s.getTextureState();
            String stateDir = s == MachineState.IDLE ? "" : s.getId() + "/";
            return new Texture[]{
                    new Texture(domain, "block/machine/overlay/" + barrel + "/" + stateDir + "bottom"),
                    new Texture(domain, "block/machine/overlay/" + barrel + "/" + stateDir + "top"),
                    new Texture(domain, "block/machine/overlay/" + barrel + "/" + stateDir + "back"),
                    new Texture(domain, "block/machine/overlay/" + barrel + "/" + stateDir + "front"),
                    new Texture(domain, "block/machine/overlay/" + barrel + "/" + stateDir + "side"),
                    new Texture(domain, "block/machine/overlay/" + barrel + "/" + stateDir + "side")
            };
        });
        AntimatterAPI.register(MassStorageMachine.class, this);
    }

    public int getCapacity() {
        return capacity;
    }
}
