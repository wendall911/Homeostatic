package homeostatic.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import homeostatic.network.Temperature;
import homeostatic.network.TemperatureData;

public class ComponentTemperatureData extends Temperature implements Component, AutoSyncedComponent {

    private final Player provider;

    public ComponentTemperatureData(Player player) {
        this.provider = player;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        ListTag listTag = new ListTag();

        listTag.add(tag.getCompound("TemperatureData"));
        this.read(listTag);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.put("TemperatureData", this.write());
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

    @Override
    public boolean shouldSyncWith(ServerPlayer sp) {
        return sp == provider;
    }

}
