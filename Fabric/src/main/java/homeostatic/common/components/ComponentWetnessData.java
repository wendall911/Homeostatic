package homeostatic.common.components;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import homeostatic.network.Wetness;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.network.WetnessData;

public class ComponentWetnessData extends Wetness implements Component, AutoSyncedComponent {

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
        WetnessData wetnessData = new WetnessData(new WetnessInfo(getWetnessLevel(), getMoistureLevel()));

        wetnessData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        WetnessData wetnessData = new WetnessData(buf);

        setWetnessData(wetnessData.getWetnessInfo());
    }

}
