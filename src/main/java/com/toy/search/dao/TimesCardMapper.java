package com.toy.search.dao;

import com.toy.search.domain.TimesCardValueType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: weierde
 * @Date: 2020-04-21 14:33
 * @Description:
 */
public interface TimesCardMapper {

    @Select("select c.type_ids typeIds, c.brand_ids brandIds, c.toy_sizes toySizes, c.rent_type rentType " +
            "from t_member_user o left join t_times_card_value_type c on o.times_card_value_type = c.times_card_value_type " +
            "where find_in_set(#{depotId}, c.depot_ids) and o.user_id = #{userId} ")
    TimesCardValueType getTimesCardValueType(@Param("userId") long userId, @Param("depotId") Long depotId);
}
