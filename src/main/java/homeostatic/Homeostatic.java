package homeostatic;

import java.util.Random;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import homeostatic.proxy.ClientProxy;
import homeostatic.proxy.CommonProxy;

@Mod(Homeostatic.MODID)
public class Homeostatic {

    public static final String MODID = "homeostatic";

    public static final Logger LOGGER = LogManager.getFormatterLogger(MODID);
    public static final Random RANDOM = new Random();
    public static CommonProxy PROXY;
    public static boolean DATA_GEN = System.getenv("DATA_GEN") != null && System.getenv("DATA_GEN").contains("all");

    public Homeostatic() {
        PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        PROXY.start();
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MODID, path);
    }

}