package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import homeostatic.common.damagesource.HomeostaticDamageTypes;

public class FabricDamageTypeTagsProvider extends FabricTagProvider<DamageType> {

    public FabricDamageTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Registries.DAMAGE_TYPE, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(HomeostaticDamageTypes.HYPERTHERMIA, HomeostaticDamageTypes.SCALDING, HomeostaticDamageTypes.DEHYDRATION);
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(HomeostaticDamageTypes.HYPERTHERMIA, HomeostaticDamageTypes.SCALDING, HomeostaticDamageTypes.DEHYDRATION);
        this.tag(DamageTypeTags.NO_KNOCKBACK).add(HomeostaticDamageTypes.HYPERTHERMIA, HomeostaticDamageTypes.SCALDING, HomeostaticDamageTypes.DEHYDRATION);
    }

}
