package homeostatic.common.components;

import org.jspecify.annotations.NonNull;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import homeostatic.network.Wetness;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.network.WetnessData;

public class ComponentWetnessData extends Wetness implements Component, AutoSyncedComponent {

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
        WetnessData wetnessData = new WetnessData(new WetnessInfo(getWetnessLevel(), getMoistureLevel()));

        wetnessData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(@NonNull RegistryFriendlyByteBuf buf) {
        WetnessData wetnessData = new WetnessData(buf);

        setWetnessData(wetnessData.getWetnessInfo());
    }

}
