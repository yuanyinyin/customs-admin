//package com.nteport.admin.strategy;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Collection;
//import java.util.Objects;
//
///**
// * 日期分片
// * Precise处理 = 和 in 的路由
// */
//@Slf4j
//public class OrderItemStrategy implements PreciseShardingAlgorithm<String> {
//
//    private static  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
//
//    @Override
//    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
//
//        String str = shardingValue.getValue();
//        if (Objects.isNull(str)) {
//            return availableTargetNames.stream().findFirst().get();
//        }
//
//        LocalDateTime value = LocalDateTime.parse(str, formatter);
//
//        int year = value.getYear();
//        int monthValue = value.getMonthValue();
//        String logicTableName = shardingValue.getLogicTableName();
//        String table = logicTableName.concat("_").concat(year + "").concat("_").concat(monthValue + "");
//
//        log.info("OrderItemStrategy.doSharding table name: {}", table);
//        return availableTargetNames.stream().filter(s -> s.equals(table)).findFirst().orElseThrow(() -> new RuntimeException("逻辑分表不存在"));
//    }
//
//
//}
