package puzzlesolver.fs.modules;

import javafx.beans.property.BooleanProperty;
import tools.jackson.databind.module.SimpleModule;

public class BooleanPropertyModule extends SimpleModule {
    public BooleanPropertyModule() {
        super();
        addDeserializer(BooleanProperty.class, new BooleanPropertyDeserializer());
        addSerializer(BooleanProperty.class, new BooleanPropertySerializer());
    }
}
