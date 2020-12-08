package com.toy.search.dao;

import com.toy.search.domain.Depot;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: weierde
 * @Date: 2020-02-12 10:52
 * @Description:
 */
public interface DepotMapper {

    @Select("select depot_id depotId, name, address_city_id addressCityId, address_city_code addressCityCode, can_onsite canOnsite " +
            "from td_depot where address_city_code = #{cityCode}")
    Depot getDepot(String cityCode);

    @Select("select address_city_code from td_depot")
    List<String> wgetDepotCityCodeList();
}
