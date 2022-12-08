package com.nteport.admin.util;


import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommonUtil {
    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toUpperCase();
            resultMap.put(newKey, orgMap.get(key));
        }
        return resultMap;
    }


    public static List<Map>  transformUpperCase(List<Map> list) {
        List<Map> resultlist = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return resultlist;
        }
        for (Map<String, Object> map :list
             ) {
            Map<String, Object> resultMap = new HashMap<>();
            if (map == null || map.isEmpty()) {
                continue;
            }
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                String newKey = key.toLowerCase();
                resultMap.put(newKey, map.get(key));
            }
            resultlist.add(resultMap);
        }

        return resultlist;
    }

    public static LocalDateTime string2LocalDateTime(String str,String pattern){
        pattern=str.isEmpty()?"yyyy-MM-dd HH:mm:ss":pattern;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(str,df);
    }

    public static LocalDateTime string2LocalDateTime1(String str,String pattern){
        if (!str.isEmpty())return string2LocalDateTime(str.substring(0,str.length()-2),pattern);
        return null;
    }

}
