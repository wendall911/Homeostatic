package homeostatic.common.item;

public class LeatherFlask extends WaterContainerItem {

    public static final long LEATHER_FLASK_CAPACITY = 1000L;
    public static final int LEATHER_FLASK_USES = 20;

    public LeatherFlask(Properties properties) {
        super(properties, (int) LEATHER_FLASK_CAPACITY, LEATHER_FLASK_USES);
    }

}
