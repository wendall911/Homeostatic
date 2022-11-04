package homeostatic.data;

import java.util.Objects;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;

import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.Homeostatic;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Homeostatic.MODID, existingFileHelper);
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
    }

    private void build(ModelFile itemGenerated, Item item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();

        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

}
