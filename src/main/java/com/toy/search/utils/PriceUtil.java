package com.toy.search.utils;

import java.math.BigDecimal;

/**
 * @Author: sunxinyue
 * @Date: 2019-08-19 15:49
 * @Description:
 */
public class PriceUtil {

    /**
     * 厘转元
     *
     * @param li
     * @return
     */
    public static BigDecimal changeLiToYuan(Integer li) {
        if (li == null) {
            return null;
        }
        BigDecimal num = new BigDecimal(li).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN);
        String numStr = num.stripTrailingZeros().toPlainString();

        BigDecimal returnNum = null;
        if (!numStr.contains(".")) {
            // 如果num 不含有小数点,使用stripTrailingZeros()处理时,变成了科学计数法
            returnNum = new BigDecimal(numStr);
        } else {
            if (num.compareTo(BigDecimal.ZERO) == 0) {
                returnNum = BigDecimal.ZERO;
            } else {
                returnNum = num.stripTrailingZeros();
            }
        }
        return returnNum;
    }

    /**
     * 元转厘
     *
     * @param yuan
     * @return
     */
    public static int changeYuanToLi(BigDecimal yuan) {
        if (yuan == null) {
            return 0;
        }
        BigDecimal li = yuan.multiply(new BigDecimal(1000));
        return li.intValue();
    }

    public static void main(String[] args) {
        System.out.println(changeLiToYuan(10000));
    }
}
