package puzzlesolver;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import puzzlesolver.fs.modules.BooleanPropertyModule;
import tools.jackson.dataformat.toml.TomlFactory;
import tools.jackson.dataformat.toml.TomlMapper;

import java.io.IOException;

import static puzzlesolver.fs.FileSystem.CONFIG_FILE;


@Value
@Accessors(fluent = true)
@Builder(toBuilder = true, access = AccessLevel.PACKAGE)
@Jacksonized
@Slf4j
public class Config {
    private static final InvalidationListener AUTO_SAVE = (e) -> save();
    private static final TomlMapper MAPPER = new TomlMapper.Builder(new TomlFactory()).addModule(
            new BooleanPropertyModule()
    ).build();
    private static final Config INSTANCE = createInstance();

    @JsonProperty("reasoning")
    BooleanProperty reasoningProperty;
    @JsonProperty("backtracking")
    BooleanProperty backtrackingProperty;

    public void setReasoning(boolean r) {
        this.reasoningProperty.set(r);
    }

    public boolean getReasoning() {
        return reasoningProperty.get();
    }

    public void setBacktracking(boolean b) {
        this.backtrackingProperty.set(b);
    }

    public boolean getBacktracking() {
        return backtrackingProperty.get();
    }

    private Config() {
        this(true, true);
    }

    private Config(boolean r, boolean b) {
        this(new SimpleBooleanProperty(r), new SimpleBooleanProperty(b));
    }

    private Config(BooleanProperty r, BooleanProperty b) {
        this.reasoningProperty = r;
        this.backtrackingProperty = b;
        this.reasoningProperty.addListener(AUTO_SAVE);
        this.backtrackingProperty.addListener(AUTO_SAVE);
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    private static void save() {
        MAPPER.writeValue(CONFIG_FILE.toPath(), getInstance());
    }

    private static Config createInstance() {
        Config config;
        try {
            if (CONFIG_FILE.createNewFile()) {
                config = builder().build();
                MAPPER.writeValue(CONFIG_FILE, config);
            }
            else {
                config = MAPPER.readValue(CONFIG_FILE, Config.class);
            }
        }
        catch (IOException e) {
            log.error("Could not create config file");
            throw new RuntimeException();
        }
        return config;
    }

    private static ConfigBuilder builder() {
        return (new Config()).toBuilder();
    }
}
