package homeostatic.util;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.LitBlazeBurnerBlock;

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
        else if (state.hasProperty(LitBlazeBurnerBlock.FLAME_TYPE)) {
            LitBlazeBurnerBlock.FlameType flameType = state.getValue(LitBlazeBurnerBlock.FLAME_TYPE);

            level = switch(flameType) {
                default:
                    yield 0D;
                case REGULAR:
                    yield 0.67D * radiation;
                case SOUL:
                    yield radiation;
            };
        }

        return level;
    }

}
