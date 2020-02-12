package com.toy.search.dao;

import com.toy.search.domain.Depot;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: weierde
 * @Date: 2020-02-12 10:52
 * @Description:
 */
public interface DepotMapper {

    @Select("select depot_id depotId, name, address_city_id addressCityId, address_city_code addressCityCode " +
            "from td_depot where address_city_code = #{cityCode}")
    Depot getDepot(String cityCode);
}
