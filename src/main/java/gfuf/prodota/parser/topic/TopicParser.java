package gfuf.prodota.parser.topic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.text.html.Option;
import java.util.Optional;

import static gfuf.prodota.parser.topic.TopicParserUtils.*;

public class TopicParser
{
    /**
     * поиск картинки в первом посте
     */
    public Optional<String> findFirstPicture(String body)
    {
        Document doc = Jsoup.parse(body);

        System.out.println(body);
        Optional<String> dataSrc = Optional.ofNullable(doc.selectFirst(ARTICLE_IPS_COMMENT))
                .map(element -> element.selectFirst(DIV_IPS_COMMENT_CONTENT))
                .map(element -> element.selectFirst(DIV_CPOST_CONTENTWRAP))
                .map(element -> element.selectFirst(IMG))
                .map(element -> element.attr(DATA_SRC));

        return dataSrc;
    }

    private Element selectFirstNullable(Element element, String cssQuery)
    {
        return element != null ? element.selectFirst(cssQuery) : null;
    }

}
