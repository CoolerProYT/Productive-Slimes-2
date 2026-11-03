package com.coolerpromc.productiveslimes.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;

import java.util.Collections;

public final class ContainerUtil {
    private ContainerUtil() {
    }

    public static boolean canFit(SimpleContainer container, ItemStack stack) {
        int remaining = stack.getCount();
        for (ItemStack existing : container.getItems()) {
            if (existing.isEmpty()) {
                remaining -= Math.min(stack.getMaxStackSize(), container.getMaxStackSize());
            } else if (ItemStack.isSameItemSameComponents(existing, stack)) {
                remaining -= Math.min(existing.getMaxStackSize(), container.getMaxStackSize()) - existing.getCount();
            }
            if (remaining <= 0) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack insert(SimpleContainer container, ItemStack stack) {
        NonNullList<ItemStack> items = container.getItems();
        for (int slot = 0; slot < items.size() && !stack.isEmpty(); slot++) {
            ItemStack existing = items.get(slot);
            if (!existing.isEmpty() && ItemStack.isSameItemSameComponents(existing, stack)) {
                int moved = Math.min(stack.getCount(), Math.min(existing.getMaxStackSize(), container.getMaxStackSize()) - existing.getCount());
                if (moved > 0) {
                    existing.grow(moved);
                    stack.shrink(moved);
                }
            }
        }
        for (int slot = 0; slot < items.size() && !stack.isEmpty(); slot++) {
            if (items.get(slot).isEmpty()) {
                items.set(slot, stack.split(Math.min(stack.getMaxStackSize(), container.getMaxStackSize())));
            }
        }
        container.setChanged();
        return stack;
    }

    public static void loadItems(ValueInput input, NonNullList<ItemStack> items) {
        Collections.fill(items, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
    }
}
