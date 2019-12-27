package com.toy.search.dao;

import com.toy.search.domain.SpecialToy;
import com.toy.search.domain.Toy;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @Author: weierde
 * @Date: 2019-12-05 15:55
 * @Description:
 */
public interface ToyMapper {

    @Select("select t.toy_id toyId, CONCAT(b.brand_name, t.toy_name) toyName, t.toy_tpt_image image, b.brand_name brandName, " +
            "t.toy_price price, t.toy_daily_money rentMoney, t.min_age_range minAgeRange, t.max_age_range maxAgeRange, b.brand_image brandImg, " +
            "t.toy_size_type toySize, t.rent_type rentType, t.brand_id brandId, t.toy_type_ids toyTypeIds, t.ability_ids abilityIds, " +
            "group_concat(DISTINCT y.toy_type_name) typeName, group_concat(DISTINCT a.ability_name) abilityName, d.depot_id depotId, " +
            "d.toy_stock_num stockNum, d.toy_rent_num toyRentNum, date_format(t.purchase_time, '%Y-%m-%d %H:%i:%s') purchaseTime " +
            "from t_toy t left join t_brand b on t.brand_id = b.brand_id " +
            "left join t_toy_type y on FIND_IN_SET(y.toy_type_id, REPLACE(t.toy_type_ids,';',',')) " +
            "LEFT JOIN t_ability a on FIND_IN_SET(a.ability_id, REPLACE(t.ability_ids,';',',')) " +
            "left join tt_toy_depot d on t.toy_id = d.toy_id " +
            "WHERE t.is_publish = 1 " +
            "group by t.toy_id ")
    List<Toy> getToyList();

    @Select("select min_age_range from t_age_range where age_range_id = #{ageRange}")
    Integer getMinAge(String ageRange);

    @Select("select max_age_range from t_age_range where age_range_id = #{ageRange}")
    Integer getMaxAge(String ageRange);

    @Select("select recommend_toy_ids from t_age_range where depot_id = #{depotId} order by age_range_id asc")
    Set<Long> getRecommendToyIds(Long depotId);

    @Select("select * from " +
            "(select t.toy_id from tt_toy_depot d left join t_toy t on t.toy_id = d.toy_id " +
            "where d.depot_id = #{depotId} and t.is_publish = 1 and t.toy_stock_num > 0 ORDER BY t.purchase_time desc, t.toy_id DESC) a " +
            "union " +
            "select * from " +
            "(select t.toy_id from tt_toy_depot d left join t_toy t on t.toy_id = d.toy_id " +
            "where d.depot_id = #{depotId} and t.is_publish = 1 and t.toy_stock_num = 0 ORDER BY t.purchase_time desc, t.toy_id DESC) b " +
            "limit 20")
    List<Long> getLatestToyIds(Long depotId);

    @Select("select t.toy_id from tt_toy_depot d left join t_hottest_toy t on t.toy_id = d.toy_id " +
            "where d.depot_id = #{depotId} ORDER BY t.rank ASC")
    List<Long> getHotToyIds(Long depotId);

    @Select("select toy_id toyId, toy_daily_money toyDailyMoney from t_special_money_toy " +
            "WHERE start_time < NOW() AND end_time > NOW() and depot_id = #{depotId} " +
            "group by toy_id ORDER BY rank ASC, id DESC")
    List<SpecialToy> getSpecialToyIds(Long depotId);
}
