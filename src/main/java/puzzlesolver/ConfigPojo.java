package puzzlesolver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import puzzlesolver.fs.FileSystem;
import tools.jackson.dataformat.toml.TomlMapper;

import java.io.File;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class ConfigPojo {
    private static final TomlMapper MAPPER = new TomlMapper();
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private static final String DEFAULT_PUZZLES_FOLDER = FileSystem.DEFAULT_PUZZLES_FOLDER.toString();

    @Builder.Default
    boolean backtracking = true;
    @Builder.Default
    boolean reasoning = true;
    @Builder.Default
    int windowHeight = DEFAULT_HEIGHT;
    @Builder.Default
    int windowWidth = DEFAULT_WIDTH;
    @Builder.Default
    boolean maximized = false;
    @Builder.Default
    String lastFolder = DEFAULT_PUZZLES_FOLDER;

    public static ConfigPojo loadFromFile(File file) {
        return MAPPER.readValue(file, ConfigPojo.class);
    }

    public void saveToFile(File file) {
        MAPPER.writeValue(file, this);
    }
}
