package homeostatic.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class TConHelper {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public static double getBlockRadiation(BlockState state, Double radiation) {
        if (state.hasProperty(ACTIVE) && state.getValue(ACTIVE)) {
            return radiation;
        }

        return 0D;
    }

}
