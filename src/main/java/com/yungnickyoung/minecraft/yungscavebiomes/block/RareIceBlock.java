package com.yungnickyoung.minecraft.yungscavebiomes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class RareIceBlock extends HalfTransparentBlock {
    public RareIceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack);
        int i = 15 + serverLevel.random.nextInt(15) + serverLevel.random.nextInt(15);
        this.popExperience(serverLevel, blockPos, i);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        double x = (double)blockPos.getX() + 0.5 + ((0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1));
        double y = (double)blockPos.getY() + 0.5 + ((0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1));
        double z = (double)blockPos.getZ() + 0.5 + ((0.5 + random.nextDouble() * 0.5) * (random.nextBoolean() ? 1 : -1));
        Vec3 dir = new Vec3(x, y, z).subtract(new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5)).normalize();
//            level.addParticle(ParticleTypes.GLOW, x, y, z, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005);
        level.addParticle(ParticleTypes.GLOW, x, y, z, 0, 0, 0);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return ItemStack.EMPTY;
    }
}
