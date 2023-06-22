package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.Homeostatic;

public class ModDamageTypeTagsProvider extends TagsProvider<DamageType> {

    public ModDamageTypeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(packOutput, Registries.DAMAGE_TYPE, future, Homeostatic.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(HomeostaticDamageTypes.HYPERTHERMIA_KEY, HomeostaticDamageTypes.SCALDING_KEY, HomeostaticDamageTypes.DEHYDRATION_KEY);
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(HomeostaticDamageTypes.HYPERTHERMIA_KEY, HomeostaticDamageTypes.SCALDING_KEY, HomeostaticDamageTypes.DEHYDRATION_KEY);
    }

}
