package com.mfeia.book.server_automaiton.paiHang_stackRoom;


import ZLYUtils.DoubleOperation;
import com.mfeia.book.server_automaiton.UserInfoUtils;
import net.sf.json.JSONObject;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_Utils.ThreadPoolUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;

import java.util.*;


/**
 * 书库出版不进行三级筛选查询
 * 书库页进行遍历每一种晒选条件检查
 */
public class TestCasesPaiHangStackRoom implements AddTestCases {
    @Override
    public void additionTestCases(PerformInspection performInspection, double number) throws Exception {
        JSONObject paiHangJson = JSONObject.fromObject(
                AutoHttpUtils.doGet(PaiHangStackRoomConfig.PAI_HANG_PHINDEXYS, "", number)
        );
        Phindexys phindexys = new Phindexys(paiHangJson);
        phindexys.setTag(number);
        //需要执行对象，因为执行后可以获取到ItemId
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
                ThreadPoolUtils.addExecute(new Runnable() {
                    @Override
                    public void run() {

                        double pow = Math.pow(10, integer.trim().length());
                              /*
                        将bdid转换为0.XXX小数
                         */
                        double nb = DoubleOperation.add(number, DoubleOperation.div(Integer.parseInt(integer)
                                , pow));
                        String query = "uid=" + userId
                                + "&cnid=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID)
                                + "&bdid=" + integer
                                + "&name=" + name
                                + "&index=1&pageSize=20&curpage=1";
                        JSONObject jsonObject = JSONObject.fromObject(
                                AutoHttpUtils.doGet(PaiHangStackRoomConfig.PAI_HANG_NEWRANKLIST, query, nb)
                        );

                        performInspection.addtestFrameList(new NewRankList(jsonObject)
                                , nb);
                    }
                });
            }

        }

    /*
    执行书库
     */
        JSONObject stackRoomJson = JSONObject.fromObject(
                AutoHttpUtils.doGet(PaiHangStackRoomConfig.STACK_ROOM_FINDEXYS, "", number)
        );
        StackRoom stackRoom = new StackRoom(stackRoomJson);
        stackRoom.setTag(number);
        performInspection.addtestFrameList(stackRoom, number);
        //免追书库
        JSONObject catelogueJson = JSONObject.fromObject(
                AutoHttpUtils.doGet(PaiHangStackRoomConfig.STATCK_ROOM_MZ_CATELOGUE, "", number)
        );
        performInspection.addtestFrameList(new Catelogue(catelogueJson), number);

        for (Iterator<Map.Entry<String, List<String>>> iterator = stackRoom.getItemId().entrySet().iterator();
             iterator.hasNext(); ) {
            Map.Entry<String, List<String>> m = iterator.next();
            final String name = m.getKey();
            for (final String integer : m.getValue()) {
                ThreadPoolUtils.addExecute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            /*
                             * 书库二级分类
                             */
                            double pow = Math.pow(10, integer.trim().length());
                            double shuKuTwoNumber = DoubleOperation.add(number, DoubleOperation.div(Integer.parseInt(integer), pow));
                            String query = "uid=" + userId
                                    + "&cnid=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID)
                                    + "&flid=" + integer
                                    + "&name=" + name
                                    + "&thitdCateId=0&pageSize=20&curpage=1&bookStatus=0&sortType=0";
                            JSONObject jsonObject = JSONObject.fromObject(
                                    AutoHttpUtils.doGet(PaiHangStackRoomConfig.STACK_ROOM_CATELISTNEW, query, shuKuTwoNumber)
                            );

                            CateListNew cateListNew = new CateListNew(jsonObject, "flid=" + integer + "&name=" + name + "&thitdCateId=0&pageSize=20&curpage=1&bookStatus=0&sortType=0");
                            cateListNew.setTag(shuKuTwoNumber);
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
                                        pow = Math.pow(10, integer.trim().length());
                                        shuKuTwoNumber = DoubleOperation.add(number, DoubleOperation.div(Integer.parseInt(integer), pow));
                                        query = "uid=" + userId
                                                + "&cnid=" + AutomationUtils.getServerAutomaitonProperties(AutomationUtils.CNID)
                                                + "&flid=" + integer
                                                + "&name=" + name
                                                + "&thitdCateId=" + thirdCateId
                                                + "&pageSize=20&curpage=1" +
                                                "&bookStatus=" + bookStatu +
                                                "&sortType=" + shrtTypeNumber;
                                        jsonObject = JSONObject.fromObject(
                                                AutoHttpUtils.doGet(PaiHangStackRoomConfig.STACK_ROOM_CATELISTNEW,
                                                        query, shuKuTwoNumber)
                                        );

                                        cateListNew = new CateListNew(jsonObject, "flid=" + integer
                                                + "&name=" + name
                                                + "&thitdCateId=" + thirdCateId
                                                + "&pageSize=20&curpage=1" +
                                                "&bookStatus=" + bookStatu +
                                                "&sortType=" + shrtTypeNumber);
                                        performInspection.addtestFrameList(cateListNew, shuKuTwoNumber);
                                    }
                                }

                            }
                        } catch (Exception e) {
                            performInspection.addtestFrameList(new ErrException(TestCasesPaiHangStackRoom.class, "执行书库二级分类", e, number));
                        }
                    }
                });
            }

        }

    }
}
