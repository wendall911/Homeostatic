package homeostatic.common.components;

import org.jetbrains.annotations.NotNull;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import homeostatic.network.Temperature;
import homeostatic.network.TemperatureData;

public class ComponentTemperatureData extends Temperature implements Component, AutoSyncedComponent {

    @Override
    public void readFromNbt(@NotNull CompoundTag tag) {
        this.read(tag);
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag) {
        this.write(tag);
    }

    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer sp) {
        TemperatureData temperatureData = new TemperatureData(getLocalTemperature(), getSkinTemperature(), getCoreTemperature());

        temperatureData.write(buf);
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        TemperatureData temperatureData = new TemperatureData(buf);

        this.setLocalTemperature(temperatureData.localTemperature);
        this.setSkinTemperature(temperatureData.skinTemperature);
        this.setCoreTemperature(temperatureData.coreTemperature);
    }

}
