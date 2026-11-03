package com.coolerpromc.productiveslimes.block.custom;

import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SlimyGrassBlock extends Block implements BonemealableBlock {
    public SlimyGrassBlock(Properties properties) {
        super(properties);
    }

    private static boolean canStayGrass(LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        return !aboveState.isFaceSturdy(level, above, Direction.DOWN) && aboveState.getFluidState().isEmpty();
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canStayGrass(level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.SLIMY_DIRT.get().defaultBlockState());
            return;
        }

        if (level.getMaxLocalRawBrightness(pos.above()) < 9) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            BlockPos target = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            if (level.getBlockState(target).is(ModBlocks.SLIMY_DIRT.get()) && canStayGrass(level, target)) {
                level.setBlockAndUpdate(target, this.defaultBlockState());
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockState sprouts = ModBlocks.SLIME_SPROUTS.get().defaultBlockState();
        for (int i = 0; i < 24; i++) {
            BlockPos target = pos.offset(random.nextInt(5) - 2, 1, random.nextInt(5) - 2);
            if (level.getBlockState(target).isAir() && sprouts.canSurvive(level, target)) {
                level.setBlockAndUpdate(target, sprouts);
            }
        }
    }
}
