package site.golets.pomanager.utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Paths;

@UtilityClass
public class PathUtils {

    public boolean exists(String path) {
        File root = Paths.get(path).toFile();
        return root.exists();
    }

    public void existsFailFast(String path) {
        if (!exists(path)) {
            throw new IllegalStateException(String.format("Path [%s] doesn't exist.", path));
        }
    }

}
