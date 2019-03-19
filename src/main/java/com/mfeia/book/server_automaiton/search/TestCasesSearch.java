package com.mfeia.book.server_automaiton.search;

import ZLYUtils.HttpUtils;
import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;

public class TestCasesSearch implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {

        /*
        搜索首页
         */
        JSONObject searchHomePageJson = JSONObject.fromObject(
                AutomationUtils.doGet(
                        SearchConfig.SERACH_HOME_PAGE, ""));
        performInspection.addtestFrameList(new SearchHomePage(searchHomePageJson), number);
        performInspection.addtestFrameList(
                new SearchBooksCheck(
                        searchHomePageJson.getJSONArray("hotBooks")
                        , 10, new SearchHomePage(), true), number);


        /*
        搜索更多
         */
        JSONObject morebdbooks = JSONObject.fromObject(
                AutomationUtils.doGet(
                        SearchConfig.SERACH_MOREBDBOOKS, "bdId=21&pageNo=1"));
        performInspection.addtestFrameList(new Morebdbooks(morebdbooks), number);
        performInspection.addtestFrameList(new SearchBooksCheck(
                morebdbooks.getJSONArray("list")
                , 20, new Morebdbooks(), false), number);


        /*
        搜索查询词
         */
        String keyword = AutomationUtils.getServerAutomaitonProperties(SearchConfig.SERACH_TERM);
        JSONObject searchassociationwords = JSONObject.fromObject(
                AutomationUtils.doGet(
                        SearchConfig.SERACK_SEARCHASSOCIATIONWORDS, "keyword=" + keyword));
        performInspection.addtestFrameList(new Searchassociationwords(searchassociationwords, keyword), number);

        /*
        搜索结果
         */
        String host = AutomationUtils.getServerAutomaitonProperties(AutomationUtils.getHost());
        String path = HttpUtils.getURI(
                AutomationUtils.getServerAutomaitonProperties(SearchConfig.SERACK_SEARCHRESULT),
                "keyword=" + keyword + "&pageNo=1" +
                        "&version=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.VERCODE)).toString();
        JSONObject searchResult = JSONObject.fromObject(
                AutomationUtils.doPost(host, path, null, null)
        );
        performInspection.addtestFrameList(new SearchResult(searchResult,keyword),number);
        performInspection.addtestFrameList(new SearchBooksCheck(
                searchResult.getJSONArray("list")
                , 20, new SearchResult(), true), number);

    }
}
