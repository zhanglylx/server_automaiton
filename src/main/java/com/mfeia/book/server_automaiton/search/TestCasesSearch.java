package com.mfeia.book.server_automaiton.search;

import ZLYUtils.HttpUtils;
import com.mfeia.book.server_automaiton.AutomationBooksMap;
import com.mfeia.book.server_automaiton.Book;
import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.BooksMapCirculationCallBack;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;

import java.util.UUID;

public class TestCasesSearch implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {

        /*
        搜索首页
         */
        JSONObject searchHomePageJson = JSONObject.fromObject(
                AutoHttpUtils.doGet(
                        SearchConfig.SERACH_HOME_PAGE, "", number));
        performInspection.addtestFrameList(new SearchHomePage(searchHomePageJson), number);
        performInspection.addtestFrameList(
                new SearchBooksCheck(
                        searchHomePageJson.getJSONArray("hotBooks")
                        , 10, new SearchHomePage(), true), number);


        /*
        搜索更多
         */
        JSONObject morebdbooks = JSONObject.fromObject(
                AutoHttpUtils.doGet(
                        SearchConfig.SERACH_MOREBDBOOKS, "bdId=21&pageNo=1", number));
        performInspection.addtestFrameList(new Morebdbooks(morebdbooks), number);
        performInspection.addtestFrameList(new SearchBooksCheck(
                morebdbooks.getJSONArray("list")
                , 20, new Morebdbooks(), false), number);


        AutomationBooksMap.getAutomationBooksMap().booksMapCirculation(new BooksMapCirculationCallBack() {
            @Override
            public void bookCirculation(Book book, double circulationNumber) throws Exception {

                /*
               搜索查询词
                */
                String keyword = book.getBookName();
                String caseName = "关键词["+keyword+"] book:"+book;
                JSONObject searchassociationwords = JSONObject.fromObject(
                        AutoHttpUtils.doGet(
                                SearchConfig.SERACK_SEARCHASSOCIATIONWORDS, "keyword=" + keyword, number));
                performInspection.addtestFrameList(
                        new Searchassociationwords(searchassociationwords, keyword).setCaseName(caseName), number);
                  /*
                   搜索结果
                  */
                String host = AutomationUtils.getServerAutomaitonProperties(AutomationUtils.getHost());
                String path = HttpUtils.getURI(
                        AutomationUtils.getServerAutomaitonProperties(SearchConfig.SERACK_SEARCHRESULT),
                        "keyword=" + keyword + "&pageNo=1" +
                                "&version=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.VERCODE)).toString();

                JSONObject searchResult = JSONObject.fromObject(
                        AutoHttpUtils.doPost(host, path, null, null, number)
                );
                performInspection.addtestFrameList(new SearchResult(searchResult, keyword).setCaseName(caseName), number);
                performInspection.addtestFrameList(new SearchBooksCheck(
                        searchResult.getJSONArray("list")
                        , 2, new SearchResult(), true).setCaseName(caseName), number);
            }
        });

//          暂时先不用
//        /**
//         * 执行无结果查询词
//         * keyword使用时间戳加随机中文字符
//         */
//        String host = AutomationUtils.getServerAutomaitonProperties(AutomationUtils.getHost());
//        String path = HttpUtils.getURI(
//                AutomationUtils.getServerAutomaitonProperties(SearchConfig.SERACK_SEARCHRESULT),
//                "keyword=" + System.currentTimeMillis() + "&pageNo=1" +
//                        "&version=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.VERCODE)).toString();
//        JSONObject ineffectivenessSearchResult = JSONObject.fromObject(
//                AutoHttpUtils.doPost(host, path, null, null)
//
//        );
//
//        performInspection.addtestFrameList(new SearchResult(ineffectivenessSearchResult).setCaseName("搜索无结果检查"), number);

    }
}
