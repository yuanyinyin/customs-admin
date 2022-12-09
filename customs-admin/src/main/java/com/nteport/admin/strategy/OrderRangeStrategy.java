//package com.nteport.admin.strategy;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.Range;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
//
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * RangeShardingAlgorithm 是可选的，用于处理BETWEEN AND， >， <，>=，<= 条件分片
// */
//@Slf4j
//public class OrderRangeStrategy implements RangeShardingAlgorithm<String> {
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
//
//    @Override
//    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
//
//        log.info("OrderRangeStrategy availableTargetNames: " + JSON.toJSONString(availableTargetNames));
//        String logicTableName = shardingValue.getLogicTableName();
//        Range<String> valueRange = shardingValue.getValueRange();
//
//        Set<String> queryRangeTables = extracted(logicTableName, LocalDateTime.parse(valueRange.lowerEndpoint(), formatter),
//                LocalDateTime.parse(valueRange.upperEndpoint(), formatter));
//        ArrayList<String> tables = new ArrayList<>(availableTargetNames);
//        tables.retainAll(queryRangeTables);
//        System.out.println(tables);
//        return tables;
//    }
//
//    private Set<String> extracted(String logicTableName, LocalDateTime lowerEndpoint, LocalDateTime upperEndpoint) {
//        Set<String> rangeTable = new HashSet<>();
//        while (lowerEndpoint.isBefore(upperEndpoint)) {
//            String str = getTableNameByDate(lowerEndpoint, logicTableName);
//            rangeTable.add(str);
//            lowerEndpoint = lowerEndpoint.plusMonths(1);
//        }
//
//        //up 月份
//        String str = getTableNameByDate(upperEndpoint, logicTableName);
//        rangeTable.add(str);
//        return rangeTable;
//    }
//
//    private String getTableNameByDate(LocalDateTime dateTime, String logicTableName) {
//        String year = dateTime.getYear() + "";
//        String monthValue = dateTime.getMonthValue() + "";
//        return logicTableName.concat("_").concat(year.concat("_").concat(monthValue));
//    }
//}
