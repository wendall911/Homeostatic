package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import homeostatic.Homeostatic;
import homeostatic.common.damagesource.HomeostaticDamageTypes;

public class FabricDamageTypeProvider extends FabricDynamicRegistryProvider {

    public FabricDamageTypeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.@NonNull Provider provider, @NonNull Entries entries) {
        HomeostaticDamageTypes.ALL.forEach((type) -> add(provider, entries, type));
    }

    private void add(HolderLookup.Provider provider, Entries entries, ResourceKey<DamageType> key) {
        entries.add(key, provider.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key).value());
    }

    @Override
    public @NonNull String getName() {
        return Homeostatic.MOD_NAME + " Damage Types";
    }

}
