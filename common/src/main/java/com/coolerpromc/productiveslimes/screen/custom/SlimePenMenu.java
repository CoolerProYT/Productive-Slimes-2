package com.coolerpromc.productiveslimes.screen.custom;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimePenBlockEntity;
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

public class SlimePenMenu extends AbstractContainerMenu {
    public static final int IMAGE_HEIGHT = 166;
    public static final int JAR_SLOT_X = 38;
    public static final int JAR_SLOT_Y = 26;
    public static final int FOOD_SLOT_X = 38;
    public static final int FOOD_SLOT_Y = 44;
    public static final int UPGRADE_SLOT_X = 8;
    public static final int UPGRADE_SLOT_Y = 26;
    public static final int OUTPUT_SLOT_X = 98;
    public static final int OUTPUT_SLOT_Y = 17;
    public static final int INVENTORY_Y = 84;

    public static final int JAR_SLOT = 0;
    public static final int FOOD_SLOT = 1;
    public static final int UPGRADE_SLOT_START = 2;
    public static final int UPGRADE_SLOT_END = UPGRADE_SLOT_START + SlimePenBlockEntity.UPGRADE_SLOT_COUNT;
    private static final int INV_SLOT_START = UPGRADE_SLOT_END + SlimePenBlockEntity.OUTPUT_SLOT_COUNT;
    private static final int INV_SLOT_END = INV_SLOT_START + 27;
    private static final int HOTBAR_SLOT_END = INV_SLOT_END + 9;

    private final ContainerData data;
    private final ContainerLevelAccess access;

    public SlimePenMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        this(containerId, playerInventory, new SimpleContainer(1), new SimpleContainer(1), new SimpleContainer(SlimePenBlockEntity.UPGRADE_SLOT_COUNT), new SimpleContainer(SlimePenBlockEntity.OUTPUT_SLOT_COUNT), new SimpleContainerData(SlimePenBlockEntity.DATA_COUNT), ContainerLevelAccess.NULL);
    }

    public SlimePenMenu(int containerId, Inventory playerInventory, Container slime, Container food, Container upgrades, Container output, ContainerData data, ContainerLevelAccess access) {
        super(ModMenuTypes.SLIME_PEN.get(), containerId);
        checkContainerSize(slime, 1);
        checkContainerSize(food, 1);
        checkContainerSize(upgrades, SlimePenBlockEntity.UPGRADE_SLOT_COUNT);
        checkContainerSize(output, SlimePenBlockEntity.OUTPUT_SLOT_COUNT);
        checkContainerDataCount(data, SlimePenBlockEntity.DATA_COUNT);
        this.data = data;
        this.access = access;

        this.addSlot(new FilteredSlot(slime, 0, JAR_SLOT_X, JAR_SLOT_Y, 1));
        this.addSlot(new FilteredSlot(food, 0, FOOD_SLOT_X, FOOD_SLOT_Y, 64));

        for (int i = 0; i < SlimePenBlockEntity.UPGRADE_SLOT_COUNT; i++) {
            this.addSlot(new FilteredSlot(upgrades, i, UPGRADE_SLOT_X, UPGRADE_SLOT_Y + i * 18, 1));
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new OutputSlot(output, col + row * 3, OUTPUT_SLOT_X + col * 18, OUTPUT_SLOT_Y + row * 18));
            }
        }

        MenuUtil.addPlayerInventory(this::addSlot, playerInventory, INVENTORY_Y);
        this.addDataSlots(data);
    }

    public float getProgress() {
        int timer = this.data.get(SlimePenBlockEntity.DATA_TIMER);
        int duration = this.data.get(SlimePenBlockEntity.DATA_DURATION);
        return duration <= 0 ? 0.0F : Math.min(1.0F, timer / (float) duration);
    }

    public boolean isFed() {
        return this.getFedBatches() > 0;
    }

    public int getFedBatches() {
        return this.data.get(SlimePenBlockEntity.DATA_FED);
    }

    public int getStatus() {
        return this.data.get(SlimePenBlockEntity.DATA_STATUS);
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
            } else if (this.slots.get(JAR_SLOT).mayPlace(original) && this.moveItemStackTo(original, JAR_SLOT, JAR_SLOT + 1, false)) {
                // Moved into the jar slot.
            } else if (this.slots.get(FOOD_SLOT).mayPlace(original) && this.moveItemStackTo(original, FOOD_SLOT, FOOD_SLOT + 1, false)) {
                // Moved into the food slot.
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
        return stillValid(this.access, player, ModBlocks.SLIME_PEN.get());
    }
}
