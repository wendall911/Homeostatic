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

import static homeostatic.common.attachments.TemperatureData.TEMPERATURE_DATA_INSTANCE;
import static homeostatic.common.attachments.ThermometerData.THERMOMETER_DATA_INSTANCE;
import static homeostatic.common.attachments.WaterData.WATER_DATA_INSTANCE;
import static homeostatic.common.attachments.WetnessData.WETNESS_DATA_INSTANCE;

public class AttachmentsRegistry {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_DEFERRED_REGISTER =
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Homeostatic.MODID);

    public static final Supplier<AttachmentType<? extends Temperature>> TEMPERATURE_DATA =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
            "temperature_data_provider",
            () -> AttachmentType.serializable(() -> TEMPERATURE_DATA_INSTANCE).build()
        );
    public static final Supplier<AttachmentType<? extends Thermometer>> THERMOMETER_DATA =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
            "thermometer_data_provider",
            () -> AttachmentType.serializable(() -> THERMOMETER_DATA_INSTANCE).build()
        );
    public static final Supplier<AttachmentType<? extends Water>> WATER_DATA =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
            "water_data_provider",
            () -> AttachmentType.serializable(() -> WATER_DATA_INSTANCE).build()
        );
    public static final Supplier<AttachmentType<? extends Wetness>> WETNESS_DATA =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
            "wetness_data_provider",
            () -> AttachmentType.serializable(() -> WETNESS_DATA_INSTANCE).build()
        );

    public static void init(IEventBus bus) {
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(bus);
    }

}