package homeostatic.common.fluid;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.TagManager;
import homeostatic.common.item.HomeostaticItems;

public abstract class PurifiedWater extends FlowingFluid {

    @Override
    public Fluid getFlowing() {
        return HomeostaticFluids.PURIFIED_WATER_FLOWING;
    }

    @Override
    public Fluid getSource() {
        return HomeostaticFluids.PURIFIED_WATER;
    }

    @Override
    public Item getBucket() {
        return HomeostaticItems.PURIFIED_WATER_BUCKET;
    }

    @Override
    protected boolean canConvertToSource(Level level) {
        return false;
    }

    @Override
    public BlockState createLegacyBlock(FluidState fluidState) {
        return HomeostaticBlocks.PURIFIED_WATER_FLUID.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == HomeostaticFluids.PURIFIED_WATER || fluid == HomeostaticFluids.PURIFIED_WATER_FLOWING;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(TagManager.Fluids.PURIFIED_WATER);
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {
        BlockEntity $$3 = $$2.hasBlockEntity() ? $$0.getBlockEntity($$1) : null;
        Block.dropResources($$2, $$0, $$1, $$3);
    }

    @Override
    public int getSlopeFindDistance(LevelReader $$0) {
        return 4;
    }

    @Override
    public int getDropOff(LevelReader $$0) {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader $$0) {
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    public static class Flowing extends PurifiedWater {

        public Flowing() {}

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> $$0) {
            super.createFluidStateDefinition($$0);
            $$0.add(LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

    }

    public static class Source extends PurifiedWater {

        public Source() {}

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

    }

}
