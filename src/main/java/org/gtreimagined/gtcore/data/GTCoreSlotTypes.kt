package org.gtreimagined.gtcore.data

import brachy.modularui.widgets.slot.ModularSlot
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.wrapper.EmptyHandler
import org.gtreimagined.gtcore.gui.slots.SlotBlueprint
import org.gtreimagined.gtcore.gui.slots.SlotCrafting
import org.gtreimagined.gtcore.gui.slots.SlotCraftingOutput
import org.gtreimagined.gtcore.gui.slots.SlotUnlimited
import org.gtreimagined.gtcore.mui.GTCoreGuiTextures
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine
import org.gtreimagined.gtlib.gui.SlotType
import org.gtreimagined.gtlib.gui.slot.AbstractSlot
import org.gtreimagined.gtlib.tool.IGTTool
import org.gtreimagined.tesseract.TesseractCapUtils
import org.gtreimagined.tesseract.api.eu.IEnergyHandler
import org.gtreimagined.tesseract.api.eu.IEnergyHandlerItem
import java.util.function.BiPredicate
import java.util.function.Function

object GTCoreSlotTypes {
    @JvmField
    val TOOLS: SlotType<AbstractSlot<*>> = SlotType.create { b ->
            b.id = "tools"
            b.slotSupplier = SlotType.ISlotSupplier { type, gui, item, i, _ ->
                AbstractSlot(type, gui,
                    item.getOrDefault(type, EmptyHandler.INSTANCE), i)
            }
            b.tester = BiPredicate { _, i ->
                i.item is IGTTool || i.item.canBeDepleted()
            }
        }

    @JvmField
    val TOOL_CHARGE: SlotType<AbstractSlot<*>> = SlotType.create { b ->
            b.id = "tool_charge"
            b.slotSupplier = SlotType.ISlotSupplier { type, gui, item, i, _ ->
                AbstractSlot(type, gui,
                    item.getOrDefault(type, EmptyHandler.INSTANCE), i)
            }
            b.tester = BiPredicate { t, i ->
                val electric = t is BlockEntity && TesseractCapUtils.INSTANCE.getEnergyHandler(t, null).map(
                    Function { eh: IEnergyHandler ->
                        TesseractCapUtils.INSTANCE.getEnergyHandlerItem(i)
                            .map(Function { inner: IEnergyHandlerItem? -> (inner!!.inputVoltage or inner.outputVoltage) <= (eh.inputVoltage or eh.outputVoltage) })
                            .orElse(false)!!
                    }).orElse(false)!!
                electric || i.item is IGTTool || i.item.canBeDepleted()
            }
            b.overlay = GTCoreGuiTextures.ELECTRIC_TOOL_SLOT_OVERLAY
        }

    @JvmField
    val CRAFTING: SlotType<SlotCrafting> = SlotType.create { b ->
            b.id = "crafting"
            b.slotSupplier = SlotType.ISlotSupplier { type, gui, item, i, _ ->
                    SlotCrafting(
                        type,
                        gui,
                        item.getOrDefault(type, EmptyHandler.INSTANCE),
                        i
                    )
                }
            b.allowExternalOutput = false
        }

    @JvmField
    val PARK: SlotType<AbstractSlot<*>> = SlotType.create { b ->
            b.id = "park"
            b.slotSupplier = SlotType.ISlotSupplier { type, gui, item, i, _ ->
                    AbstractSlot(type, gui,
                        item.getOrDefault(type, EmptyHandler.INSTANCE), i)
                }
            b.allowExternalInput = false
            b.allowExternalOutput(false)
            b.slotGroup(false)
            b.overlay(GTCoreGuiTextures.PARK_SLOT_OVERLAY)
        }

    @JvmField
    val BLUEPRINT: SlotType<SlotBlueprint> = SlotType.create { r ->
            r.id = "blueprint"
            r.slotSupplier = SlotType.ISlotSupplier { type, gui, item, i, _ ->
                    SlotBlueprint(type, gui,
                        item.getOrDefault(type, EmptyHandler.INSTANCE), i)
                }
            r.tester = BiPredicate { _, i -> i.item === GTCoreItems.Blueprint || i.item === GTCoreItems.EmptyBlueprint }
            r.allowExternalOutput = false
            r.overlay = GTCoreGuiTextures.BLUEPRINT_SLOT_OVERLAY
        }

    @JvmField
    val EXPORT: SlotType<AbstractSlot<*>> = SlotType.create { b ->
            b.id = "export"
            b.slotSupplier = SlotType.ISlotSupplier { type, gui, item, i, _ ->
                    AbstractSlot(type, gui,
                        item.getOrDefault(type, EmptyHandler.INSTANCE), i)
                }
            b.slotGroup = false
            b.overlay = GTCoreGuiTextures.EXPORT_SLOT_OVERLAY
        }

    @JvmField
    val CRAFTING_RESULT: SlotType<ModularSlot> = SlotType.create { b ->
            b.id = "crafting_result"
            b.slotSupplier = SlotType.ISlotSupplier { type, tile, slots, index, _ ->
                    if (tile is BlockEntityMachine<*>) {
                        return@ISlotSupplier SlotCraftingOutput(
                            tile.itemHandler.map(Function { m -> m }).orElse(null),
                            slots.getOrDefault(type, EmptyHandler.INSTANCE),
                            slots.getOrDefault(
                                CRAFTING, EmptyHandler.INSTANCE
                            ) as IItemHandlerModifiable,
                            index
                        )
                    }
                    ModularSlot(slots.getOrDefault(type, EmptyHandler.INSTANCE), index)
                }
            b.allowExternalInput = false
            b.overlay = GTCoreGuiTextures.CRAFTING_OUTPUT_SLOT_OVERLAY
        }

    @JvmField
    val UNLIMITED: SlotType<SlotUnlimited> = SlotType.create { b ->
            b.id = "unlimited"
            b.slotSupplier =
                SlotType.ISlotSupplier { type, gui, item, i, _ ->
                    SlotUnlimited(type, gui,
                        item.getOrDefault(type, EmptyHandler.INSTANCE), i)
                }
        }

    @JvmStatic
    fun init() {
    }
}
