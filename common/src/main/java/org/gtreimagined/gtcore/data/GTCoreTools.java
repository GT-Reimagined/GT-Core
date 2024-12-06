package org.gtreimagined.gtcore.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.BlockTags;
import org.gtreimagined.gtcore.behaviour.BehaviourElectricWrenchSwitching;
import org.gtreimagined.gtcore.behaviour.BehaviourKnifeTooltip;
import org.gtreimagined.gtcore.behaviour.BehaviourMultitoolSwitching;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Data;
import muramasa.antimatter.Ref;
import muramasa.antimatter.capability.energy.ItemEnergyHandler;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.item.ItemBattery;
import muramasa.antimatter.machine.BlockMachine;
import muramasa.antimatter.pipe.BlockPipe;
import muramasa.antimatter.registration.Side;
import muramasa.antimatter.tool.AntimatterItemTier;
import muramasa.antimatter.tool.AntimatterToolType;
import muramasa.antimatter.tool.IAntimatterTool;
import muramasa.antimatter.tool.MaterialSword;
import muramasa.antimatter.tool.MaterialTool;
import muramasa.antimatter.tool.behaviour.*;
import muramasa.antimatter.util.TagUtils;
import muramasa.antimatter.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.gtreimagined.gtcore.behaviour.BehaviourElectricWrenchSwitching;
import org.gtreimagined.gtcore.behaviour.BehaviourKnifeTooltip;
import org.gtreimagined.gtcore.behaviour.BehaviourMultitoolSwitching;
import org.jetbrains.annotations.Nullable;
import tesseract.TesseractCapUtils;
import tesseract.api.gt.IEnergyHandlerItem;
import tesseract.api.gt.IGTNode;

import java.util.List;
import java.util.Map;

import static org.gtreimagined.gtcore.data.GTCoreItems.*;
import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static net.minecraft.world.level.material.Material.*;
import static net.minecraft.world.level.material.Material.LEAVES;

public class GTCoreTools {
    private static final AntimatterToolType.IToolSupplier POWERED_TOOL_SUPPLIER = new AntimatterToolType.IToolSupplier() {
        @Override
        public IAntimatterTool create(String domain, AntimatterToolType toolType, AntimatterItemTier tier, Item.Properties properties) {
            return null;
        }

        @Override
        public IAntimatterTool create(String domain, AntimatterToolType toolType, AntimatterItemTier tier, Item.Properties properties, int energyTier) {
            return new PoweredTool(domain, toolType, tier, properties, energyTier);
        }
    };
    public static final AntimatterToolType DRILL = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "drill", 1, 2, 10, 3.0F, -3.0F, false)).setToolSupplier(POWERED_TOOL_SUPPLIER).setType(PICKAXE).setUseAction(UseAnim.BLOCK).setPowered(100000, 1, 2, 3).setMaterialTypeItem(DRILLBIT).setUseSound(Ref.DRILL).addTags("pickaxe", "shovel").addEffectiveMaterials(ICE_SOLID, PISTON, DIRT, CLAY, GRASS, SAND).setRepairability(false);
    public static final AntimatterToolType BUZZSAW = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "buzzsaw", 1, 1, 1, 0.5F, -2.7F, false)).setToolSupplier(POWERED_TOOL_SUPPLIER).setTag(SAW).setPowered(100000, 1, 2, 3).setOverlayLayers(2).addTags("saw").setMaterialTypeItem(BUZZSAW_BLADE);
    public static final AntimatterToolType ELECTRIC_SCREWDRIVER = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "electric_screwdriver", SCREWDRIVER)).setTag(SCREWDRIVER).setPowered(100000, 1, 2, 3).setToolSupplier(POWERED_TOOL_SUPPLIER).setUseSound(Ref.WRENCH).setOverlayLayers(2).setTag(SCREWDRIVER);
    public static final AntimatterToolType ELECTRIC_WRENCH = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "electric_wrench", WRENCH).setTag(WRENCH).setPowered(100000, 1, 2, 3)).setToolSupplier(POWERED_TOOL_SUPPLIER).setUseSound(Ref.WRENCH).addEffectiveBlocks(Blocks.HOPPER).setType(WRENCH).setMaterialTypeItem(WRENCHBIT).addBlacklistedEnchantments(Enchantments.BLOCK_EFFICIENCY);
    public static final AntimatterToolType ELECTRIC_WRENCH_ALT = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "electric_wrench_alt", WRENCH).setTag(WRENCH_ALT).setPowered(100000, 1, 2, 3)).setToolSupplier(POWERED_TOOL_SUPPLIER).setUseSound(Ref.WRENCH).addEffectiveBlocks(Blocks.HOPPER).setType(WRENCH).setMaterialTypeItem(WRENCHBIT).addBlacklistedEnchantments(Enchantments.BLOCK_EFFICIENCY).setCustomName("Electric Wrench (Alt)");
    public static final AntimatterToolType CHAINSAW = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "chainsaw", 1, 1, 5, 6.0F, -2.0F, false)).setToolSupplier(POWERED_TOOL_SUPPLIER).setUseAction(UseAnim.BLOCK).setPowered(100000, 1, 2, 3).setMaterialTypeItem(CHAINSAWBIT).addEffectiveMaterials(WOOD, PLANT, REPLACEABLE_PLANT, BAMBOO, LEAVES).addEffectiveBlocks(Blocks.COBWEB).setType(AXE).addTags("saw");

    public static final AntimatterToolType JACKHAMMER = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "jackhammer", 1, 2, 10, 1.0F, -3.2F, false)).setToolSupplier(POWERED_TOOL_SUPPLIER).setPowered(100000, 3).setToolSpeedMultiplier(2.0f).setUseSound(Ref.DRILL).addEffectiveBlockTags(TagUtils.getForgelikeBlockTag("stone"), TagUtils.getForgelikeBlockTag("cobblestone"), BlockTags.NYLIUM).addEffectiveBlocks(Blocks.BASALT, Blocks.NETHERRACK, Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.DRIPSTONE_BLOCK, Blocks.END_STONE);

    public static final AntimatterToolType POCKET_MULTITOOL = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "pocket_multitool", 1, 2, 2, 1.0f, -1.5f, false)).setDurabilityMultiplier(4.0f).setToolSupplier(PocketMultitoolTool::new).setMaterialTypeItem(RING);
    public static final AntimatterToolType POCKET_MULTITOOL_KNIFE = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "pocket_multitool_knife", KNIFE)).setDurabilityMultiplier(4.0f).setToolSupplier(PocketMultitoolTool::new).setCustomName("Pocket Multitool (Knife)").setTag(new ResourceLocation(Ref.ID, "knives")).setType(KNIFE);
    public static final AntimatterToolType POCKET_MULTITOOL_SAW = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "pocket_multitool_saw", SAW)).setDurabilityMultiplier(4.0f).setToolSupplier(PocketMultitoolTool::new).setCustomName("Pocket Multitool (Saw)").setType(SAW).setTag(SAW);
    public static final AntimatterToolType POCKET_MULTITOOL_FILE = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "pocket_multitool_file", FILE)).setDurabilityMultiplier(4.0f).setToolSupplier(PocketMultitoolTool::new).setCustomName("Pocket Multitool (File)").setType(FILE).setTag(FILE);
    public static final AntimatterToolType POCKET_MULTITOOL_SCREWDRIVER = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "pocket_multitool_screwdriver", SCREWDRIVER)).setDurabilityMultiplier(4.0f).setToolSupplier(PocketMultitoolTool::new).setCustomName("Pocket Multitool (Screwdriver)").setTag(SCREWDRIVER).setType(SCREWDRIVER).setUseSound(Ref.WRENCH);
    public static final AntimatterToolType POCKET_MULTITOOL_WIRE_CUTTER = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "pocket_multitool_wire_cutter", WIRE_CUTTER)).setDurabilityMultiplier(4.0f).setToolSupplier(PocketMultitoolTool::new).setCustomName("Pocket Multitool (Wire Cutter)").setTag(WIRE_CUTTER).setType(WIRE_CUTTER).setUseSound(SoundEvents.SHEEP_SHEAR).addEffectiveMaterials(WOOL, SPONGE, WEB, CLOTH_DECORATION);
    public static final AntimatterToolType POCKET_MULTITOOL_SCISSORS = AntimatterAPI.register(AntimatterToolType.class, new AntimatterToolType(Ref.ID, "pocket_multitool_scissors", SCISSORS)).setDurabilityMultiplier(4.0f).setToolSupplier(PocketMultitoolTool::new).setCustomName("Pocket Multitool (Scissors)").setTag(SCISSORS).setType(SCISSORS);

    public static class PoweredTool extends MaterialTool {

        public PoweredTool(String domain, AntimatterToolType type, AntimatterItemTier tier, Properties properties, int energyTier) {
            super(domain, type, tier, properties, energyTier);
        }

        @Override
        public float getDefaultMiningSpeed(ItemStack stack) {
            return super.getDefaultMiningSpeed(stack) * (3 * energyTier);
        }

        @Override
        public int getDefaultEnergyUse() {
            int defaultUse = (int) (25 * Math.pow(2, energyTier - 1));
            if (this.type == JACKHAMMER) defaultUse /=2;
            return defaultUse;
        }

        @Override
        public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
            if (i == 1){
                if (this.type == JACKHAMMER) return -1;
                if (energyTier == 1) return GTCoreMaterials.Aluminium.getRGB();
                if (energyTier == 2) return GTCoreMaterials.StainlessSteel.getRGB();
                if (energyTier == 3) return GTCoreMaterials.Titanium.getRGB();
            }
            return super.getItemColor(stack, block, i);
        }
    }

    public static class PocketMultitoolTool extends MaterialTool{

        public PocketMultitoolTool(String domain, AntimatterToolType type, AntimatterItemTier tier, Properties properties) {
            super(domain, type, tier, properties);
        }

        @Override
        public void onGenericAddInformation(ItemStack stack, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Utils.translatable("tooltip.gtcore.pocket_multitool"));
            super.onGenericAddInformation(stack, tooltip, flag);
            tooltip.add(Utils.translatable("tooltip.gtcore.pocket_multitool.switch_mode"));
        }

        @Override
        public int getItemColor(ItemStack stack, @Nullable Block block, int i) {
            if (i == 1) return -1;
            return super.getItemColor(stack, block, i);
        }
    }

    public static void init(Side side){
        CHAINSAW.addBehaviour(BehaviourTreeFelling.INSTANCE, BehaviourLogStripping.INSTANCE);
        DRILL.addBehaviour(new BehaviourAOEBreak(1, 1, 1, 8, "3x3"), BehaviourTorchPlacing.INSTANCE);
        JACKHAMMER.addBehaviour(new BehaviourAOEBreak(1, 1, 1, 8, "3x3"), BehaviourTorchPlacing.INSTANCE);
        ELECTRIC_WRENCH.addBehaviour(BehaviourElectricWrenchSwitching.INSTANCE);
        ELECTRIC_WRENCH_ALT.addBehaviour(BehaviourElectricWrenchSwitching.INSTANCE);
        POCKET_MULTITOOL.addBehaviour(BehaviourMultitoolSwitching.INSTANCE);
        POCKET_MULTITOOL_KNIFE.addBehaviour(BehaviourMultitoolSwitching.INSTANCE);
        POCKET_MULTITOOL_SAW.addBehaviour(BehaviourMultitoolSwitching.INSTANCE);
        POCKET_MULTITOOL_FILE.addBehaviour(BehaviourMultitoolSwitching.INSTANCE);
        POCKET_MULTITOOL_SCREWDRIVER.addBehaviour(BehaviourMultitoolSwitching.INSTANCE);
        POCKET_MULTITOOL_WIRE_CUTTER.addBehaviour(BehaviourMultitoolSwitching.INSTANCE);
        POCKET_MULTITOOL_SCISSORS.addBehaviour(BehaviourMultitoolSwitching.INSTANCE);
        POCKET_MULTITOOL_SCISSORS.addBehaviour(BehaviourShearing.INSTANCE);
        GTCoreTools.DRILL.setBrokenItems(ImmutableMap.of("drill_lv", i -> getBrokenItem(i, GTCoreItems.PowerUnitLV), "drill_mv", i -> getBrokenItem(i, GTCoreItems.PowerUnitMV), "drill_hv", i -> getBrokenItem(i, GTCoreItems.PowerUnitHV)));
        GTCoreTools.CHAINSAW.setBrokenItems(ImmutableMap.of("chainsaw_lv", i -> getBrokenItem(i, GTCoreItems.PowerUnitLV), "chainsaw_mv", i -> getBrokenItem(i, GTCoreItems.PowerUnitMV), "chainsaw_hv", i -> getBrokenItem(i, GTCoreItems.PowerUnitHV)));
        GTCoreTools.ELECTRIC_WRENCH.setBrokenItems(ImmutableMap.of("electric_wrench_lv", i -> getBrokenItem(i, GTCoreItems.PowerUnitLV), "electric_wrench_mv", i -> getBrokenItem(i, GTCoreItems.PowerUnitMV), "electric_wrench_hv", i -> getBrokenItem(i, GTCoreItems.PowerUnitHV)));
        GTCoreTools.ELECTRIC_WRENCH_ALT.setBrokenItems(ImmutableMap.of("electric_wrench_alt_lv", i -> getBrokenItem(i, GTCoreItems.PowerUnitLV), "electric_wrench_alt_mv", i -> getBrokenItem(i, GTCoreItems.PowerUnitMV), "electric_wrench_alt_hv", i -> getBrokenItem(i, GTCoreItems.PowerUnitHV)));
        GTCoreTools.BUZZSAW.setBrokenItems(ImmutableMap.of("buzzsaw_lv", i -> getBrokenItem(i, GTCoreItems.PowerUnitLV), "buzzsaw_mv", i -> getBrokenItem(i, GTCoreItems.PowerUnitMV), "buzzsaw_hv", i -> getBrokenItem(i, GTCoreItems.PowerUnitHV)));
        GTCoreTools.ELECTRIC_SCREWDRIVER.setBrokenItems(ImmutableMap.of("electric_screwdriver_lv", i -> getBrokenItem(i, GTCoreItems.SmallPowerUnit)));
        GTCoreTools.JACKHAMMER.setBrokenItems(ImmutableMap.of("jackhammer_hv", i -> getBrokenItem(i, GTCoreItems.JackhammerPowerUnit)));
        KNIFE.addBehaviour(BehaviourKnifeTooltip.INSTANCE);
        if (side.isClient()) clientInit();
    }

    private static void clientInit() {
        ELECTRIC_SCREWDRIVER.addBehaviour(new BehaviourExtendedHighlight(b -> b instanceof BlockMachine || b instanceof BlockPipe, BehaviourExtendedHighlight.COVER_FUNCTION));
        ELECTRIC_WRENCH.addBehaviour(new BehaviourExtendedHighlight(b -> b instanceof BlockMachine || (b instanceof BlockPipe && b.builtInRegistryHolder().is(AntimatterDefaultTools.WRENCH.getToolType())) || b.defaultBlockState().hasProperty(BlockStateProperties.FACING_HOPPER) || b.defaultBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING), BehaviourExtendedHighlight.PIPE_FUNCTION));
        POCKET_MULTITOOL_SCREWDRIVER.addBehaviour(new BehaviourExtendedHighlight(b -> b instanceof BlockMachine || b instanceof BlockPipe, BehaviourExtendedHighlight.COVER_FUNCTION));
        POCKET_MULTITOOL_WIRE_CUTTER.addBehaviour(new BehaviourExtendedHighlight(b -> b instanceof BlockPipe && b.builtInRegistryHolder().is(AntimatterDefaultTools.WIRE_CUTTER.getToolType()), BehaviourExtendedHighlight.PIPE_FUNCTION));
    }

    private static ItemStack getBrokenItem(ItemStack tool, ItemLike broken){
        ItemStack powerUnit = new ItemStack(broken);
        Tuple<Long, Long> tuple = getEnergy(tool);
        CompoundTag dataTag = powerUnit.getOrCreateTagElement(muramasa.antimatter.Ref.TAG_ITEM_ENERGY_DATA);
        IEnergyHandlerItem handler = TesseractCapUtils.INSTANCE.getEnergyHandlerItem(powerUnit).orElse(null);
        if (handler != null){
            handler.setEnergy(tuple.getA());
            handler.setCapacity(tuple.getB());
            powerUnit = handler.getContainer().getItemStack();
        } else {
            dataTag.putLong(muramasa.antimatter.Ref.KEY_ITEM_ENERGY, tuple.getA());
            dataTag.putLong(muramasa.antimatter.Ref.KEY_ITEM_MAX_ENERGY, tuple.getB());
        }
        if (broken.asItem() == GTCoreItems.SmallPowerUnit){
            GTCoreItems.PowerUnitHV.setMaterial(((IAntimatterTool)tool.getItem()).getSecondaryMaterial(tool), powerUnit);
        }
        return powerUnit;
    }

    public static Tuple<Long, Long> getEnergy(ItemStack stack){
        if (stack.getItem() instanceof ItemBattery battery){
            long energy = TesseractCapUtils.INSTANCE.getEnergyHandlerItem(stack).map(IGTNode::getEnergy).orElse((long)0);
            long maxEnergy = TesseractCapUtils.INSTANCE.getEnergyHandlerItem(stack).map(IGTNode::getCapacity).orElse(battery.getCapacity());
            return new Tuple<>(energy, maxEnergy);
        }
        if (stack.getItem() instanceof IAntimatterTool tool){
            if (tool.getAntimatterToolType().isPowered()){
                long currentEnergy = tool.getCurrentEnergy(stack);
                long maxEnergy = tool.getMaxEnergy(stack);
                return new Tuple<>(currentEnergy, maxEnergy);
            }
        }
        return null;
    }

}
