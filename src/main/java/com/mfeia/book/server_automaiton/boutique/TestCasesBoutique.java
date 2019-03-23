package com.mfeia.book.server_automaiton.boutique;

import server_automaiton_gather.server_automaiton_Utils.AutoHttpUtils;
import server_automaiton_gather.server_automaiton_Utils.AutomationUtils;
import server_automaiton_gather.server_automaiton_interface.AddTestCases;
import server_automaiton_gather.server_automaiton_interface.PerformInspection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.TestFrame;

import java.util.Map;
import java.util.regex.Pattern;


/**
 * 检查精品页每一个模块
 * 检查返回数据格式是否正确
 * 检查返回的数据数量是否正确
 */
public class TestCasesBoutique implements AddTestCases {

    @Override
    public void additionTestCases(final PerformInspection performInspection, double number) {
        double d;
        for (int i = 0; i < 6; i++) {
            d = i * 0.1;
            switch (i) {
                case 0:
                    //精品页
                    load(getJSONArray(BoutiqueConfig.BOUTIQUE_INDEX, i),
                            performInspection,
                            number);
                    loadRefresh(getJSONArray(BoutiqueConfig.BOUTIQUE_REFRESHBD, i),
                            performInspection,
                            number + d);
                    break;
                case 1:
                case 2:
                    //男频，女频
                    loadList(getJSONArray(BoutiqueConfig.BOUTIQUE_INDEX, i),
                            performInspection,
                            number + d);
                    loadListRefresh(getJSONArray(BoutiqueConfig.BOUTIQUE_REFRESHBD, i),
                            performInspection,
                            number + d + 0.01);
                    break;
                default:
                    //出版，新书，完结
                    loadNewArrivals(getJSONArray(BoutiqueConfig.BOUTIQUE_INDEX, i),
                            performInspection,
                            number + d, i);
                    loadListRefresh(JSONObject.fromObject(
                            AutoHttpUtils.doGet(BoutiqueConfig.BOUTIQUE_REFRESHBD, "type=" + i)),
                            performInspection,
                            number + d);
            }
        }

    }

    private JSONArray getJSONArray(String path, int type) {
        return JSONObject.fromObject(
                AutoHttpUtils.doGet(path, "type=" + type)).getJSONArray("data");
    }

    private void load(JSONArray jsonArray, PerformInspection performInspection, double number) {
        TestFrame roofBanner = null;
        TestFrame roofFiveImage = null;
        TestFrame roofNewExpress = null;
        TestFrame roofEditorSelected = null;
        TestFrame roofFlagFourteen = null;
        TestFrame roofMinBanner1 = null;
        TestFrame roofMinBanner2 = null;
        TestFrame roofFlagSeven = null;
        TestFrame roofFlagOne = null;
        TestFrame roofFlagSixtenn = null;
        TestFrame roofFlagNine = null;
        TestFrame roofFlagFifteen = null;
        TestFrame MorebdbooksFlagOne = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            switch (jsonArray.getJSONObject(i).getInt("flag")) {
                case RoofFlagSeven.FLAG:
                    if (roofFlagSeven == null) {
                        roofFlagSeven = new RoofFlagSeven(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagSeven(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }

                    break;
                case RoofFlagEight.FLAG:
                    roofFiveImage = new RoofFlagEight(
                            jsonArray.getJSONObject(i));
                    break;
                case RoofNewExpress.FLAG:
                    roofNewExpress = new RoofNewExpress(
                            jsonArray.getJSONObject(i));
                    break;
                case RoofFlagZero.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofEditorSelected == null) {
                        roofEditorSelected = new RoofFlagZero(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagZero(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    break;
                case RoofFlagFourteen.FLAG:
                    roofFlagFourteen = new RoofFlagFourteen(
                            jsonArray.getJSONObject(i)
                    );
                    break;
                case 6:
                    //此模块会有多个，并且每一个的FLAG相同但是内容不同
                    if (roofMinBanner1 == null) {
                        roofMinBanner1 = new RoofFlagSix1(
                                jsonArray.getJSONObject(i)
                        );
                    } else {
                        roofMinBanner2 = new RoofFlagSix2(
                                jsonArray.getJSONObject(i)
                        );
                    }
                    break;
                case RoofFlagOne.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofFlagOne == null) {
                        roofFlagOne = new RoofFlagOne(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagOne(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    MorebdbooksFlagOne = new RoofFlagOne(
                            jsonArray.getJSONObject(i));
                    break;
                case RoofRoot.FLAG:
                    roofBanner = new RoofRoot(
                            jsonArray.getJSONObject(i),
                            RoofRoot.FLAG,
                            1,
                            "置顶轮播图",
                            Pattern.compile("置顶轮播图"),
                            Pattern.compile(""),
                            0
                    );
                    break;
                case RoofFlagSixteen.FLAG:
                    roofFlagSixtenn = new RoofFlagSixteen(jsonArray.getJSONObject(i));
                    break;
                case RoofFlagNine.FLAG:
                    roofFlagNine = new RoofFlagNine(jsonArray.getJSONObject(i));
                    break;
                case RoofFlagFifteen.FLAG:
                    roofFlagFifteen = new RoofFlagFifteen(jsonArray.getJSONObject(i));
                    break;
                default:
                    performInspection.addtestFrameList(
                            new ErrException(this.getClass(), "FLAG:" + jsonArray.getJSONObject(i).getInt("flag"),
                                    new Exception("未找到FLAG"), number), number);

            }
        }
        performInspection.addtestFrameList(roofBanner, number);
        performInspection.addtestFrameList(roofFiveImage, number);
        performInspection.addtestFrameList(roofNewExpress, number);
        performInspection.addtestFrameList(roofEditorSelected, number);
        performInspection.addtestFrameList(roofFlagFourteen, number);
        performInspection.addtestFrameList(roofMinBanner1, number);
        performInspection.addtestFrameList(roofMinBanner2, number);
        performInspection.addtestFrameList(roofFlagSeven, number);
        performInspection.addtestFrameList(roofFlagOne, number);
        performInspection.addtestFrameList(roofFlagSixtenn, number);
        performInspection.addtestFrameList(roofFlagNine, number);
        performInspection.addtestFrameList(roofFlagFifteen, number);
        performInspection.addtestFrameList(new Morebdbooks(MorebdbooksFlagOne).setTag(number), number);
        performInspection.addtestFrameList(new ChangeBook(roofFlagFourteen, number).setTag(number), number);
        performInspection.addtestFrameList(new ChangeTag(roofFlagNine, number).setTag(number), number);
    }

    /**
     * 精品顶部刷新
     *
     * @param jsonArray
     * @param performInspection
     * @param number
     */
    private void loadRefresh(JSONArray jsonArray, PerformInspection performInspection, double number) {
        TestFrame roofNewExpress = null;
        TestFrame roofEditorSelected = null;
        TestFrame roofFlagFourteen = null;
        TestFrame roofFlagOne = null;
        TestFrame roofFlagSixtenn = null;
        TestFrame roofFlagFifteen = null;
        TestFrame MorebdbooksFlagOne = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            switch (jsonArray.getJSONObject(i).getInt("flag")) {
                case RoofNewExpress.FLAG:
                    roofNewExpress = new RoofNewExpress(
                            jsonArray.getJSONObject(i));
                    break;
                case RoofFlagZero.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofEditorSelected == null) {
                        roofEditorSelected = new RoofFlagZero(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagZero(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    break;
                case RoofFlagFourteen.FLAG:
                    roofFlagFourteen = new RoofFlagFourteen(
                            jsonArray.getJSONObject(i)
                    );
                    break;
                case RoofFlagOne.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofFlagOne == null) {
                        roofFlagOne = new RoofFlagOne(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagOne(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    MorebdbooksFlagOne = new RoofFlagOne(
                            jsonArray.getJSONObject(i));
                    break;
                case RoofFlagSixteen.FLAG:
                    roofFlagSixtenn = new RoofFlagSixteen(jsonArray.getJSONObject(i));
                    break;
                case RoofFlagFifteen.FLAG:
                    roofFlagFifteen = new RoofFlagFifteen(jsonArray.getJSONObject(i));
                    break;
                default:
                    performInspection.addtestFrameList(
                            new ErrException(this.getClass(), "FLAG:" + jsonArray.getJSONObject(i).getInt("flag"),
                                    new Exception("未找到FLAG"), number), number);
            }
        }
        performInspection.addtestFrameList(roofNewExpress, number);
        performInspection.addtestFrameList(roofEditorSelected, number);
        performInspection.addtestFrameList(roofFlagFourteen, number);
        performInspection.addtestFrameList(roofFlagOne, number);
        performInspection.addtestFrameList(roofFlagSixtenn, number);
        performInspection.addtestFrameList(roofFlagFifteen, number);
        performInspection.addtestFrameList(new Morebdbooks(MorebdbooksFlagOne).setTag(number), number);
        performInspection.addtestFrameList(new ChangeBook(roofFlagFourteen, number).setTag(number), number);
    }

    /**
     * 榜单
     * 男频，女频
     *
     * @param jsonArray
     * @param performInspection
     * @param number
     */
    private void loadList(JSONArray jsonArray,
                          PerformInspection performInspection,
                          double number) {
        TestFrame roofFlagZero = null;
        TestFrame roofFlagOne = null;
        TestFrame MorebdbooksFlagOne = null;
        TestFrame roofFlagSeven = null;
        TestFrame roofFlagFive = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            switch (jsonArray.getJSONObject(i).getInt("flag")) {
                case RoofFlagZero.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofFlagZero == null) {
                        roofFlagZero = new RoofFlagZero(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagZero(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    break;
                case RoofFlagOne.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofFlagOne == null) {
                        roofFlagOne = new RoofFlagOne(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagOne(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    MorebdbooksFlagOne = new RoofFlagOne(
                            jsonArray.getJSONObject(i));
                    break;
                case RoofFlagSeven.FLAG:
                    roofFlagSeven = new RoofFlagSeven(
                            jsonArray.getJSONObject(i));
                    break;
                case RoofFlagFive.FLAG:
                    roofFlagFive = new RoofFlagFive(
                            jsonArray.getJSONObject(i));
                    break;
                default:
                    performInspection.addtestFrameList(
                            new ErrException(this.getClass(), "FLAG:" + jsonArray.getJSONObject(i).getInt("flag"),
                                    new Exception("未找到FLAG"), number), number);
            }
        }
        performInspection.addtestFrameList(roofFlagZero, number);
        performInspection.addtestFrameList(roofFlagOne, number);
        performInspection.addtestFrameList(new Morebdbooks(MorebdbooksFlagOne).setTag(number), number);
        performInspection.addtestFrameList(roofFlagSeven, number);
        if (roofFlagFive != null)
            performInspection.addtestFrameList(roofFlagFive, number);
    }


    /**
     * 榜单刷新
     * 男频，女频
     *
     * @param jsonArray
     * @param performInspection
     * @param number
     */
    private void loadListRefresh(JSONArray jsonArray,
                                 PerformInspection performInspection,
                                 double number) {
        TestFrame roofFlagZero = null;
        TestFrame roofFlagOne = null;
        TestFrame MorebdbooksFlagOne = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            switch (jsonArray.getJSONObject(i).getInt("flag")) {
                case RoofFlagZero.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofFlagZero == null) {
                        roofFlagZero = new RoofFlagZero(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagZero(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    break;
                case RoofFlagOne.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofFlagOne == null) {
                        roofFlagOne = new RoofFlagOne(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagOne(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    MorebdbooksFlagOne = new RoofFlagOne(
                            jsonArray.getJSONObject(i));
                    break;
                default:
                    performInspection.addtestFrameList(
                            new ErrException(this.getClass(), "FLAG:" + jsonArray.getJSONObject(i).getInt("flag"),
                                    new Exception("未找到FLAG"), number), number);
            }
        }
        performInspection.addtestFrameList(roofFlagZero, number);
        performInspection.addtestFrameList(roofFlagOne, number);
        performInspection.addtestFrameList(new Morebdbooks(MorebdbooksFlagOne).setTag(number), number);
    }

    /**
     * 出版/新书/完结
     */
    private void loadNewArrivals(JSONArray jsonArray,
                                 PerformInspection performInspection,
                                 double number, int type) {
        RoofFlagOne roofFlagOne = null;
        TestFrame morebdbooksFlagOne = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            switch (jsonArray.getJSONObject(i).getInt("flag")) {
                case RoofFlagOne.FLAG:
                    //此模块会同时可能出现多个，所以每次都打印来检查多个模块是否通过
                    if (roofFlagOne == null) {
                        roofFlagOne = new RoofFlagOne(
                                jsonArray.getJSONObject(i));
                    } else {
                        performInspection.addtestFrameList(new RoofFlagOne(
                                jsonArray.getJSONObject(i)
                        ), number);
                    }
                    morebdbooksFlagOne = new RoofFlagOne(
                            jsonArray.getJSONObject(i));
                    break;
                default:
                    performInspection.addtestFrameList(
                            new ErrException(this.getClass(), "FLAG:" + jsonArray.getJSONObject(i).getInt("flag"),
                                    new Exception("未找到FLAG"), number), number);
            }
        }

        if (type == 5) {
            if (roofFlagOne == null) {
                performInspection.addtestFrameList(new ErrException(this.getClass(), "type=5", new Exception(
                        new NullPointerException()), number), number);
            } else {
                performInspection.addtestFrameList(roofFlagOne.setShow(1), number);
            }

        } else {
            performInspection.addtestFrameList(roofFlagOne, number);
        }
        performInspection.addtestFrameList(new Morebdbooks(morebdbooksFlagOne), number);

    }

    /**
     * 出版，新书，完结
     *
     * @param jsonObject
     * @param performInspection
     * @param number
     */
    public void loadListRefresh(JSONObject jsonObject,
                                PerformInspection performInspection,
                                double number) {
        performInspection.addtestFrameList(new RoofNewExpress() {
                    @Override
                    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {
                        if (this.getJsonArray().size() == 0) return;
                        jsonArrayMap.putAll(new RoofNewExpress().getJsonArrayMap());
                    }

                    @Override
                    public void settingJsonMap(Map<String, Object> jsonMap) {
                        jsonMap.put("code", 0);
                    }

                }.setCaseName("loadListRefresh 出版，新书，完结")
                        .setJsonObject(jsonObject)
                        .setJsonArray(jsonObject.getJSONArray("data"))
                , number);

    }

}