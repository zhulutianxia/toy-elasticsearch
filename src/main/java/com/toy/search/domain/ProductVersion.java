package com.toy.search.domain;

/**
 * @Author: weierde
 * @Date: 2020-04-10 13:23
 * @Description:
 */
public class ProductVersion {

    public static final ProductVersion VERSION_5_3_0 = new ProductVersion(5, 3, 0);
    private int majorVer;
    private int minorVer;
    private int miniVer;
    private long timeVer = 0;

    public ProductVersion(int majorVer, int minorVer, int miniVer) {
        this.majorVer = majorVer;
        this.minorVer = minorVer;
        this.miniVer = miniVer;
    }

    public ProductVersion(int majorVer, int minorVer, int miniVer, long timeVer) {
        this(majorVer, minorVer, miniVer);
        this.timeVer = timeVer;
    }

    public static ProductVersion parseProductVersion(String s) throws NumberFormatException {
        try {
            String[] numbers = s.split("\\.");
            if (numbers.length == 3) {
                return new ProductVersion(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));
            } else if (numbers.length == 4) {
                return new ProductVersion(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]), Long.parseLong(numbers[3]));
            }
        } catch (Exception e) {
        }
        throw new NumberFormatException("The format of product version should be 'x.x.x' or 'x.x.x.x'. s is " + s);
    }

    private int compareTo(ProductVersion o) {
        // timeVer的值主要用于区分测试版的版本,版本号大小比较的时候不考虑此数值
        int flag;
        if ((flag = Integer.compare(this.majorVer, o.majorVer)) != 0) {
            return flag;
        } else if ((flag = Integer.compare(this.minorVer, o.minorVer)) != 0) {
            return flag;
        } else if ((flag = Integer.compare(this.miniVer, o.miniVer)) != 0) {
            return flag;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof ProductVersion) {
            return compareTo((ProductVersion) o) == 0;
        } else {
            return false;
        }
    }

    public boolean greaterThan(ProductVersion productVersion) {
        return compareTo(productVersion) > 0;
    }

    public boolean greaterThanOrEqualTo(ProductVersion productVersion) {
        return compareTo(productVersion) >= 0;
    }

    public boolean lessThan(ProductVersion productVersion) {
        return compareTo(productVersion) < 0;
    }

    public boolean lessThanOrEqualTo(ProductVersion productVersion) {
        return compareTo(productVersion) <= 0;
    }

    @Override
    public String toString() {
        String s = majorVer + "." + minorVer + "." + miniVer;
        if (timeVer != 0) {
            s += "." + timeVer;
        }
        return s;
    }
}
