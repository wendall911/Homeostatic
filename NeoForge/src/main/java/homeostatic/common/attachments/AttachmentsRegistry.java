package homeostatic.common.attachments;

import java.util.function.Supplier;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import homeostatic.Homeostatic;
import homeostatic.network.Temperature;
import homeostatic.network.Thermometer;
import homeostatic.network.Water;
import homeostatic.network.Wetness;

public class AttachmentsRegistry {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_DEFERRED_REGISTER =
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Homeostatic.MODID);

    public static final Supplier<AttachmentType<? extends Temperature>> TEMPERATURE_DATA =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
            "temperature_data_provider",
            () -> AttachmentType.serializable(TemperatureData.TemperatureDataProvider::new).build()
        );
    public static final Supplier<AttachmentType<? extends Thermometer>> THERMOMETER_DATA =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
            "thermometer_data_provider",
            () -> AttachmentType.serializable(ThermometerData.ThermometerDataProvider::new).build()
        );
    public static final Supplier<AttachmentType<? extends Water>> WATER_DATA =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
            "water_data_provider",
            () -> AttachmentType.serializable(WaterData.WaterDataProvider::new).build()
        );
    public static final Supplier<AttachmentType<? extends Wetness>> WETNESS_DATA =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
            "wetness_data_provider",
            () -> AttachmentType.serializable(WetnessData.WetnessDataProvider::new).build()
        );

    public static void init(IEventBus bus) {
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(bus);
    }

}