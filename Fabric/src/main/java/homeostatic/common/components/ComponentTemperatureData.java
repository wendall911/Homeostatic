package homeostatic.common.components;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import homeostatic.network.Temperature;
import homeostatic.network.TemperatureData;

public class ComponentTemperatureData extends Temperature implements Component, AutoSyncedComponent {

    @Override
    public void readFromNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        this.read(tag);
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        this.write(tag);
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
