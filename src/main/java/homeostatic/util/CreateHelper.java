package homeostatic.util;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;

import net.minecraft.world.level.block.state.BlockState;

public class CreateHelper {

    public static double getBlockRadiation(BlockState state, Double radiation) {
        Double level = 0D;

        if (state.hasProperty(BlazeBurnerBlock.HEAT_LEVEL)) {
            BlazeBurnerBlock.HeatLevel heatLevel = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);

            level = switch (heatLevel) {
                case NONE:
                default:
                    yield 0D;
                case FADING:
                    yield 0.25D * radiation;
                case SMOULDERING:
                    yield 0.5D * radiation;
                case KINDLED:
                    yield 0.75D * radiation;
                case SEETHING:
                    yield radiation;
            };
        }

        return level;
    }

}
