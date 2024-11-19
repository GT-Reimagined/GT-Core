package org.gtreimagined.gtcore.machine;

import com.google.common.collect.ImmutableMap;
import muramasa.antimatter.datagen.builder.AntimatterBlockModelBuilder;
import muramasa.antimatter.datagen.json.JLoaderModel;
import org.gtreimagined.gtcore.GTCore;
import muramasa.antimatter.Ref;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.datagen.builder.AntimatterItemModelBuilder;
import muramasa.antimatter.datagen.providers.AntimatterItemModelProvider;
import muramasa.antimatter.machine.BlockMachine;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.registration.IColorHandler;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.List;

import static muramasa.antimatter.Data.WRENCH_MATERIAL;
import static muramasa.antimatter.material.Material.NULL;

public class BlockMachineMaterial extends BlockMachine {
    Material material = NULL;
    public BlockMachineMaterial(Machine<?> type, Tier tier) {
        this(type, tier, Properties.of(WRENCH_MATERIAL).strength(1.0f, 10.0f).sound(SoundType.METAL).requiresCorrectToolForDrops());
    }

    public BlockMachineMaterial(Machine<?> type, Tier tier, Properties properties) {
        super(type, tier, properties);
        if (type instanceof MaterialMachine){
            this.material = ((MaterialMachine)type).getMaterial();
        }
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public int getBlockColor(BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, int i) {
        if (i == 2 && world != null && pos != null) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof BlockEntityMassStorage massStorage){
                var storage = massStorage.itemHandler.map(h -> h.getHandler(SlotTypes.UNLIMITED)).orElse(null);
                if (storage != null){
                    int max = massStorage.getMaxLimit();
                    int count = storage.getItem(0).getCount();
                    if (max == count) return 0xAA0000;
                }
            }
        }
        return i == 0 ? material.getRGB() : -1;
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return i == 0 ? material.getRGB() : -1;
    }

    public void onItemModelBuild(ItemLike item, AntimatterItemModelProvider prov) {
        if (!(type instanceof MassStorageMachine) && !(type instanceof BarrelMachine)) {
            super.onItemModelBuild(item, prov);
            return;
        }
        AntimatterItemModelBuilder b = prov.getBuilder(item).parent(prov.existing("antimatter", "block/preset/layered")).texture("base", this.type.getBaseTexture(this.tier, MachineState.IDLE)[0]);
        Texture[] base = this.type.getBaseTexture(this.tier, MachineState.IDLE);
        if (base.length >= 6) {
            for(int s = 0; s < 6; ++s) {
                b.texture("base" + Utils.coverRotateFacing(Ref.DIRS[s], Direction.NORTH).getSerializedName(), base[s]);
            }
        }
        for (int i = 0; i < type.getOverlayLayers(); i++) {
            Texture[] overlays = type.getOverlayTextures(MachineState.IDLE, tier, i);
            for (int s = 0; s < 6; s++) {
                String suffix = i == 0 ? "" : String.valueOf(i);
                b.texture("overlay" + Utils.coverRotateFacing(Ref.DIRS[s], Direction.NORTH).getSerializedName() + suffix, overlays[s]);
            }
        }
        if (!(type instanceof MassStorageMachine)) return;
        b.override().predicate(new ResourceLocation(GTCore.ID, "taped"), 1.0F).model(new ResourceLocation(getDomain(), "item/" +id + "_taped")).end();
        b = prov.getBuilder(getId() + "_taped").parent(prov.existing("antimatter", "block/preset/layered")).texture("base", this.type.getBaseTexture(this.tier, MachineState.ACTIVE)[0]);
        if (base.length >= 6) {
            for(int s = 0; s < 6; ++s) {
                b.texture("base" + Utils.coverRotateFacing(Ref.DIRS[s], Direction.NORTH).getSerializedName(), base[s]);
            }
        }

        for (int i = 0; i < type.getOverlayLayers(); i++) {
            Texture[] overlays = type.getOverlayTextures(MachineState.ACTIVE, tier, i);
            for (int s = 0; s < 6; s++) {
                String suffix = i == 0 ? "" : String.valueOf(i);
                b.texture("overlay" + Utils.coverRotateFacing(Ref.DIRS[s], Direction.NORTH).getSerializedName() + suffix, overlays[s]);
            }
        }

    }

    @Override
    protected void buildModelsForState(AntimatterBlockModelBuilder builder, MachineState state) {
        List<JLoaderModel> arr = new ArrayList<>();

        for (Direction dir : Ref.DIRS) {
            ImmutableMap.Builder<String, String> builder1 = ImmutableMap.builder();
            builder1.put("base", getType().getBaseTexture(tier, dir, state).toString());
            for (int i = 0; i < type.getOverlayLayers(); i++) {
                String suffix = i == 0 ? "" : String.valueOf(i);
                builder1.put("overlay" + suffix, type.getOverlayTextures(state, tier, i)[dir.get3DDataValue()].toString());
            }
            JLoaderModel obj = builder.addModelObject(JLoaderModel.modelKeepElements(), this.getType().getOverlayModel(state, dir).toString(), builder1.build());
            if (getType() instanceof MassStorageMachine && dir == Direction.SOUTH) obj.loader("gtcore:icon");
            arr.add(obj);
        }

        builder.property(state.toString().toLowerCase(), arr);
    }
}
