package org.gtreimagined.gtcore.machine;

import com.google.common.collect.ImmutableMap;
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
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.blockentity.BlockEntityMassStorage;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.builder.GTBlockModelBuilder;
import org.gtreimagined.gtlib.datagen.builder.GTItemModelBuilder;
import org.gtreimagined.gtlib.datagen.json.JLoaderModel;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.machine.BlockMachine;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static org.gtreimagined.gtlib.Data.WRENCH_MATERIAL;
import static org.gtreimagined.gtlib.material.Material.NULL;

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
                    int count = storage.getStackInSlot(0).getCount();
                    if (max == count) return 0xFF0000;
                    if (massStorage.getMassStorageMachine().getMaterial().has(MaterialTags.WOOD)) return 0x000000;
                }
            }
        }
        return i == 0 ? material.getRGB() : -1;
    }

    @Override
    public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
        return i == 0 ? material.getRGB() : -1;
    }

    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        if (!(type instanceof MassStorageMachine) && !(type instanceof BarrelMachine) && !(type instanceof BookShelfMachine)) {
            super.onItemModelBuild(item, prov);
            return;
        }
        GTItemModelBuilder b = prov.getBuilder(item).parent(prov.existing("gtlib", "block/preset/layered")).texture("base", this.type.getBaseTexture(this.tier, MachineState.IDLE)[0]);
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
        b = prov.getBuilder(getId() + "_taped").parent(prov.existing("gtlib", "block/preset/layered")).texture("base", this.type.getBaseTexture(this.tier, MachineState.ACTIVE)[0]);
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
    protected void buildModelsForState(GTBlockModelBuilder builder, MachineState state) {
        if (!(getType() instanceof MassStorageMachine) && !(getType() instanceof BookShelfMachine)){
            super.buildModelsForState(builder, state);
            return;
        }
        List<JLoaderModel> arr = new ArrayList<>();

        for (Direction dir : Ref.DIRS) {
            ImmutableMap.Builder<String, String> builder1 = ImmutableMap.builder();
            builder1.put("base", getType().getBaseTexture(tier, dir, state).toString());
            for (int i = 0; i < type.getOverlayLayers(); i++) {
                String suffix = i == 0 ? "" : String.valueOf(i);
                builder1.put("overlay" + suffix, type.getOverlayTextures(state, tier, i)[dir.get3DDataValue()].toString());
            }
            JLoaderModel obj = builder.addModelObject(JLoaderModel.modelKeepElements(), this.getType().getOverlayModel(state, dir).toString(), builder1.build());
            if (getType() instanceof MassStorageMachine) {
                if (dir == Direction.SOUTH) obj.loader("gtcore:icon");
            } else if (dir == Direction.NORTH || dir == Direction.SOUTH) obj.loader("gtcore:bookshelf");
            arr.add(obj);
        }

        builder.property(state.toString().toLowerCase(), arr);
    }
}
