package com.coolerpromc.productiveslimes.screen.custom;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimeSqueezerBlockEntity;
import com.coolerpromc.productiveslimes.screen.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlimeSqueezerMenu extends AbstractContainerMenu {
    public static final int IMAGE_HEIGHT = 166;
    public static final int INPUT_SLOT_X = 44;
    public static final int INPUT_SLOT_Y = 35;
    public static final int OUTPUT_SLOT_X = 107;
    public static final int OUTPUT_SLOT_Y = 35;
    public static final int UPGRADE_SLOT_X = 8;
    public static final int UPGRADE_SLOT_Y = 26;
    public static final int INVENTORY_Y = 84;

    public static final int INPUT_SLOT = 0;
    public static final int UPGRADE_SLOT_START = 1 + SlimeSqueezerBlockEntity.OUTPUT_SLOT_COUNT;
    public static final int UPGRADE_SLOT_END = UPGRADE_SLOT_START + SlimeSqueezerBlockEntity.UPGRADE_SLOT_COUNT;
    private static final int INV_SLOT_START = UPGRADE_SLOT_END;
    private static final int INV_SLOT_END = INV_SLOT_START + 27;
    private static final int HOTBAR_SLOT_END = INV_SLOT_END + 9;

    private final ContainerData data;
    private final ContainerLevelAccess access;

    public SlimeSqueezerMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        this(containerId, playerInventory, new SimpleContainer(1), new SimpleContainer(SlimeSqueezerBlockEntity.UPGRADE_SLOT_COUNT), new SimpleContainer(SlimeSqueezerBlockEntity.OUTPUT_SLOT_COUNT), new SimpleContainerData(SlimeSqueezerBlockEntity.DATA_COUNT), ContainerLevelAccess.NULL);
    }

    public SlimeSqueezerMenu(int containerId, Inventory playerInventory, Container input, Container upgrades, Container output, ContainerData data, ContainerLevelAccess access) {
        super(ModMenuTypes.SLIME_SQUEEZER.get(), containerId);
        checkContainerSize(input, 1);
        checkContainerSize(upgrades, SlimeSqueezerBlockEntity.UPGRADE_SLOT_COUNT);
        checkContainerSize(output, SlimeSqueezerBlockEntity.OUTPUT_SLOT_COUNT);
        checkContainerDataCount(data, SlimeSqueezerBlockEntity.DATA_COUNT);
        this.data = data;
        this.access = access;

        this.addSlot(new FilteredSlot(input, 0, INPUT_SLOT_X, INPUT_SLOT_Y, 64));
        for (int i = 0; i < SlimeSqueezerBlockEntity.OUTPUT_SLOT_COUNT; i++) {
            this.addSlot(new OutputSlot(output, i, OUTPUT_SLOT_X + i * 18, OUTPUT_SLOT_Y));
        }
        for (int i = 0; i < SlimeSqueezerBlockEntity.UPGRADE_SLOT_COUNT; i++) {
            this.addSlot(new FilteredSlot(upgrades, i, UPGRADE_SLOT_X, UPGRADE_SLOT_Y + i * 18, 1));
        }

        MenuUtil.addPlayerInventory(this::addSlot, playerInventory, INVENTORY_Y);
        this.addDataSlots(data);
    }

    public float getProgress() {
        int duration = this.data.get(SlimeSqueezerBlockEntity.DATA_DURATION);
        return duration <= 0 ? 0.0F : Math.min(1.0F, this.data.get(SlimeSqueezerBlockEntity.DATA_PROGRESS) / (float) duration);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            result = original.copy();

            if (index < INV_SLOT_START) {
                if (!this.moveItemStackTo(original, INV_SLOT_START, HOTBAR_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.slots.get(INPUT_SLOT).mayPlace(original) && this.moveItemStackTo(original, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                // Moved into the input slot.
            } else if (this.slots.get(UPGRADE_SLOT_START).mayPlace(original) && this.moveItemStackTo(original, UPGRADE_SLOT_START, UPGRADE_SLOT_END, false)) {
                // Moved into an upgrade slot.
            } else if (index < INV_SLOT_END) {
                if (!this.moveItemStackTo(original, INV_SLOT_END, HOTBAR_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(original, INV_SLOT_START, INV_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (original.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (original.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, result);
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.SLIME_SQUEEZER.get());
    }
}
