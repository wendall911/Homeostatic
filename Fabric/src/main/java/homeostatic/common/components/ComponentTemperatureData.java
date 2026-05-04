package homeostatic.common.components;

import org.jspecify.annotations.NonNull;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import homeostatic.network.Temperature;
import homeostatic.network.TemperatureData;

public class ComponentTemperatureData extends Temperature implements Component, AutoSyncedComponent {

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
        TemperatureData temperatureData = new TemperatureData(
            getLocalTemperature(),
            getSkinTemperature(),
            getCoreTemperature(),
            getRelativeHumidity()
        );

        temperatureData.write(buf);
    }

    @Override
    public void applySyncPacket(@NonNull RegistryFriendlyByteBuf buf) {
        TemperatureData temperatureData = new TemperatureData(buf);

        this.setLocalTemperature(temperatureData.localTemperature);
        this.setSkinTemperature(temperatureData.skinTemperature);
        this.setCoreTemperature(temperatureData.coreTemperature);
        this.setRelativeHumidity(temperatureData.relativeHumidity);
    }

}
