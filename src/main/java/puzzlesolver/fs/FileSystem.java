package puzzlesolver.fs;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
@UtilityClass
public class FileSystem {
    public static final File WORKING_FOLDER = new File(System.getProperty("user.dir"));
    public static final File DEFAULT_PUZZLES_FOLDER = WORKING_FOLDER.toPath().resolve("puzzles").toFile();
    public static final File CONFIG_FILE = WORKING_FOLDER.toPath().resolve("config.toml").toFile();

    // If DEFAULT_PUZZLES_FOLDER does not exist, create it and log it
    static {
        final boolean created;
        if (!DEFAULT_PUZZLES_FOLDER.exists())
            created = DEFAULT_PUZZLES_FOLDER.mkdir();
        else
            created = false;
        if (created)
            log.info("Created default puzzle folder at {}", DEFAULT_PUZZLES_FOLDER);
    }
}
