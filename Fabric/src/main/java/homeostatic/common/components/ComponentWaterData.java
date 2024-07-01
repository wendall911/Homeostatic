package homeostatic.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import homeostatic.network.Water;
import homeostatic.common.water.WaterInfo;
import homeostatic.network.WaterData;

public class ComponentWaterData extends Water implements Component, AutoSyncedComponent {

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
        WaterData waterData = new WaterData(new WaterInfo(getWaterLevel(), getWaterSaturationLevel(), getWaterExhaustionLevel()));

        waterData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        WaterData waterData = new WaterData(buf);

        setWaterData(waterData.getWaterInfo());
    }

}
