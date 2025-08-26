package homeostatic.common.components;

import org.jetbrains.annotations.NotNull;

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
    public void readData(@NotNull ValueInput valueInput) {
        this.read(valueInput);
    }

    @Override
    public void writeData(@NotNull ValueOutput valueOutput) {
        this.write(valueOutput);
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer sp) {
        TemperatureData temperatureData = new TemperatureData(getLocalTemperature(), getSkinTemperature(), getCoreTemperature());

        temperatureData.write(buf);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        TemperatureData temperatureData = new TemperatureData(buf);

        this.setLocalTemperature(temperatureData.localTemperature);
        this.setSkinTemperature(temperatureData.skinTemperature);
        this.setCoreTemperature(temperatureData.coreTemperature);
    }

}
