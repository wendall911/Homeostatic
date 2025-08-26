package homeostatic.common.components;

import org.jetbrains.annotations.NotNull;

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
    public void readData(@NotNull ValueInput valueInput) {
        this.read(valueInput);
    }

    @Override
    public void writeData(@NotNull ValueOutput valueOutput) {
        this.write(valueOutput);
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer sp) {
        WaterData waterData = new WaterData(new WaterInfo(getWaterLevel(), getWaterSaturationLevel(), getWaterExhaustionLevel()));

        waterData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        WaterData waterData = new WaterData(buf);

        setWaterData(waterData.getWaterInfo());
    }

}
