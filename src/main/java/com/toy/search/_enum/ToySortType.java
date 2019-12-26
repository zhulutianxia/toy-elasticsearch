package com.toy.search._enum;

/**
 * @Author: weierde
 * @Date: 2019-12-25 13:52
 * @Description:
 */
public enum ToySortType {
    NORMAL_SORT(0, "综合排序"),
    POPULAR_SORT(1, "人气最高"),
    LATEST_SORT(2, "最新上架"),
    PRICE_MAX_SORT(3, "价格最高"),
    PRICE_MIN_SORT(4, "价格最低");

    private int type;
    private String name;

    ToySortType(int type, String value) {
        this.type = type;
        this.name = value;
    }

    private static ToySortType[] RETURN_TOY_SORT_TYPE_ARR = {NORMAL_SORT, POPULAR_SORT, LATEST_SORT,
            PRICE_MAX_SORT, PRICE_MIN_SORT};

    public static ToySortType getToySortType(int origin) {
        if (origin < 0 || origin > ToySortType.values().length) {
            return NORMAL_SORT;
        }
        return ToySortType.values()[origin];
    }

    public static ToySortType[] getReturnToySortTypeArr() {
        return RETURN_TOY_SORT_TYPE_ARR;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
