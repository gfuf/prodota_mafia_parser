package utils;



import gfuf.prodota.data.MafiaTopic;
import gfuf.prodota.data.Topic;
import org.apache.commons.lang3.ArrayUtils;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils
{

    private static final String[] BEGIN_PATH = {"test", "resources"};

    public static final Path resourcePath(String... resourceName)
    {
        return Paths.get("src", ArrayUtils.addAll(BEGIN_PATH, resourceName));
    }

    public static Topic createTopic(String name, String url)
    {
        return Topic.builder().setName(name)
                .setUri(URI.create(url)).build();
    }

    public static MafiaTopic createMafiaTopic(String name, String url)
    {
        return MafiaTopic.builder().setName(name)
                .setUri(URI.create(url)).build();
    }

}
