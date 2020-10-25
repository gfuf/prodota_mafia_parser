import prodota.data.content.SectionContent;
import prodota.parser.Parser;
import web.RestTeamplate;
import web.impl.RestSimpleTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        RestTeamplate restTeamplate = new RestSimpleTemplate();
        String body = restTeamplate.doGet("https://prodota.ru/forum/16/");
        Parser parser = new Parser();
        SectionContent sectionContent = parser.parse(body);
        System.out.println(sectionContent);

    }
}
