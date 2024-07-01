package homeostatic.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import homeostatic.network.Thermometer;
import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.network.ThermometerData;

public class ComponentThermometerData extends Thermometer implements Component, AutoSyncedComponent {

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.read(tag);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        this.write(tag);
    }

    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
        ThermometerData thermometerData = new ThermometerData(new ThermometerInfo(hasThermometer()));

        thermometerData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        ThermometerData thermometerData = new ThermometerData(buf);

        this.setHasThermometer(thermometerData.hasThermometer);
    }

}
