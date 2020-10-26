package utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils
{
    public static final Path resourcePath(String resourceName)
    {
        return Paths.get("src", "test", "resources", resourceName);
    }
}
