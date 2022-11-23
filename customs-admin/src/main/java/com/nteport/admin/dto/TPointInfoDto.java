package com.nteport.admin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nteport.admin.entity.TMaintainPersonEntity;
import com.nteport.admin.entity.TPatrolPersonEntity;
import com.nteport.admin.entity.TSupervisePersonEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TPointInfoDto {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 	点位类型：1-码头，2-渔港，3-船闸，4-船舶临时停靠点，5-其他走私风险点,6-走私案发点
     */
    private String type;

    private String typeName;

    /**
     * 	沿江还是沿海：1-沿江，2-沿海
     */
    private String along;

    private String alongName;
    /**
     * 	点位名称
     */
    private String name;

    /**
     * 	点位坐标
     */
    private String position;

    private String address;

    /**
     * 	性质：1-民营，2-国有，3-合资，4-个体（码头专用字段）
     */
    private String properties;
    private String propertiesName;
    /**
     * 	类别：1-危化品，2-通用散杂货
     */
    private String category;
    private String categoryName;
    /**
     * 	泊位数
     */
    private Integer berthNum;

    /**
     * 	吊机情况：1-有吊机，2-无吊机
     */
    private String crane;
    private String craneName;
    /**
     * 	最大吨级
     */
    private Integer maxTon;

    /**
     * 	码头数（渔港专用字段）
     */
    private Integer dockNum;

    /**
     * 	是否通航：1-通航，2-不通航（船闸专用字段）
     */
    private String whetherSail;
    private String whetherSailName;
    /**
     * 	日均进闸船舶数（船闸专用字段）
     */
    private String averageBoats;

    /**
     * 	用途（船舶临时停靠点专用字段）
     */
    private String useFor;

    private Long areaId;
    /**
     * 	所在地区县
     */
    private String area;

    private Long streetId;
    /**
     * 	所在街道
     */
    private String street;

    /**
     * 	照片
     */
    private String photoId;

    /**
     * 	走私风险等级：1-低，2-中，3-高
     */
    private String riskLevel;
    private String riskLevelName;
    /**
     * 	风险原因
     */
    private String riskReason;

    /**
     * 	监管举措
     */
    private String manageMeasure;

    /**
     * 	案发部位：1-岸上，2-水上
     */
    private String part;
    private String partName;
    /**
     * 案发货物类型
     */
    private String goodstype;
    private String goodstypeName;

    /**
     * 查获货物吨位
     */
    private String goodston;
    /**
     * 案发时间 年月日时
     */
    private String crimetime;

    /**
     * 	状态：0-暂存，1-待审核，2-审核通过，3-审核未通过
     */
    private String status;

    /**
     *
     */
    private String creatorName;

    /**
     *
     */
    private Long createUser;

    /**
     *
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private Long updateUser;

    /**
     *
     */
    private LocalDateTime updateTime;


    /**
     * 	备注
     */
    private String memo;

    /**
     * 主要负责人表
     */
    private List<TMaintainPersonEntity> maintainPersonList;

    /**
     * 监管责任人表
     */
    private List<TSupervisePersonEntity> supervisePersonList;
}
