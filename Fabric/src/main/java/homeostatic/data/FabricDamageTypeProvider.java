package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import homeostatic.Homeostatic;
import homeostatic.common.damagesource.HomeostaticDamageTypes;

public class FabricDamageTypeProvider extends FabricDynamicRegistryProvider {

    public FabricDamageTypeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        HomeostaticDamageTypes.ALL.forEach((type) -> add(provider, entries, type));
    }

    private void add(HolderLookup.Provider provider, Entries entries, ResourceKey<DamageType> key) {
        entries.add(key, provider.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key).value());
    }

    @Override
    public @NotNull String getName() {
        return Homeostatic.MOD_NAME + " Damage Types";
    }

}
