package utils;



import org.apache.commons.lang3.ArrayUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils
{

    private static final String[] BEGIN_PATH = {"test", "resources"};

    public static final Path resourcePath(String... resourceName)
    {
        return Paths.get("src", ArrayUtils.addAll(BEGIN_PATH, resourceName));
    }
}
