package parser;

import org.junit.jupiter.api.Test;
import prodota.data.Section;
import prodota.data.content.SectionContent;
import prodota.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

public class ParserTest {

    private Parser parser = new Parser();

    @Test
    public void testByMainPage() throws IOException {

        SectionContent testContent = mock(SectionContent.class);

        String pageStr = Files.readString(Paths.get("src","test","resources","prodota_forum_main.html"));
        SectionContent content = parser.parse(pageStr);

        //TODO check equals
    }
}
