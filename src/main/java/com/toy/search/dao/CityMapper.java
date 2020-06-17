package com.toy.search.dao;

import org.apache.ibatis.annotations.Select;

/**
 * @Author: weierde
 * @Date: 2019-12-24 13:20
 * @Description:
 */
public interface CityMapper {

    @Select("select depot_id from tz_city where city_code = #{cityCode} limit 1")
    Long getDepotId(String cityCode);

    @Select("SELECT mu_city_code FROM t_member_user WHERE user_id = #{userId}")
    String getMemberCityCode(long userId);

    @Select("select city_code from tz_city where city_id = #{cityId} limit 1")
    String getCityCodeByCityId(Long cityId);
}
