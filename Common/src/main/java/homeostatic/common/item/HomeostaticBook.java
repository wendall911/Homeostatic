package homeostatic.common.item;

import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        if (Services.PLATFORM.isModLoaded(ModIntegration.PATCHOULI_MODID)) {
            if (level.isClientSide()) {
                // TODO: Add book back once Patchouli is updated to 1.21.8+
                //vazkii.patchouli.api.PatchouliAPI.get().openBookGUI(book);
            }
        }

        return InteractionResult.SUCCESS.heldItemTransformedTo(player.getItemInHand(hand));
    }

}
