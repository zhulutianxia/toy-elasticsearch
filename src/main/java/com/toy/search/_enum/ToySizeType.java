package com.toy.search._enum;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: weierde
 * @Date: 2019-12-25 13:53
 * @Description:
 */
public enum ToySizeType {

    /**
     * “大”、“中”、“小”用4、2、1（2^2/2^1/2^0，即不同位置的bit）这几个值来表示，目前主要用于会员订单下单时的“一大一中一小”规则。
     * 如果选择的玩具的总“价值”不超过7(4+2+1)，则允许下单。所以“一大一中一小(7)”、“一大两小(6)”、“三中(6)”、“两中一小(5)”、“一中两小(4)”、“三小(3)”都是被允许的，
     * 而“两大一中(10)”、“两大一小(9)”、“一大两中(8)”都是不被允许的。
     */
    SMALL(1, "S", "小", "/ts/s.png"),
    MEDIUM(2, "M", "中", "/ts/m.png"),
    LARGE(4, "L", "大", "/ts/l.png"),
    EXTRA_LARGE(8, "XL", "豪华", "/ts/xl.png");

    public static final int MEMBER_ORDER_TOTAL_VALUE = SMALL.value + MEDIUM.value + LARGE.value;

    private int value;
    private String engName;
    private String chnName;
    private String img;

    ToySizeType(int value, String engName, String name, String img) {
        this.value = value;
        this.engName = engName;
        this.chnName = name;
        this.img = img;
    }

    public int getValue() {
        return value;
    }

    public String getEngName() {
        return engName;
    }

    public String getChnName() {
        return chnName;
    }

    public String getImg() {
        return img;
    }

    public static ToySizeType getToySizeTypeByEngName(String name) {
        if (StringUtils.isEmpty(name)) {
            return EXTRA_LARGE;
        }
        switch (name.toUpperCase()) {
            case "S":
                return SMALL;
            case "M":
                return MEDIUM;
            case "L":
                return LARGE;
            default:
                return EXTRA_LARGE;
        }
    }

    public static ToySizeType getToySizeTypeByOrigin(int ordinal) {
        if (ordinal < 0 || ordinal >= ToySizeType.values().length) {
            return EXTRA_LARGE;
        }
        return ToySizeType.values()[ordinal];
    }

    public static ToySizeType getMemberCanSelectSizeByValue(int value) throws Exception {
        if (value / LARGE.value > 0) {
            return LARGE;
        } else if (value / MEDIUM.value > 0) {
            return MEDIUM;
        } else if (value / SMALL.value > 0) {
            return SMALL;
        } else {
            throw new Exception();
        }
    }
}
