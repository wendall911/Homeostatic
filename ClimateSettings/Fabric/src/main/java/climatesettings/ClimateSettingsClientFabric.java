package climatesettings;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import climatesettings.network.SyncBiomeTypeData;

public class ClimateSettingsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(
            SyncBiomeTypeData.TYPE,
            (SyncBiomeTypeData packet, ClientPlayNetworking.Context context) -> {
                context.client().execute(() -> {
                    packet.handle(context.player());
                });
            }
        );
    }

}
