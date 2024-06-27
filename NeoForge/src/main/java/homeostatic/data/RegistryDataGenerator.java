package homeostatic.data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;

import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DataPackRegistriesHooks;

import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.Homeostatic;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, HomeostaticDamageTypes::bootstrap);

    private RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of("minecraft", Homeostatic.MODID));
    }

    public static void addProviders(boolean isServer, DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        gen.addProvider(isServer, new RegistryDataGenerator(output, provider));
        gen.addProvider(isServer, new HomeostaticDamageTypeTagsProvider(output, provider.thenApply(r -> append(r, BUILDER)), helper));
    }

    @SuppressWarnings("UnstableApiUsage")
    private static HolderLookup.Provider append(HolderLookup.Provider original, RegistrySetBuilder builder) {
        HashSet<? extends ResourceKey<? extends Registry<?>>> builderKeys = new HashSet<>(builder.getEntryKeys());
        Cloner.Factory factory = new Cloner.Factory();

        DataPackRegistriesHooks.getDataPackRegistriesWithDimensions()
                .filter(data -> !builderKeys.contains(data.key()))
                .forEach(data -> builder.add(data.key(), context -> {}));
        DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(factory::addCodec));

        return builder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original, factory).full();
    }

}
