package puzzlesolver.fs.modules;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class BooleanPropertyDeserializer extends ValueDeserializer<BooleanProperty> {

    @Override
    public BooleanProperty deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        return new SimpleBooleanProperty(p.getBooleanValue());
    }
}
