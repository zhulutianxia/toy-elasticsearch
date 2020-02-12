package com.toy.search.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: weierde
 * @Date: 2020-02-12 10:50
 * @Description:
 */
@Getter
@Setter
public class Depot {

    private Long depotId;
    private String name;
    private Integer addressCityId;
    private String addressCityCode;
}
