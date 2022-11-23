package com.nteport.admin.util;

import java.util.HashMap;

public class ConstantUtil {
    /**
     * 角色
     */
    //超级管理员
    public static final String ROLE_ADMIN = "admin";

    //市打私办管理
    public static final String ROLE_CITYADMIN = "cityAdmin";

    //市级部门管理员
    public static final String ROLE_SJBMADMIN = "sjbmAdmin";

    //市级部门巡防人员
    public static final String ROLE_CITYXUNFANG = "cityXunfang";

    //区县打私办管理员
    public static final String ROLE_QXDSBADMIN = "qxdsbAdmin";

    //区县部门管理员
    public static final String ROLE_QXBMADMIN = "qxbmAdmin";

    //区县部门巡防人员
    public static final String ROLE_QXBMXUNFANG = "qxbmXunfang";

    //乡镇街道管理员
    public static final String ROLE_XZJDADMIN = "xzjdAdmin";

    //部门名称
    public static final String CITYDSB = "市打私办";

    //部门名称
    public static final String DSB = "打私办";

    /**
     * 定时任务的参数
     */
    //每天
    public final static String TASK_FREQUENCY_EVERYDAY = "1";

    //待执行
    public final static String TASK_EXECUTED_TODO = "0";

    //已执行
    public final static String TASK_EXECUTED_DONE = "1";

    //已逾期
    public final static String TASK_EXECUTED_LATE = "2";

    //点位翻译
    //性质：1-民营，2-国有，3-合资，4-个体（码头专用字段）
    public final static HashMap<String,String> POINT_PROPERTIESMAP = new HashMap();
    //沿江还是沿海：1-沿江，2-沿海
    public final static HashMap<String,String> POINT_ALONGMAP = new HashMap();
    //类别：1-危化品，2-通用散杂货（码头专用）；1-开放式，2-封闭式（渔港专用）；1-水闸，2-船闸（船闸专用）
    public final static HashMap<String,String> POINT_CATEGORYMAP_1 = new HashMap();
    public final static HashMap<String,String> POINT_CATEGORYMAP_2 = new HashMap();
    public final static HashMap<String,String> POINT_CATEGORYMAP_3 = new HashMap();
    //吊机情况：1-有吊机，2-无吊机
    public final static HashMap<String,String> POINT_CRANEMAP = new HashMap();

    //是否通航：1-通航，2-不通航（船闸专用字段）
    public final static HashMap<String,String> POINT_WHETHERSAILMAP = new HashMap();

    //走私风险等级：1-低，2-中，3-高
    public final static HashMap<String,String> POINT_RISKLEVELMAP = new HashMap();

    //案发部位：1-岸上，2-水上，3-渔港，4-码头，5-临停点
    public final static HashMap<String,String> POINT_PARTMAP = new HashMap();

    //案发货物类型 1冻品、2香烟、3成品油、4煤炭、5海砂、6其他
    public final static HashMap<String,String> POINT_GOODSTYPEMAP = new HashMap();

    static {
        POINT_PROPERTIESMAP.put("1","民营");
        POINT_PROPERTIESMAP.put("2","国有");
        POINT_PROPERTIESMAP.put("3","合资");
        POINT_PROPERTIESMAP.put("4","个体");

        POINT_ALONGMAP.put("1","沿江");
        POINT_ALONGMAP.put("2","沿海");

        POINT_CATEGORYMAP_1.put("1","危化品");
        POINT_CATEGORYMAP_1.put("2","通用散杂货");

        POINT_CATEGORYMAP_2.put("1","开放式");
        POINT_CATEGORYMAP_2.put("2","封闭式");

        POINT_CATEGORYMAP_3.put("1","水闸");
        POINT_CATEGORYMAP_3.put("2","船闸");

        POINT_CRANEMAP.put("1","有吊机");
        POINT_CRANEMAP.put("2","无吊机");

        POINT_WHETHERSAILMAP.put("1","通航");
        POINT_WHETHERSAILMAP.put("2","不通航");

        POINT_RISKLEVELMAP.put("1","低");
        POINT_RISKLEVELMAP.put("2","中");
        POINT_RISKLEVELMAP.put("3","高");

        POINT_PARTMAP.put("1","岸上");
        POINT_PARTMAP.put("2","水上");
        POINT_PARTMAP.put("3","渔港");
        POINT_PARTMAP.put("4","码头");
        POINT_PARTMAP.put("5","临停点");

        POINT_GOODSTYPEMAP.put("1","冻品");
        POINT_GOODSTYPEMAP.put("2","香烟");
        POINT_GOODSTYPEMAP.put("3","成品油");
        POINT_GOODSTYPEMAP.put("4","煤炭");
        POINT_GOODSTYPEMAP.put("5","海砂");
        POINT_GOODSTYPEMAP.put("6","其他");
    }
}
