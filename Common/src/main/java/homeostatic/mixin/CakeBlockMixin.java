package homeostatic.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import homeostatic.util.WaterHelper;

@Mixin(CakeBlock.class)
public abstract class CakeBlockMixin {

    @Inject(method = "eat",
            at = @At("HEAD"))
    private static void injectEat(LevelAccessor pLevel, BlockPos pPos, BlockState pState, Player pPlayer, CallbackInfoReturnable<Biome> cir) {
        if (!pLevel.isClientSide() && pPlayer.canEat(false)) {
            WaterHelper.drink((ServerPlayer) pPlayer, new ItemStack(Items.CAKE), null);
        }
    }

}
