//package com.nteport.admin.strategy;
//
//import com.google.common.collect.Range;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Collection;
//
////@Component
//public class QpDecHeadStrategy implements PreciseShardingAlgorithm, RangeShardingAlgorithm<Timestamp> {
//
//
//    @Override
//    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
//        System.out.println("11111111111111111111111111111111");
//        String target = shardingValue.getValue().toString();
//        return shardingValue.getLogicTableName() + "_" + target.substring(0,4);
//    }
//
//    @Override
//    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Timestamp> shardingValue) {
//        System.out.println("2222222222222222222222222222222222");
//
//        Collection<String> availables = new ArrayList<>();
//        Range valueRange = shardingValue.getValueRange();
//        for (String target : availableTargetNames) {
//            Integer shardValue = Integer.parseInt(target.substring(0,4));
//            if (valueRange.hasLowerBound()) {
//                String lowerStr = valueRange.lowerEndpoint().toString();
//                Integer start = Integer.parseInt(lowerStr.substring(0, 4));
//                if (start - shardValue > 0) {
//                    continue;
//                }
//            }
//            if (valueRange.hasUpperBound()) {
//                String upperStr = valueRange.upperEndpoint().toString();
//                Integer end = Integer.parseInt(upperStr.substring(0, 4));
//                if (end - shardValue < 0) {
//                    continue;
//                }
//            }
//            availables.add(target);
//        }
//        return availables;
//    }
//
//
//}
