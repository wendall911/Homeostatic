package homeostatic.common.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import homeostatic.network.Wetness;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.network.WetnessData;

public class ComponentWetnessData extends Wetness implements Component, AutoSyncedComponent {

    @Override
    public void readFromNbt(CompoundTag tag) {
        ListTag listTag = new ListTag();

        listTag.add(tag.getCompound("WetnessData"));
        this.read(listTag);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.put("WetnessData", this.write());
    }

    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
        WetnessData wetnessData = new WetnessData(new WetnessInfo(getWetnessLevel(), getMoistureLevel()));

        wetnessData.toBytes(buf);
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        WetnessData wetnessData = new WetnessData(buf);

        setWetnessData(wetnessData.getWetnessInfo());
    }

}
