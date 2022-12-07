//package com.nteport.admin.strategy;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
///**
// * 日期分片
// */
///**
// * 日期分片
// */
//@Slf4j
//@Component
//public class OrderStrategy implements PreciseShardingAlgorithm<Date> {
//
//
//    @Override
//    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
//        System.out.println("88888888888888888888888888888888888888888888");
//        System.out.println(collection);
//        System.out.println(preciseShardingValue);
//        //preciseShardingValue就是当前插入的字段值
//        //collection 内就是所有的逻辑表
//        //获取字段值
//        Date time = preciseShardingValue.getValue();
//
//        if(time == null){
//            throw new UnsupportedOperationException("prec is null");
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//        String year =sdf.format(time);
//
//        for (String tableName : collection) {//循环表名已确定使用哪张表
//            String name = tableName.substring(tableName.length() - 11);
//            if(year.equals(name)){
//                return tableName;//返回要插入的逻辑表
//            }
//        }
//        return null;
//    }
//
//}
