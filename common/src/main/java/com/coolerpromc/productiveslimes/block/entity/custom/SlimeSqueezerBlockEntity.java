package com.coolerpromc.productiveslimes.block.entity.custom;

import com.coolerpromc.productiveslimes.block.custom.SlimeSqueezerBlock;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import com.coolerpromc.productiveslimes.data.component.ModDataComponents;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.item.custom.SpeedUpgradeItem;
import com.coolerpromc.productiveslimes.screen.custom.SlimeSqueezerMenu;
import com.coolerpromc.productiveslimes.slime.SlimeType;
import com.coolerpromc.productiveslimes.util.ContainerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class SlimeSqueezerBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SQUEEZE_TIME = 80;
    public static final int OUTPUT_SLOT_COUNT = 2;
    public static final int UPGRADE_SLOT_COUNT = 2;

    public static final int DATA_PROGRESS = 0;
    public static final int DATA_DURATION = 1;
    public static final int DATA_COUNT = 2;

    private static final Component NAME = Component.translatable("container.productiveslimes.slime_squeezer");

    private final SimpleContainer input = new SqueezerContainer(1) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return isSqueezable(stack);
        }
    };

    private final SimpleContainer output = new SqueezerContainer(OUTPUT_SLOT_COUNT) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return false;
        }
    };

    private final SimpleContainer upgrades = new SqueezerContainer(UPGRADE_SLOT_COUNT) {
        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return stack.getItem() instanceof SpeedUpgradeItem;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    };

    private int progress;
    private int duration = SQUEEZE_TIME;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_PROGRESS -> SlimeSqueezerBlockEntity.this.progress;
                case DATA_DURATION -> SlimeSqueezerBlockEntity.this.duration;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case DATA_PROGRESS -> SlimeSqueezerBlockEntity.this.progress = value;
                case DATA_DURATION -> SlimeSqueezerBlockEntity.this.duration = value;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public SlimeSqueezerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SLIME_SQUEEZER.get(), pos, state);
    }

    public static boolean isSqueezable(ItemStack stack) {
        return stack.is(ModItems.SLIMEBALL.get()) && stack.has(ModDataComponents.SLIME_TYPE.get());
    }

    public static ItemStack resultFor(ItemStack stack) {
        Holder<SlimeType> type = stack.get(ModDataComponents.SLIME_TYPE.get());
        return type == null ? ItemStack.EMPTY : type.value().createSqueezeResult();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SlimeSqueezerBlockEntity squeezer) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack input = squeezer.input.getItem(0);
        ItemStack result = isSqueezable(input) ? resultFor(input) : ItemStack.EMPTY;
        boolean working = !result.isEmpty() && ContainerUtil.canFit(squeezer.output, result);

        if (state.getValue(SlimeSqueezerBlock.WORKING) != working) {
            level.setBlock(pos, state.setValue(SlimeSqueezerBlock.WORKING, working), Block.UPDATE_ALL);
        }

        if (!working) {
            if (squeezer.progress != 0) {
                squeezer.progress = 0;
                squeezer.setChanged();
            }
            return;
        }

        squeezer.duration = Math.max(1, Math.round(SQUEEZE_TIME * SpeedUpgradeItem.timeMultiplier(squeezer.upgrades)));
        if (++squeezer.progress < squeezer.duration) {
            if (squeezer.progress % 20 == 0) {
                serverLevel.playSound(null, pos, SoundEvents.SLIME_SQUISH_SMALL, SoundSource.BLOCKS, 0.4F, 0.7F);
            }
            return;
        }

        squeezer.progress = 0;
        input.shrink(1);
        ContainerUtil.insert(squeezer.output, result);
        serverLevel.playSound(null, pos, SoundEvents.HONEY_BLOCK_SLIDE, SoundSource.BLOCKS, 0.8F, 1.0F);
        squeezer.setChanged();
    }

    public Container getContainer(@Nullable Direction side) {
        return side == Direction.DOWN ? this.output : this.input;
    }

    public Container[] getContainers() {
        return new Container[]{this.input, this.upgrades, this.output};
    }

    public float getProgress() {
        return this.duration <= 0 ? 0.0F : Math.min(1.0F, this.progress / (float) this.duration);
    }

    public ItemStack getInput() {
        return this.input.getItem(0);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) {
            Containers.dropContents(this.level, pos, this.input);
            Containers.dropContents(this.level, pos, this.upgrades);
            Containers.dropContents(this.level, pos, this.output);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerUtil.loadItems(input.childOrEmpty("Input"), this.input.getItems());
        ContainerUtil.loadItems(input.childOrEmpty("Upgrades"), this.upgrades.getItems());
        ContainerUtil.loadItems(input.childOrEmpty("Output"), this.output.getItems());
        this.progress = input.getIntOr("Progress", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output.child("Input"), this.input.getItems());
        ContainerHelper.saveAllItems(output.child("Upgrades"), this.upgrades.getItems());
        ContainerHelper.saveAllItems(output.child("Output"), this.output.getItems());
        output.putInt("Progress", this.progress);
    }

    @Override
    public Component getDisplayName() {
        return NAME;
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new SlimeSqueezerMenu(containerId, inventory, this.input, this.upgrades, this.output, this.dataAccess, ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    private class SqueezerContainer extends SimpleContainer {
        SqueezerContainer(int size) {
            super(size);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            SlimeSqueezerBlockEntity.this.setChanged();
        }

        @Override
        public boolean stillValid(Player player) {
            return Container.stillValidBlockEntity(SlimeSqueezerBlockEntity.this, player);
        }
    }
}
