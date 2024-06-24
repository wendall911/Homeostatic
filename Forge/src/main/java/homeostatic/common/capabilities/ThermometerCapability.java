package homeostatic.common.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;


public class ThermometerCapability {

    public static LazyOptional<IThermometer> getCapability(final Player player) {
        return player.getCapability(CapabilityRegistry.THERMOMETER_CAPABILITY);
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @NotNull
        private final IThermometer instance;

        private final LazyOptional<IThermometer> handler;

        public Provider() {
            instance = new Thermometer();
            handler = LazyOptional.of(this::getInstance);
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return CapabilityRegistry.THERMOMETER_CAPABILITY.orEmpty(cap, handler);
        }

        public IThermometer getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return instance.write();
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            instance.read((CompoundTag) nbt);
        }

    }

}