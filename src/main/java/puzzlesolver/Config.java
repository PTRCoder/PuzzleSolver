package puzzlesolver;

import javafx.beans.property.*;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static puzzlesolver.fs.FileSystem.CONFIG_FILE;


@Value
@Accessors(fluent = true)
@Slf4j
public class Config {
    private static final Config INSTANCE = createInstance();

    BooleanProperty reasoningProperty;
    BooleanProperty backtrackingProperty;
    IntegerProperty windowWidthProperty;
    IntegerProperty windowHeightProperty;
    BooleanProperty maximizedProperty;
    Property<File> lastFolderProperty;

    private Config(ConfigPojo pojo) {
        this.reasoningProperty = new SimpleBooleanProperty(pojo.reasoning);
        this.backtrackingProperty = new SimpleBooleanProperty(pojo.backtracking);
        this.windowHeightProperty = new SimpleIntegerProperty(pojo.windowHeight);
        this.windowWidthProperty = new SimpleIntegerProperty(pojo.windowWidth);
        this.maximizedProperty = new SimpleBooleanProperty(pojo.maximized);
        this.lastFolderProperty = new SimpleObjectProperty<>(new File(pojo.lastFolder));
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public ConfigPojo toPojo() {
        return ConfigPojo.builder()
                .withBacktracking(backtrackingProperty().get())
                .withReasoning(reasoningProperty.get())
                .withWindowHeight(windowHeightProperty.get())
                .withWindowWidth(windowWidthProperty.get())
                .withMaximized(maximizedProperty.get())
                .withLastFolder(lastFolderProperty.getValue().toString()).build();
    }

    public static void saveInstance() {
        getInstance().toPojo().saveToFile(CONFIG_FILE);
    }

    private static Config createInstance() {
        ConfigPojo config;
        if (CONFIG_FILE.exists()) {
            config = ConfigPojo.loadFromFile(CONFIG_FILE);
        }
        else {
            config = new ConfigPojo();
        }
        return new Config(config);
    }
}
