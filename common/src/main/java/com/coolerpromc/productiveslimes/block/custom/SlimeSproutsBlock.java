package com.coolerpromc.productiveslimes.block.custom;

import com.mojang.serialization.MapCodec;
import com.coolerpromc.productiveslimes.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlimeSproutsBlock extends VegetationBlock {
    public static final MapCodec<SlimeSproutsBlock> CODEC = simpleCodec(SlimeSproutsBlock::new);

    private static final VoxelShape SHAPE = Block.column(12.0, 0.0, 10.0);

    public SlimeSproutsBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.SLIMY_GRASS_BLOCK.get()) || state.is(ModBlocks.SLIMY_DIRT.get()) || super.mayPlaceOn(state, level, pos);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }
}
