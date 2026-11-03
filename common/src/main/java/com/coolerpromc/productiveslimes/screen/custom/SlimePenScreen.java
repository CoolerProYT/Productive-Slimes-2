package com.coolerpromc.productiveslimes.screen.custom;

import com.coolerpromc.productiveslimes.Constants;
import com.coolerpromc.productiveslimes.block.entity.custom.SlimePenBlockEntity;
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

public class SlimePenScreen extends AbstractContainerScreen<SlimePenMenu> {
    public static final Identifier TEXTURE = Constants.id("textures/gui/container/slime_pen.png");
    public static final int ARROW_X = 64;
    public static final int ARROW_Y = 35;
    private static final int ARROW_U = 176;
    private static final int ARROW_V = 0;
    public static final int ARROW_WIDTH = 22;
    public static final int ARROW_HEIGHT = 16;
    private static final int FED_X = 75;
    private static final int FED_Y = 55;
    private static final int STATUS_RIGHT = 168;
    private static final int WARNING_COLOR = 0xFFB02E26;
    private static final int FED_COLOR = 0xFF2E8B1E;

    public SlimePenScreen(SlimePenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, SlimePenMenu.IMAGE_HEIGHT);
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
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);
        Component warning = switch (this.menu.getStatus()) {
            case SlimePenBlockEntity.STATUS_EMPTY -> Component.translatable("gui.productiveslimes.slime_pen.empty");
            case SlimePenBlockEntity.STATUS_FULL -> Component.translatable("gui.productiveslimes.slime_pen.full");
            default -> null;
        };
        if (warning != null) {
            graphics.text(this.font, warning, STATUS_RIGHT - this.font.width(warning), this.titleLabelY, WARNING_COLOR, false);
        }

        if (this.menu.isFed()) {
            Component fed = Component.translatable("gui.productiveslimes.slime_pen.fed");
            graphics.text(this.font, fed, FED_X - this.font.width(fed) / 2, FED_Y, FED_COLOR, false);
        }
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);
        if (!this.menu.getCarried().isEmpty()) {
            return;
        }

        Slot slot = this.hoveredSlot;
        if (slot != null && !slot.hasItem()) {
            List<Component> lines = slotHint(slot);
            if (!lines.isEmpty()) {
                graphics.setComponentTooltipForNextFrame(this.font, lines, mouseX, mouseY);
            }
        } else if (this.menu.isFed() && this.isHovering(FED_X - 8, FED_Y - 1, 16, 10, mouseX, mouseY)) {
            graphics.setTooltipForNextFrame(this.font, Component.translatable("gui.productiveslimes.slime_pen.fed.tooltip", this.menu.getFedBatches()), mouseX, mouseY);
        }
    }

    private List<Component> slotHint(Slot slot) {
        String key;
        if (slot.index == SlimePenMenu.JAR_SLOT) {
            key = "jar";
        } else if (slot.index == SlimePenMenu.FOOD_SLOT) {
            key = "food";
        } else if (slot.index >= SlimePenMenu.UPGRADE_SLOT_START && slot.index < SlimePenMenu.UPGRADE_SLOT_END) {
            key = "upgrade";
        } else {
            return List.of();
        }
        String base = "gui.productiveslimes.slime_pen.slot." + key;
        return List.of(Component.translatable(base), Component.translatable(base + ".desc").withStyle(ChatFormatting.GRAY));
    }
}
