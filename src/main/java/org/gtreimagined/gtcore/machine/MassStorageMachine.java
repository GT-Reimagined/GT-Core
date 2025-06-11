package org.gtreimagined.gtcore.machine;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.item.ItemBlockMassStorage;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.Utils;

public class MassStorageMachine extends MaterialMachine{
    final int capacity;
    public MassStorageMachine(String domain, Material material, String suffix, int capacity) {
        super(domain, material.getId() + "_" + suffix, material);
        this.setTiers(Tier.NONE);
        this.setTile((m, p, s) -> new BlockEntityMassStorage(this, p, s));
        this.setItemBlock(ItemBlockMassStorage::new);
        this.addTooltipInfo((machine, stack, world, tooltip, flag) -> {
            tooltip.add(Utils.translatable("machine.mass_storage.capacity", capacity));
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
        setRenderAsTesr();
        setClientTicking();
        String barrel = material.has(MaterialTags.WOOD) ?  "item_storage" : material.has(MaterialTags.RUBBERTOOLS) ? "plastic_storage" : "mass_storage";
        setBaseTexture((m, t, s) -> new Texture[] {
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/bottom"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/top"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/side"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/side"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/side"),
                new Texture(GTCore.ID, "block/machine/base/" + barrel + "/side"),
        });
        setOverlayTextures((m, s, t, i) -> {
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
        GTAPI.register(MassStorageMachine.class, this);
    }

    public int getCapacity() {
        return capacity;
    }
}
