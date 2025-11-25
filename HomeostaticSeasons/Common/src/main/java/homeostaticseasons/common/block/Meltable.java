package homeostaticseasons.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import homeostaticseasons.common.TagManager;
import homeostaticseasons.event.SnowAndIceEventHandler;

public interface Meltable {

    default void onMeltableReplaced(ServerLevel level, BlockPos pos) {
        // Flush any saved data about this meltable block being manually placed or replaced
        SnowAndIceEventHandler.getPlacedMeltablesSavedData(level).setManuallyPlaced(pos, false);
        SnowAndIceEventHandler.getReplacedMeltablesSavedData(level).setReplaced(pos, null);
    }

    default void onMeltableManuallyPlaced(ServerLevel level, BlockPos pos) {
        SnowAndIceEventHandler.getPlacedMeltablesSavedData(level).setManuallyPlaced(pos, true);
    }

    static void replaceBlockOnSnow(ServerLevel level, BlockPos pos) {
        BlockState plantBlockState = level.getBlockState(pos);

        if (plantBlockState.is(TagManager.Blocks.REPLACEABLE_BY_SNOW)) {
            if (pos.getY() >= level.getMinBuildHeight()
                    && pos.getY() < level.getMaxBuildHeight() - 1
                    && level.getBrightness(LightLayer.BLOCK, pos) < 10) {
                BlockState upperBlockState = level.getBlockState(pos.above());

                if (plantBlockState.getProperties().contains(DoublePlantBlock.HALF)
                        && upperBlockState.getProperties().contains(DoublePlantBlock.HALF)) {
                    if (upperBlockState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
                        SnowAndIceEventHandler.cacheMeltableBlock(pos);
                        SnowAndIceEventHandler.getReplacedMeltablesSavedData(level).setReplaced(pos, plantBlockState);
                        level.setBlock(pos, Blocks.SNOW.defaultBlockState(), Block.UPDATE_KNOWN_SHAPE);
                        level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
                        Blocks.SNOW.defaultBlockState().updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
                        level.sendBlockUpdated(pos, plantBlockState, Blocks.SNOW.defaultBlockState(), Block.UPDATE_ALL);
                    }
                }
                else if (upperBlockState.isAir()) {
                    SnowAndIceEventHandler.cacheMeltableBlock(pos);
                    SnowAndIceEventHandler.getReplacedMeltablesSavedData(level).setReplaced(pos, plantBlockState);
                    level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState());
                }
            }
        }
    }

}
