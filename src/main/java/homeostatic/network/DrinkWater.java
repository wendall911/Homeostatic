package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;

public class DrinkWater implements IData {

    public DrinkWater() {}

    public DrinkWater(FriendlyByteBuf buf) {}

    @Override
    public void toBytes(FriendlyByteBuf buf) {}

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();
        player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            data.increaseWaterLevel();
            data.increaseSaturationLevel();

            if (Homeostatic.RANDOM.nextFloat() < ConfigHandler.Server.effectChance()) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        ConfigHandler.Server.effectDuration(),
                        ConfigHandler.Server.effectPotency(),
                        false, true, true));
            }
        });
    }

}
