package puzzlesolver;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.dataformat.toml.TomlMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static puzzlesolver.fs.FileSystem.CONFIG_FILE;


@Data
@Builder(toBuilder = true)
@Jacksonized
@Slf4j
public class Config implements Observable {
    private static final InvalidationListener AUTO_SAVE = (e) -> save((Config) e);
    private static final TomlMapper MAPPER = new TomlMapper();
    private static final Config INSTANCE = createInstance();

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    final List<InvalidationListener> listeners = new ArrayList<>();

    boolean reasoning;
    boolean backtracking;

    private void ping() {
        for (InvalidationListener l : listeners) {
            l.invalidated(this);
        }
    }

    public void setReasoning(boolean r) {
        if (this.reasoning ^ r)
            ping();
        this.reasoning = r;
    }

    public void setBacktracking(boolean b) {
        if (this.backtracking ^ b)
            ping();
        this.backtracking = b;
    }

    private Config() {
        this(true, true);
    }

    private Config(boolean r, boolean b) {
        this.reasoning = r;
        this.backtracking = b;
        this.addListener(AUTO_SAVE);
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    private static void save(Config config) {
        MAPPER.writeValue(CONFIG_FILE.toPath(), config);
    }

    private static Config createInstance() {
        Config config;
        log.warn(MAPPER.getClass().getCanonicalName());
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

    @Override
    public void addListener(InvalidationListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(InvalidationListener l) {
        listeners.remove(l);
    }
}
