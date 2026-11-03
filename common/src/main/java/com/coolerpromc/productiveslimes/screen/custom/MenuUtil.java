package com.coolerpromc.productiveslimes.screen.custom;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.function.Consumer;

public final class MenuUtil {
    private MenuUtil() {
    }

    public static void addPlayerInventory(Consumer<Slot> addSlot, Inventory playerInventory, int inventoryY) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                addSlot.accept(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, inventoryY + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            addSlot.accept(new Slot(playerInventory, col, 8 + col * 18, inventoryY + 58));
        }
    }
}
