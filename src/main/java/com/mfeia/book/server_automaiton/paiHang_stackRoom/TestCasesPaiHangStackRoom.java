package com.mfeia.book.server_automaiton.paiHang_stackRoom;


import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.UserInfoUtils;
import net.sf.json.JSONObject;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;

import java.util.*;


/**
 * 书库出版不进行三级筛选查询
 */
public class TestCasesPaiHangStackRoom implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {
        JSONObject paiHangJson = JSONObject.fromObject(
                AutomationUtils.doGet(PaiHangStackRoomConfig.PAI_HANG_PHINDEXYS, "")
        );
        Phindexys phindexys = new Phindexys(paiHangJson);
        phindexys.setTag(number);
        //需要执行对象，因为执行后可以获取到ItemId
        phindexys.stratCheck();
        performInspection.addtestFrameList(phindexys, number);
        final String userId = UserInfoUtils.getHistoryUserId();
        /*
        执行排行
         */
        for (Iterator<Map.Entry<String, List<String>>> iterator = phindexys.getItemId().entrySet().iterator();
             iterator.hasNext(); ) {
            Map.Entry<String, List<String>> m = iterator.next();
            final String name = m.getKey();
            for (final String integer : m.getValue()) {
                AutomationUtils.addExecute(new Runnable() {
                    @Override
                    public void run() {
                        String query = "uid=" + userId
                                + "&cnid=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID)
                                + "&bdid=" + integer
                                + "&name=" + name
                                + "&index=1&pageSize=20&curpage=1";
                        JSONObject jsonObject = JSONObject.fromObject(
                                AutomationUtils.doGet(PaiHangStackRoomConfig.PAI_HANG_NEWRANKLIST, query)
                        );
                        double pow = Math.pow(10, integer.trim().length());
                        performInspection.addtestFrameList(new NewRankList(jsonObject), DoubleOperation.add(number, DoubleOperation.div(Integer.parseInt(integer), pow)));
                    }
                });
            }

        }

    /*
    执行书库
     */
        JSONObject stackRoomJson = JSONObject.fromObject(
                AutomationUtils.doGet(PaiHangStackRoomConfig.STACK_ROOM_FINDEXYS, "")
        );
        Phindexys stackRoom = new StackRoom(stackRoomJson);
        stackRoom.setTag(number);
        stackRoom.stratCheck();
        performInspection.addtestFrameList(stackRoom, number);
        for (Iterator<Map.Entry<String, List<String>>> iterator = stackRoom.getItemId().entrySet().iterator();
             iterator.hasNext(); ) {
            Map.Entry<String, List<String>> m = iterator.next();
            final String name = m.getKey();
            for (final String integer : m.getValue()) {
                AutomationUtils.addExecute(new Runnable() {
                    @Override
                    public void run() {
                        /*
                         * 书库二级分类
                         */
                        String query = "uid=" + userId
                                + "&cnid=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID)
                                + "&flid=" + integer
                                + "&name=" + name
                                + "&thitdCateId=0&pageSize=20&curpage=1&bookStatus=0&sortType=0";
                        JSONObject jsonObject = JSONObject.fromObject(
                                AutomationUtils.doGet(PaiHangStackRoomConfig.STACK_ROOM_CATELISTNEW, query)
                        );
                        double pow = Math.pow(10, integer.trim().length());
                        CateListNew cateListNew = new CateListNew(jsonObject, "flid=" + integer +  "&name=" + name+"&thitdCateId=0&pageSize=20&curpage=1&bookStatus=0&sortType=0");
                        cateListNew.setTag(DoubleOperation.add(number, DoubleOperation.div(Integer.parseInt(integer), pow)));
                        //需要执行对象，因为执行后可以获取到ThirdCateListId
                        cateListNew.stratCheck();
                        performInspection.addtestFrameList(cateListNew, DoubleOperation.add(number, DoubleOperation.div(Integer.parseInt(integer), pow)));
                        /**
                         * 执行书库三级筛选
                         */
                        String[] bookStatus = {"0", "1", "3"};
                        String[] sortType = {"1", "2"};

                        List<String> thirdCateIdList = new ArrayList<>(cateListNew.getThirdCateListId());
                        //0为全部
                        thirdCateIdList.add("0");

                        for (String thirdCateId : thirdCateIdList) {
                            //出版书籍少，线上中已把出版中的筛选条件去除
                            if ("出版".equals(name)) continue;
                            for (String bookStatu : bookStatus) {
                                for (String shrtTypeNumber : sortType) {
                                    query = "uid=" + userId
                                            + "&cnid=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID)
                                            + "&flid=" + integer
                                            + "&name=" + name
                                            + "&thitdCateId=" + thirdCateId
                                            + "&pageSize=20&curpage=1" +
                                            "&bookStatus=" + bookStatu +
                                            "&sortType=" + shrtTypeNumber;
                                    jsonObject = JSONObject.fromObject(
                                            AutomationUtils.doGet(PaiHangStackRoomConfig.STACK_ROOM_CATELISTNEW,
                                                    query)
                                    );
                                    pow = Math.pow(10, integer.trim().length());
                                    cateListNew = new CateListNew(jsonObject, "flid=" + integer
                                            + "&name=" + name
                                            + "&thitdCateId=" + thirdCateId
                                            + "&pageSize=20&curpage=1" +
                                            "&bookStatus=" + bookStatu +
                                            "&sortType=" + shrtTypeNumber);
                                    performInspection.addtestFrameList(cateListNew, DoubleOperation.add(number, DoubleOperation.div(Integer.parseInt(integer), pow)));
                                }
                            }

                        }
                    }
                });
            }

        }

    }
}
