package com.toy.search.utils;

import com.toy.search.constant.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: weierde
 * @Date: 2019-12-25 13:56
 * @Description:
 */
public class AgeRangeUtil {

    /**
     * 根据最小月龄和最大月龄生成月龄段字符串，如“0-6个月”、“1岁半-2岁”、“3岁以上”等
     *
     * @param minAgeRange
     * @param maxAgeRange
     * @return
     */
    public static String getAgeRangeString(int minAgeRange, int maxAgeRange) {
        if (minAgeRange < 0 || minAgeRange >= maxAgeRange) {
            return "非正常年龄段";
        }
        String arName;
        if (minAgeRange < 12 || minAgeRange % 6 != 0 ||
                (maxAgeRange < Constants.MAX_AGE_LIMIT && maxAgeRange % 6 != 0)) {
            arName = minAgeRange + StringUtils.EMPTY;
            if (maxAgeRange >= Constants.MAX_AGE_LIMIT) {
                arName += Constants.HAN_ZI_GE_YUE + Constants.HAN_ZI_YI_SHANG;
            } else {
                arName += Constants.RANGE_SEPARATOR + maxAgeRange + Constants.HAN_ZI_GE_YUE;
            }
        } else {
            arName = getAgeNameByYear(minAgeRange);
            if (maxAgeRange >= Constants.MAX_AGE_LIMIT) {
                arName += Constants.HAN_ZI_YI_SHANG;
            } else {
                arName += Constants.RANGE_SEPARATOR + getAgeNameByYear(maxAgeRange);
            }
        }
        return arName;
    }

    private static String getAgeNameByYear(int month) {
        if (month <= 0) {
            return 0 + Constants.HAN_ZI_SUI;
        } else if (month % 12 == 0) {
            return month / 12 + Constants.HAN_ZI_SUI;
        } else {
            String name = month / 12 + Constants.HAN_ZI_SUI;
            if (month % 12 == 6) {
                name += Constants.HAN_ZI_BAN;
            }
            return name;
        }
    }
}
