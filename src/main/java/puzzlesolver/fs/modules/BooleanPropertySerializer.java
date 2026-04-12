package puzzlesolver.fs.modules;

import javafx.beans.property.BooleanProperty;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class BooleanPropertySerializer extends ValueSerializer<BooleanProperty> {

    @Override
    public void serialize(BooleanProperty value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeBoolean(value.get());
    }
}
