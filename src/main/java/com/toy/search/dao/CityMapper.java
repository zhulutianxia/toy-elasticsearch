package com.toy.search.dao;

import org.apache.ibatis.annotations.Select;

/**
 * @Author: weierde
 * @Date: 2019-12-24 13:20
 * @Description:
 */
public interface CityMapper {

    @Select("select depot_id from tz_city where city_code = #{cityCode} limit 1")
    Long getDepotIdByNormal(String cityCode);

    @Select("select depot_id from tz_city where city_code = (SELECT mu_city_code FROM t_member_user WHERE user_id = #{userId}) limit 1")
    Long getDepotIdByMember(long userId);
}
