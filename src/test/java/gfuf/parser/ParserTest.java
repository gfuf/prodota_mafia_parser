package gfuf.parser;

import org.junit.jupiter.api.Test;
import gfuf.prodota.data.Section;
import gfuf.prodota.data.Topic;
import gfuf.prodota.data.content.SectionContent;
import gfuf.prodota.parser.Parser;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static gfuf.utils.TestUtils.resourcePath;

public class ParserTest
{

    private Parser parser = new Parser();

    @Test
    public void testByMainPage() throws IOException
    {

        Set<Section> testSections = Set.of(new Section("Dota 2 Inside", URI.create("https://prodota.ru/forum/63/")),
                new Section("TI and Majors", URI.create("https://prodota.ru/forum/127/")),
                new Section("Стримы", URI.create("https://prodota.ru/forum/65/")),
                new Section("Профессиональные ивенты", URI.create("https://prodota.ru/forum/70/")),
                new Section("Полигон", URI.create("https://prodota.ru/forum/91/")),
                new Section("Вакансии", URI.create("https://prodota.ru/forum/67/")),
                new Section("Таверна", URI.create("https://prodota.ru/forum/16/")),
                new Section("Анонимная таверна", URI.create("https://prodota.ru/forum/129/")),
                new Section("Мастерская", URI.create("https://prodota.ru/forum/15/")),
                new Section("Архив", URI.create("https://prodota.ru/forum/95/")));

        SectionContent testContent = SectionContent.of(testSections, Set.of(), Optional.empty());
        String pageStr = Files.readString(resourcePath("prodota_forum_main.html"));
        SectionContent content = parser.parse(pageStr);

        assertEquals(content, testContent);
    }

    @Test
    public void testByMafiaPage() throws IOException
    {

        Set<Section> testSections = Set.of(new Section("Архив", URI.create("https://prodota.ru/forum/71/")));
        Set<Topic> testTopics = Set.of(new Topic("Регистрация№445", URI.create("https://prodota.ru/forum/topic/219579/?do=getNewComment")),
                new Topic("Мафиозная флудилка т.18, Покорение статы в новом десятилетии", URI.create("https://prodota.ru/forum/topic/217907/?do=getNewComment")),
                new Topic("Мафия 444. Трудно быть демоном. Ночи в 21-00", URI.create("https://prodota.ru/forum/topic/219541/?do=getNewComment")),
                new Topic("Стата версия 3.0 (черная)", URI.create("https://prodota.ru/forum/topic/217329/?do=getNewComment")),
                new Topic("рейд за новичками (рега)", URI.create("https://prodota.ru/forum/topic/218642/?do=getNewComment")),
                new Topic("Мафия 443: Туссент, Эрвелюс, Кровосиси", URI.create("https://prodota.ru/forum/topic/219239/?do=getNewComment")),
                new Topic("Мафия 442: Вас посетила Эрафия Police, впредь больше без матвинов фуллконфой", URI.create("https://prodota.ru/forum/topic/219101/?do=getNewComment")));

        SectionContent testContent = SectionContent.of(testSections, testTopics, Optional.of("https://prodota.ru/forum/45/page/2/"));
        String pageStr = Files.readString(resourcePath("prodota_forum_mafia.html"));
        SectionContent content = parser.parse(pageStr);

        assertEquals(content, testContent);
    }
}
