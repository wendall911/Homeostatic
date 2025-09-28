package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import homeostatic.common.damagesource.HomeostaticDamageTypeTags;

public class FabricDamageTypeTagsProvider extends FabricTagProvider<DamageType> {

    public FabricDamageTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Registries.DAMAGE_TYPE, provider);
    }

    // TODO: investigate why addTag doesn't work here. I have a registry error thrown when I try to use it.
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.BYPASSES_ARMOR)
            .addOptionalTag(HomeostaticDamageTypeTags.HYPERTHERMIA.location())
            .addOptionalTag(HomeostaticDamageTypeTags.SCALDING.location())
            .addOptionalTag(HomeostaticDamageTypeTags.DEHYDRATION.location());
        tag(DamageTypeTags.BYPASSES_INVULNERABILITY)
            .addOptionalTag(HomeostaticDamageTypeTags.HYPERTHERMIA.location())
            .addOptionalTag(HomeostaticDamageTypeTags.SCALDING.location())
            .addOptionalTag(HomeostaticDamageTypeTags.DEHYDRATION.location());

    }

}
