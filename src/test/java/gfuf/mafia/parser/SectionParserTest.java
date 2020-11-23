package gfuf.mafia.parser;

import org.junit.jupiter.api.Test;
import gfuf.prodota.data.Section;
import gfuf.prodota.data.Topic;
import gfuf.prodota.data.content.SectionContent;
import gfuf.prodota.parser.section.SectionParser;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtils.resourcePath;

import static utils.TestUtils.*;

public class SectionParserTest
{

    private SectionParser sectionParser = new SectionParser();

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
        SectionContent content = sectionParser.parse(pageStr);

        assertEquals(content, testContent);
    }

    @Test
    public void testByMafiaPage() throws IOException
    {

        Set<Section> testSections = Set.of(new Section("Архив", URI.create("https://prodota.ru/forum/71/")));
        Set<Topic> testTopics = Set.of(createTopic("Регистрация№445", "https://prodota.ru/forum/topic/219579/?do=getNewComment"),
                createTopic("Мафиозная флудилка т.18, Покорение статы в новом десятилетии", "https://prodota.ru/forum/topic/217907/?do=getNewComment"),
                createTopic("Мафия 444. Трудно быть демоном. Ночи в 21-00", "https://prodota.ru/forum/topic/219541/?do=getNewComment"),
                createTopic("Стата версия 3.0 (черная)", "https://prodota.ru/forum/topic/217329/?do=getNewComment"),
                createTopic("рейд за новичками (рега)", "https://prodota.ru/forum/topic/218642/?do=getNewComment"),
                createTopic("Мафия 443: Туссент, Эрвелюс, Кровосиси", "https://prodota.ru/forum/topic/219239/?do=getNewComment"),
                createTopic("Мафия 442: Вас посетила Эрафия Police, впредь больше без матвинов фуллконфой", "https://prodota.ru/forum/topic/219101/?do=getNewComment"));

        SectionContent testContent = SectionContent.of(testSections, testTopics, Optional.of("https://prodota.ru/forum/45/page/2/"));
        String pageStr = Files.readString(resourcePath("prodota_forum_mafia.html"));
        SectionContent content = sectionParser.parse(pageStr);

        assertEquals(content, testContent);
    }
}
