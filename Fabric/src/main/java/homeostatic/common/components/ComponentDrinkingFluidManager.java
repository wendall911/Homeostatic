package homeostatic.common.components;

import org.jetbrains.annotations.NotNull;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import homeostatic.common.fluid.DrinkingFluidManager;

public class ComponentDrinkingFluidManager extends DrinkingFluidManager implements Component, AutoSyncedComponent {

    @Override
    public void readFromNbt(@NotNull CompoundTag tag) {
        ListTag nbt = tag.getList("DrinkingFluids", NbtType.COMPOUND);

        DrinkingFluidManager.read(nbt);
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag) {
        tag.put("DrinkingFluids", DrinkingFluidManager.write());
    }

}
