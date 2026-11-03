package com.coolerpromc.productiveslimes.screen.custom;

import com.coolerpromc.productiveslimes.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class SlimeSqueezerScreen extends AbstractContainerScreen<SlimeSqueezerMenu> {
    public static final Identifier TEXTURE = Constants.id("textures/gui/container/slime_squeezer.png");
    public static final int ARROW_X = 72;
    public static final int ARROW_Y = 35;
    private static final int ARROW_U = 176;
    private static final int ARROW_V = 0;
    public static final int ARROW_WIDTH = 22;
    public static final int ARROW_HEIGHT = 16;

    public SlimeSqueezerScreen(SlimeSqueezerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, SlimeSqueezerMenu.IMAGE_HEIGHT);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int progress = Mth.ceil(this.menu.getProgress() * ARROW_WIDTH);
        if (progress > 0) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos + ARROW_X, this.topPos + ARROW_Y, ARROW_U, ARROW_V, progress, ARROW_HEIGHT, 256, 256);
        }
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);
        Slot slot = this.hoveredSlot;
        if (slot == null || slot.hasItem() || !this.menu.getCarried().isEmpty()) {
            return;
        }
        String key;
        if (slot.index == SlimeSqueezerMenu.INPUT_SLOT) {
            key = "input";
        } else if (slot.index >= SlimeSqueezerMenu.UPGRADE_SLOT_START && slot.index < SlimeSqueezerMenu.UPGRADE_SLOT_END) {
            key = "upgrade";
        } else {
            return;
        }
        String base = "gui.productiveslimes.slime_squeezer.slot." + key;
        graphics.setComponentTooltipForNextFrame(this.font, List.of(Component.translatable(base), Component.translatable(base + ".desc").withStyle(ChatFormatting.GRAY)), mouseX, mouseY);
    }
}
