package com.nteport.admin.dto;
import java.util.HashMap;
import java.util.Map;

public class PrintTag {
    public Map i_bgd_hdd_map = new HashMap();
    public Map e_bgd_hdd_map = new HashMap();
    public Map i_qd_hdd_map = new HashMap();
    public Map e_qd_hdd_map = new HashMap();


    //商品附加页
    public Map i_bgd_fjl_map = new HashMap();
    public Map e_bgd_fjl_map = new HashMap();
    public Map i_qd_fjl_map = new HashMap();
    public Map e_qd_fjl_map = new HashMap();

    //查验通知书
    public Map i_bgd_cytzs_map = new HashMap();
    public Map e_bgd_cytzs_map = new HashMap();

    public PrintTag() {
        /***************核对单************************/
        //进口
        i_bgd_hdd_map.put("hdd_hwbgd","进口货物报关单");
        i_bgd_hdd_map.put("hdd_sfhr", "境外发货人");
        i_bgd_hdd_map.put("hdd_xfsydw", "消费使用单位");
        i_bgd_hdd_map.put("hdd_jjgb", "进境关别");
        i_bgd_hdd_map.put("hdd_rq", "进口日期");
        i_bgd_hdd_map.put("hdd_hwcfdd", "货物存放地点");
        i_bgd_hdd_map.put("hdd_qyg", "启运港");
        i_bgd_hdd_map.put("hdd_qygdq", "启运国（地区）");
        i_bgd_hdd_map.put("hdd_jtg", "经停港");
        i_bgd_hdd_map.put("hdd_rjka", "入境口岸");
        i_bgd_hdd_map.put("hdd_jnmdd", "境内目的地");

        i_bgd_hdd_map.put("hdd_zzmdg", "原产国（地区）");
        i_bgd_hdd_map.put("hdd_jck", "进口");
        i_bgd_hdd_map.put("hdd_inRatio_payWay", "征税比例");


        //出口"
        e_bgd_hdd_map.put("hdd_hwbgd", "出口货物报关单");
        e_bgd_hdd_map.put("hdd_sfhr", "境外收货人");
        e_bgd_hdd_map.put("hdd_xfsydw", "生产销售单位");
        e_bgd_hdd_map.put("hdd_jjgb", "出境关别");
        e_bgd_hdd_map.put("hdd_rq", "出口日期");
        e_bgd_hdd_map.put("hdd_hwcfdd", "");
        e_bgd_hdd_map.put("hdd_qyg", "");
        e_bgd_hdd_map.put("hdd_qygdq", "运抵国（地区）");
        e_bgd_hdd_map.put("hdd_jtg", "指运港");
        e_bgd_hdd_map.put("hdd_rjka", "离境口岸");
        e_bgd_hdd_map.put("hdd_jnmdd", "境内货源地");

        e_bgd_hdd_map.put("hdd_zzmdg", "最终目的国（地区）");
        e_bgd_hdd_map.put("hdd_jck", "出口");
        e_bgd_hdd_map.put("hdd_inRatio_payWay", "结汇方式");

        //进境
        i_qd_hdd_map.put ("hdd_hwbgd", "进口货物报关单");
        i_qd_hdd_map.put ("hdd_sfhr", "境外发货人");
        i_qd_hdd_map.put ("hdd_xfsydw", "消费使用单位");
        i_qd_hdd_map.put ("hdd_jjgb", "进境关别");
        i_qd_hdd_map.put ("hdd_rq", "进口日期");
        i_qd_hdd_map.put ("hdd_hwcfdd", "货物存放地点");
        i_qd_hdd_map.put ("hdd_qyg", "启运港");
        i_qd_hdd_map.put ("hdd_qygdq", "启运国（地区）");
        i_qd_hdd_map.put ("hdd_jtg", "经停港");
        i_qd_hdd_map.put ("hdd_rjka", "入境口岸");
        i_qd_hdd_map.put ("hdd_jnmdd", "境内目的地");

        i_qd_hdd_map.put("hdd_zzmdg", "原产国（地区）");
        i_qd_hdd_map.put("hdd_jck", "进口");
        i_qd_hdd_map.put("hdd_inRatio_payWay", "征税比例");

        //出境
        e_qd_hdd_map.put ("hdd_hwbgd", "出口货物报关单");
        e_qd_hdd_map.put ("hdd_sfhr", "境外收货人");
        e_qd_hdd_map.put ("hdd_xfsydw", "生产销售单位");
        e_qd_hdd_map.put ("hdd_jjgb", "出境关别");
        e_qd_hdd_map.put ("hdd_rq", "出口日期");
        e_qd_hdd_map.put ("hdd_hwcfdd", "");
        e_qd_hdd_map.put ("hdd_qyg", "");
        e_qd_hdd_map.put ("hdd_qygdq", "运抵国（地区）");
        e_qd_hdd_map.put ("hdd_jtg", "指运港");
        e_qd_hdd_map.put ("hdd_rjka", "离境口岸");
        e_qd_hdd_map.put ("hdd_jnmdd", "境内货源地");

        e_qd_hdd_map.put("hdd_zzmdg", "最终目的国（地区）");
        e_qd_hdd_map.put("hdd_jck", "出口");
        e_qd_hdd_map.put("hdd_inRatio_payWay", "结汇方式");


        /***************商品附加页************************/
        //进口
        i_bgd_fjl_map.put("fjy_zzmdg", "原产国（地区）");
        i_bgd_fjl_map.put("fjy_jck", "进口");

        //出口
        e_bgd_fjl_map.put("fjy_zzmdg", "最终目的国（地区）");
        e_bgd_fjl_map.put("fjy_jck", "出口");

        //进境
        i_qd_fjl_map.put("fjy_zzmdg", "原产国（地区）");
        i_qd_fjl_map.put("fjy_jck", "进口");

        //出境
        e_qd_fjl_map.put("fjy_zzmdg", "最终目的国（地区）");
        e_qd_fjl_map.put("fjy_jck", "出口");

        /***************查验通知书************************/
        //进口
        i_bgd_cytzs_map.put("tzs_jck","进口");
        i_bgd_cytzs_map.put("tzs_gb","进口关别");
        i_bgd_cytzs_map.put("tzs_rq","进口日期");
        i_bgd_cytzs_map.put("tzs_dw","消费使用单位");
        i_bgd_cytzs_map.put("tzs_dq","启运国(地区)");
        i_bgd_cytzs_map.put("tzs_jtg","经停港");
        i_bgd_cytzs_map.put("tzs_jn","境内目的地");
        //出口
        e_bgd_cytzs_map.put("tzs_jck","出口");
        e_bgd_cytzs_map.put("tzs_gb","出口关别");
        e_bgd_cytzs_map.put("tzs_rq","出口日期");
        e_bgd_cytzs_map.put("tzs_dw","生产销售单位");
        e_bgd_cytzs_map.put("tzs_dq","运抵国(地区)");
        e_bgd_cytzs_map.put("tzs_jtg","指运港");
        e_bgd_cytzs_map.put("tzs_jn","境内货源地");

    }


    /****
     * 模板上固定字段显示
     * @param resMap
     * @param ieFlag
     * @param cusType
     * @param modelName
     */
    public  void putTagByType(Map resMap, String ieFlag, String cusType , String modelName){
        if ("I".equals(ieFlag)) {//进
            if ("1".equals(cusType)) {//报关单
                if (modelName.contains("bgdHdd") || modelName.contains("fxtzs") || modelName.contains("bgdm")) {//核对联
                    resMap.putAll(i_bgd_hdd_map);
                }
                if (modelName.contains("goodsFjy")) { //附加联
                    resMap.putAll(i_bgd_fjl_map);
                }
                if(modelName.contains("cytzs")){
                    resMap.putAll(i_bgd_cytzs_map);
                }
            } else {//清单
                if (modelName.contains("bgdHdd") || modelName.contains("fxtzs") || modelName.contains("bgdm")) {//核对联
                    resMap.putAll(i_qd_hdd_map);
                }
                if (modelName.contains("goodsFjy")) { //附加联
                    resMap.putAll(i_qd_fjl_map);
                }
            }
        } else {//出
            if ("1".equals(cusType)) {//报关单
                if (modelName.contains("bgdHdd") || modelName.contains("fxtzs") || modelName.contains("bgdm")) {//核对联
                    resMap.putAll(e_bgd_hdd_map);
                }
                if (modelName.contains("goodsFjy")) { //附加联
                    resMap.putAll(e_bgd_fjl_map);
                }
                if(modelName.contains("cytzs")){
                    resMap.putAll(e_bgd_cytzs_map);
                }
            } else {//清单
                if (modelName.contains("bgdHdd") || modelName.contains("fxtzs") || modelName.contains("bgdm")) {//核对联
                    resMap.putAll(e_qd_hdd_map);
                }
                if (modelName.contains("goodsFjy")) { //附加联
                    resMap.putAll(e_qd_fjl_map);
                }

            }
        }
    }

}
