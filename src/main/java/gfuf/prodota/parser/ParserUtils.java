package gfuf.prodota.parser;

public class ParserUtils
{
    final static String OL_IPS_DATA_LIST = "ol.ipsDataList";

    final static String OL_TOPIC_LIST = "ol.cTopicList";

    final static String LI_C_FORUM_ROW = "li.cForumRow";

    final static String LI_IPS_DATA_ITEM = "li.ipsDataItem";

    final static String IPS_DATA_ITEM_TITLE_CLASS = ".ipsDataItem_title";

    final static String SPAN_IPS_CONTAINED = "span.ipsContained";

    final static String SPAN_FA_LOCK = "i.fa-lock";

    final static String UL_IPS_PAGINATION = "ul.ipsPagination";

    final static String LI_PAGINATION_NEXT = "li.ipsPagination_next:not(.ipsPagination_inactive)";


    final static String PATH_TO_SECTION_LI = OL_IPS_DATA_LIST + ">" + LI_C_FORUM_ROW;

    final static String PATH_TO_TOPIC_LI = OL_TOPIC_LIST + ">" + LI_IPS_DATA_ITEM;

    final static String A = "a";

    final static String HREF = "href";

    final static String PATH_TO_SECTION_A = LI_IPS_DATA_ITEM + " " + IPS_DATA_ITEM_TITLE_CLASS + " " + A;

    final static String PATH_TO_TOPIC_A = IPS_DATA_ITEM_TITLE_CLASS + " " + SPAN_IPS_CONTAINED + ">" + A;

    final static String PATH_TO_FA_LOCK = IPS_DATA_ITEM_TITLE_CLASS + " " + SPAN_FA_LOCK;

    final static String PATH_TO_PAGINATION_NEXT_A = UL_IPS_PAGINATION + ">" + LI_PAGINATION_NEXT + ">" + A;

    private ParserUtils()
    {
    }
}
