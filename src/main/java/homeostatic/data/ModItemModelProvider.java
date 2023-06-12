package homeostatic.data;

import java.util.Objects;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.Homeostatic;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Homeostatic.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Homoestatic - Item Models";
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        build(itemGenerated, HomeostaticItems.LEATHER_FLASK);
        build(itemGenerated, HomeostaticItems.PURIFIED_WATER_BUCKET);
        build(itemGenerated, HomeostaticItems.WATER_FILTER);
        build(itemGenerated, HomeostaticItems.BOOK);
        build(itemGenerated, HomeostaticItems.THERMOMETER);
    }

    private void build(ModelFile itemGenerated, Item item) {
        String name = Objects.requireNonNull(item.toString());

        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

}
