package homeostatic.common.block;

import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;

public class PurifiedWaterBlock extends LiquidBlock implements BucketPickup {

    protected PurifiedWaterBlock(FlowingFluid fluid, Properties props) {
        super(fluid, props);
    }

}
