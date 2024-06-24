package homeostatic.common.item;

public class LeatherFlask extends WaterContainerItem {

    public static final long LEATHER_FLASK_CAPACITY = 1000L;

    public LeatherFlask(Properties properties) {
        super(properties, (int) LEATHER_FLASK_CAPACITY);
    }

}
