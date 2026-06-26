package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import homeostatic.common.damagesource.HomeostaticDamageTypes;

public class FabricDamageTypeTagsProvider extends FabricTagsProvider<DamageType> {

    public FabricDamageTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Registries.DAMAGE_TYPE, provider);
    }

    protected @NonNull TagAppender<DamageType> tag(@NonNull TagKey<DamageType> key) {
        return TagAppender.forBuilder(getOrCreateRawBuilder(key));
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(HomeostaticDamageTypes.HYPERTHERMIA, HomeostaticDamageTypes.SCALDING, HomeostaticDamageTypes.DEHYDRATION);
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(HomeostaticDamageTypes.HYPERTHERMIA, HomeostaticDamageTypes.SCALDING, HomeostaticDamageTypes.DEHYDRATION);
        this.tag(DamageTypeTags.NO_KNOCKBACK).add(HomeostaticDamageTypes.HYPERTHERMIA, HomeostaticDamageTypes.SCALDING, HomeostaticDamageTypes.DEHYDRATION);
    }

}
