package com.toy.search.dao;

import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @Author: weierde
 * @Date: 2019-12-25 16:34
 * @Description:
 */
public interface BabyMapper {

    @Select("select b.baby_birth_date from t_user u left join t_baby b on FIND_IN_SET(b.baby_id, REPLACE(u.user_baby_ids,';',',')) " +
            "where u.user_id = #{userId} order by baby_birth_date desc limit 1")
    Date getUserBabyBirth(long userId);
}
