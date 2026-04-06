package homeostatic.common.components;

import org.jspecify.annotations.NonNull;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import homeostatic.network.Water;
import homeostatic.common.water.WaterInfo;
import homeostatic.network.WaterData;

public class ComponentWaterData extends Water implements Component, AutoSyncedComponent {

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
        WaterData waterData = new WaterData(new WaterInfo(getWaterLevel(), getWaterSaturationLevel(), getWaterExhaustionLevel()));

        waterData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(@NonNull RegistryFriendlyByteBuf buf) {
        WaterData waterData = new WaterData(buf);

        setWaterData(waterData.getWaterInfo());
    }

}
