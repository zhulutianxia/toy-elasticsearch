package com.toy.search.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: weierde
 * @Date: 2019-12-24 11:58
 * @Description:
 */
@Setter
@Getter
public class SearchParam {

    /** 公参 */
    private String av;
    private String cv;
    private String client;

    private String keyword;
    private String cityCode = "010";
    private String ageRange;
    private String ability;
    private String brand;
    private String toySize;
    private String toyType;
    private Integer toySort = 0;
    private Integer rentType;
    private Integer pageNumber = 1;
    private Integer pageSize = 20;
}
