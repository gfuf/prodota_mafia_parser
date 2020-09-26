package prodota.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import prodota.data.Section;

import java.util.Collection;

public class Parser {
    private  final static String OL_IPS_DATA_LIST = "ol.ipsDataList";

    private  final static String OL_TOPIC_LIST = "ol.cTopicList";

    private final static String C_FORUM_ROW_CLASS = ".cForumRow";

    private final static String IPS_DATA_ITEM_CLASS = ".ipsDataItem";

    private final static String IPS_DATA_ITEM_TITLE_CLASS = ".ipsDataItem_title";

    private final static String SPAN_IPS_CONTAINED = "span.ipsContained";

    private final static String A = "a";


    public Collection<Section> parse(String body)
    {
        Document doc = Jsoup.parse(body);

        return parse(doc);
    }

    public Collection<Section> parse(Document doc)
    {
        //кучу selection меняем на >
        Elements ipsData = doc.select(OL_IPS_DATA_LIST);

        Elements sections = ipsData.select(C_FORUM_ROW_CLASS)
                .select(IPS_DATA_ITEM_CLASS)
                .select(IPS_DATA_ITEM_TITLE_CLASS)
                .select(A);
        //sections.stream().map()
        System.out.println("Sections ");
        for(Element section : sections)
        {
            String name = section.text();
            String href = section.attr("href");
            System.out.println("name = " + name + " href = " + href);
        }

        System.out.println("Topics ");
        Elements topics = ipsData
                .select(OL_TOPIC_LIST)
                .select(IPS_DATA_ITEM_CLASS)
                .select(IPS_DATA_ITEM_TITLE_CLASS)
                .select(SPAN_IPS_CONTAINED)
                .select(SPAN_IPS_CONTAINED + ">" +A);

        for(Element topic : topics)
        {
            //есть возможность брать страницы

            String name = topic.text();
            String href = topic.attr("href");
            System.out.println("name = " + name + " href = " + href);
        }



        return null;
    }


}
