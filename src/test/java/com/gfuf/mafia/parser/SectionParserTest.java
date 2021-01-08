package com.gfuf.mafia.parser;

import com.gfuf.prodota.data.topic.TopicStatus;
import com.gfuf.prodota.data.Section;
import com.gfuf.prodota.data.topic.Topic;
import com.gfuf.prodota.data.content.SectionContent;
import com.gfuf.prodota.parser.section.SectionParser;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
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
        Set<Topic> testTopics = Set.of(createTopic("Регистрация№445", "https://prodota.ru/forum/topic/219579/", TopicStatus.OPEN),
                createTopic("Мафиозная флудилка т.18, Покорение статы в новом десятилетии", "https://prodota.ru/forum/topic/217907/", TopicStatus.OPEN),
                createTopic("Мафия 444. Трудно быть демоном. Ночи в 21-00", "https://prodota.ru/forum/topic/219541/", TopicStatus.CLOSED),
                createTopic("Стата версия 3.0 (черная)", "https://prodota.ru/forum/topic/217329/",TopicStatus.OPEN),
                createTopic("рейд за новичками (рега)", "https://prodota.ru/forum/topic/218642/",TopicStatus.OPEN),
                createTopic("Мафия 443: Туссент, Эрвелюс, Кровосиси", "https://prodota.ru/forum/topic/219239/",TopicStatus.CLOSED),
                createTopic("Мафия 442: Вас посетила Эрафия Police, впредь больше без матвинов фуллконфой", "https://prodota.ru/forum/topic/219101/", TopicStatus.CLOSED));

        SectionContent testContent = SectionContent.of(testSections, testTopics, Optional.of("https://prodota.ru/forum/45/page/2/"));
        String pageStr = Files.readString(resourcePath("prodota_forum_mafia.html"));
        SectionContent content = sectionParser.parse(pageStr);

        assertEquals(content, testContent);
    }
}
