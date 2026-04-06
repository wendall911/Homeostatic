package homeostatic.common.components;

import org.jspecify.annotations.NonNull;

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
    public void readData(@NonNull ValueInput valueInput) {
        this.read(valueInput);
    }

    @Override
    public void writeData(@NonNull ValueOutput valueOutput) {
        this.write(valueOutput);
    }

    @Override
    public void writeSyncPacket(@NonNull RegistryFriendlyByteBuf buf, @NonNull ServerPlayer sp) {
        ThermometerData thermometerData = new ThermometerData(new ThermometerInfo(hasThermometer()));

        thermometerData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(@NonNull RegistryFriendlyByteBuf buf) {
        ThermometerData thermometerData = new ThermometerData(buf);

        this.setHasThermometer(thermometerData.hasThermometer);
    }

}
