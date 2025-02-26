package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.LootTableModule;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class GiftLootGoal extends Goal {
    private static final double MAX_ALLOWED_SQR_DISTANCE_TO_TARGET = 100.0;

    private final SandSnapperEntity sandSnapper;

    @Nullable
    private final PathNavigation pathNav;

    /**
     * The position of the block that the snapper will dig for a gift at.
     * This is the block that the snapper will be standing on when digging.
     */
    private Vec3 targetPos;

    /**
     * Duration in ticks that the snapper will dig for a gift for.
     */
    private final int diggingDuration;

    /**
     * Timer that counts down the duration of the digging process.
     */
    private int diggingTimer;

    /**
     * The range in the x and z directions that the snapper will search for a block to dig in.
     */
    private final int xzRange;

    /**
     * The range in the y direction that the snapper will search for a block to dig in.
     */
    private final int yRange;

    private boolean isDone;

    public GiftLootGoal(SandSnapperEntity sandSnapper, int xzRange, int yRange, int diggingDuration) {
        this.sandSnapper = sandSnapper;
        this.pathNav = sandSnapper.getNavigation();
        this.diggingDuration = diggingDuration;
        this.xzRange = xzRange;
        this.yRange = yRange;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.sandSnapper.searchingForGift || this.isDone) return false;
        this.targetPos = this.findTargetPos();
        return this.targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.isDone) return false;

        // At any time, if the target block is no longer valid, stop digging
        if (!isBlockAValidTarget(BlockPos.containing(this.targetPos).below())) {
            this.isDone = true;
            return false;
        }

        // Must be digging or within range of target
        return diggingTimer > 0 || this.sandSnapper.distanceToSqr(this.targetPos) < MAX_ALLOWED_SQR_DISTANCE_TO_TARGET;
    }

    @Override
    public void start() {
        this.isDone = false;
    }

    @Override
    public void stop() {
        this.sandSnapper.setForceSpawnDigParticles(false);
        this.diggingTimer = 0;
        this.sandSnapper.searchingForGift = false;
        this.isDone = false;
    }

    @Override
    public void tick() {
        if (isDone) return;

        if (diggingTimer > 0) { // diggingTimer is counting down
            diggingTimer--;
            if (diggingTimer == 0) {
                // Spawn loot
                LootTable lootTable = this.sandSnapper.getServer().getLootData().getLootTable(LootTableModule.SAND_SNAPPER_LOOT);
                LootParams lootParams = new LootParams.Builder((ServerLevel) this.sandSnapper.level())
                        .withParameter(LootContextParams.ORIGIN, this.sandSnapper.position())
                        .withParameter(LootContextParams.THIS_ENTITY, this.sandSnapper)
                        .create(LootContextParamSets.GIFT);
                ObjectArrayList<ItemStack> lootChoices = lootTable.getRandomItems(lootParams, new Random().nextLong());
                ItemStack loot = lootChoices.isEmpty() ? ItemStack.EMPTY : lootChoices.get(0);
                spawnLoot(loot);
                this.isDone = true;
            } else if (diggingTimer == diggingDuration / 2) {
                // Start spawning dig particles halfway through digging
                this.sandSnapper.setForceSpawnDigParticles(true);
            }
        } else { // We haven't started digging yet
            double sqrDist = this.sandSnapper.position().distanceToSqr(this.targetPos);
            if (!this.sandSnapper.blockPosition().equals(BlockPos.containing(this.targetPos))) { // We're not on the target block yet
                if (sqrDist > 4.0 && this.pathNav != null) {
                    this.pathNav.moveTo(targetPos.x(), targetPos.y(), targetPos.z(), 1.0);
                } else {
                    this.sandSnapper.getMoveControl().setWantedPosition(this.targetPos.x(), this.targetPos.y(), this.targetPos.z(), 1.0);
                }
            } else { // We're on the target block - stop moving and start digging
                this.diggingTimer = diggingDuration;
                if (this.pathNav != null) {
                    this.pathNav.stop();
                }
                if (!this.sandSnapper.isSubmerged() && !this.sandSnapper.isDiggingDown() && !this.sandSnapper.isDiving() && this.sandSnapper.canSubmerge(true)) {
                    this.sandSnapper.setSubmerged(true);
                }
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Nullable
    private Vec3 findTargetPos() {
        return BlockPos.findClosestMatch(this.sandSnapper.getOnPos(), this.xzRange, this.yRange, this::isBlockAValidTarget)
                .map(BlockPos::above)
                .map(pos -> new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5))
                .orElse(null);
    }

    private boolean isBlockAValidTarget(BlockPos blockPos) {
        BlockState blockState = this.sandSnapper.level().getBlockState(blockPos);
        return blockState.is(BlockModule.SAND_SNAPPER_BLOCKS)
                && this.sandSnapper.level().getBlockState(blockPos.above()).isAir();
    }

    private void spawnLoot(ItemStack loot) {
        if (!loot.isEmpty()) {
            BlockPos spawnPos = this.sandSnapper.blockPosition();
            double x = (double) spawnPos.getX() + 0.5;
            double y = (double) spawnPos.getY() + 0.5 - (EntityType.ITEM.getHeight() / 2.0F);
            double z = (double) spawnPos.getZ() + 0.5;

            RandomSource random = this.sandSnapper.getRandom();

            double xOffset = Mth.nextDouble(random, 0, 0.2);
            double yOffset = 0.2;
            double zOffset = Mth.nextDouble(random, 0, 0.2);

            double dx = Mth.nextDouble(random, 0, 0.05);
            double dy = 0.2;
            double dz = Mth.nextDouble(random, 0, 0.05);

            if (random.nextBoolean()) {
                xOffset *= -1;
                dx *= -1;
            }
            if (random.nextBoolean()) {
                zOffset *= -1;
                dz *= -1;
            }

            x += xOffset;
            y += yOffset;
            z += zOffset;

            ItemEntity itemEntity = new ItemEntity(this.sandSnapper.level(), x, y, z, loot, dx, dy, dz);
            itemEntity.setDefaultPickUpDelay();
            this.sandSnapper.level().addFreshEntity(itemEntity);
        }
    }
}
