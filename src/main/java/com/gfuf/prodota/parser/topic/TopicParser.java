package com.gfuf.prodota.parser.topic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Optional;

public class TopicParser
{
    /**
     * поиск картинки в первом посте
     */
    public Optional<String> findFirstPicture(String body)
    {
        Document doc = Jsoup.parse(body);


        Optional<String> dataSrc = Optional.ofNullable(doc.selectFirst(TopicParserUtils.ARTICLE_IPS_COMMENT))
                .map(element -> element.selectFirst(TopicParserUtils.DIV_IPS_COMMENT_CONTENT))
                .map(element -> element.selectFirst(TopicParserUtils.DIV_CPOST_CONTENTWRAP))
                .map(element -> element.selectFirst(TopicParserUtils.IMG))
                .map(element -> element.attr(TopicParserUtils.DATA_SRC));

        return dataSrc;
    }

    private Element selectFirstNullable(Element element, String cssQuery)
    {
        return element != null ? element.selectFirst(cssQuery) : null;
    }

}
