package puzzlesolver.fs;

import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class FileSystem {
    public final File WORKING_FOLDER = new File(System.getProperty("user.dir"));
    public final File DEFAULT_PUZZLES_FOLDER = WORKING_FOLDER.toPath().resolve("puzzles").toFile();
    public final File CONFIG_FILE = WORKING_FOLDER.toPath().resolve("config.toml").toFile();
}
