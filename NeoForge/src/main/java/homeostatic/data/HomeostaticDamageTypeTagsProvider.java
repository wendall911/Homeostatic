package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;

import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.Homeostatic;

public class HomeostaticDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public HomeostaticDamageTypeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future) {
        super(packOutput, future, Homeostatic.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(HomeostaticDamageTypes.HYPERTHERMIA_KEY, HomeostaticDamageTypes.SCALDING_KEY, HomeostaticDamageTypes.DEHYDRATION_KEY);
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(HomeostaticDamageTypes.HYPERTHERMIA_KEY, HomeostaticDamageTypes.SCALDING_KEY, HomeostaticDamageTypes.DEHYDRATION_KEY);
        this.tag(DamageTypeTags.NO_KNOCKBACK).add(HomeostaticDamageTypes.HYPERTHERMIA_KEY, HomeostaticDamageTypes.SCALDING_KEY, HomeostaticDamageTypes.DEHYDRATION_KEY);
    }

}
