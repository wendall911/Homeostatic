package homeostatic.common.components;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import homeostatic.network.Thermometer;
import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.network.ThermometerData;

public class ComponentThermometerData extends Thermometer implements Component, AutoSyncedComponent {

    @Override
    public void readData(@NotNull ValueInput valueInput) {
        this.read(valueInput);
    }

    @Override
    public void writeData(@NotNull ValueOutput valueOutput) {
        this.write(valueOutput);
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer sp) {
        ThermometerData thermometerData = new ThermometerData(new ThermometerInfo(hasThermometer()));

        thermometerData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        ThermometerData thermometerData = new ThermometerData(buf);

        this.setHasThermometer(thermometerData.hasThermometer);
    }

}
