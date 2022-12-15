package com.nteport.admin.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalculateUtil {


        public static String percentBigDecimal(BigDecimal preNum, BigDecimal sufNum){
            double result = countDecimal(preNum,sufNum);
            if(result>0){
                return "+"+result+"%";
            }
            if(result<0){
                return result+"%";
            }
            if(result==0){
                return "+"+0+"%";
            }
            return null;
        }
        public static double countDecimal(BigDecimal preNum, BigDecimal sufNum){
            boolean preBoolean = verifyNum(preNum);
            boolean sufBoolean = verifyNum(sufNum);
            //同时为true计算
            if(preBoolean && sufBoolean){
                boolean b = verifyEqual(preNum, sufNum);
                if (b == false){
                    return realCountDecimal(preNum,sufNum);
                }
                if (b){
                    return 0;
                }
            }
            if(preBoolean == false && sufBoolean ==false){
                return 0;
            }
            if(sufBoolean ==false){
                return 100;
            }
            return  0;
        }
        //验证数字是否为零和null
        public static boolean verifyNum(BigDecimal num){
            if(null !=num && num.compareTo(BigDecimal.ZERO)!=0 ){
                return true;
            }
            return false;
        }

        //验证两个数字是否相等
        public static boolean verifyEqual(BigDecimal preNum, BigDecimal sufNum){
            int n = preNum.compareTo(sufNum);
            //比较 -1 小于   0 等于    1 大于
            if(n==0){
                return true;
            }
            return false;
        }
        //真正计算
        public static double realCountDecimal(BigDecimal preNum, BigDecimal sufNum){
            //(前面的数字-后面的数字)/后面的数字*100
            BigDecimal bigDecimal = (preNum.subtract(sufNum)).divide(sufNum,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_UP);
            if (bigDecimal.compareTo(BigDecimal.ZERO) !=0){
                return  bigDecimal.doubleValue();
            }
            return 0;
        }
    public static String percentBigDecimalnot(BigDecimal preNum, BigDecimal sufNum){
        double result = countDecimal(preNum,sufNum);
        if(result>0){
            return "+"+result;
        }
        if(result<0){
            return result+"";
        }
        if(result==0){
            return "+"+0+"";
        }
        return null;
    }
    /**
     * 获取百分率 不含%
     *
     * @param number
     * @param denominator
     * @return eg:33.33
     */
    public static Double getRatioDouble(BigDecimal number, BigDecimal denominator) {
        BigDecimal ratio = BigDecimal.valueOf(0.00);
        if (denominator.doubleValue()!=0) {
            ratio = number.divide(denominator, 4, RoundingMode.HALF_UP);
            ratio = ratio.multiply(BigDecimal.valueOf(100));
            DecimalFormat df = new DecimalFormat("#0.00");
            String format = df.format(ratio);
            ratio = new BigDecimal(format);
        }
        return ratio.doubleValue();
    }


}
