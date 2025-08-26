package homeostatic.common.components;

import org.jetbrains.annotations.NotNull;

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
    public void readData(@NotNull ValueInput valueInput) {
        this.read(valueInput);
    }

    @Override
    public void writeData(@NotNull ValueOutput valueOutput) {
        this.write(valueOutput);
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer sp) {
        WetnessData wetnessData = new WetnessData(new WetnessInfo(getWetnessLevel(), getMoistureLevel()));

        wetnessData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        WetnessData wetnessData = new WetnessData(buf);

        setWetnessData(wetnessData.getWetnessInfo());
    }

}
