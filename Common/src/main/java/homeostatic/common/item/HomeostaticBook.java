package homeostatic.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import homeostatic.data.integration.ModIntegration;
import homeostatic.platform.Services;

import static homeostatic.Homeostatic.loc;

public class HomeostaticBook extends Item {

    ResourceLocation book;

    public HomeostaticBook(Properties pProperties, String bookId) {
        super(pProperties);

        this.book = loc(bookId);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        if (Services.PLATFORM.isModLoaded(ModIntegration.PATCHOULI_MODID)) {
            if (level.isClientSide()) {
                // TODO add back once Patchoili has an update
                //vazkii.patchouli.api.PatchouliAPI.get().openBookGUI(book);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
    }

}
