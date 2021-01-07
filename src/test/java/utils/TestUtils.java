package utils;



import gfuf.prodota.data.topic.MafiaTopic;
import gfuf.prodota.data.topic.Topic;
import gfuf.prodota.data.topic.TopicStatus;
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

    public static Topic createTopic(String name, String url, TopicStatus status)
    {
        return Topic.builder()
                .setName(name)
                .setUri(URI.create(url))
                .setStatus(status).build();
    }

    public static MafiaTopic createMafiaTopic(String name, String url)
    {
        return MafiaTopic.builder()
                .setName(name)
                .setUri(URI.create(url)).build();
    }

}
