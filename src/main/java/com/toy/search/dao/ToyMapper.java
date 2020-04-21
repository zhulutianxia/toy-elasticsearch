package com.toy.search.dao;

import com.toy.search.domain.Keywords;
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

    @Select("select d.id, t.toy_id toyId, CONCAT(b.brand_name, t.toy_name) toyName, t.toy_tpt_image image, b.brand_name brandName, " +
            "t.toy_price price, t.toy_daily_money rentMoney, t.min_age_range minAgeRange, t.max_age_range maxAgeRange, b.brand_image brandImg, " +
            "t.toy_size_type toySize, ORD(t.rent_type) rentType, t.brand_id brandId, t.toy_type_ids toyTypeIds, t.ability_ids abilityIds, " +
            "group_concat(DISTINCT y.toy_type_name) typeName, group_concat(DISTINCT a.ability_name) abilityName, d.depot_id depotId, " +
            "d.toy_stock_num stockNum, d.toy_rent_num rentNum, unix_timestamp(t.publish_time) publishTime, d.update_time updateTime " +
            "from tt_toy_depot d inner join t_toy t on t.toy_id = d.toy_id " +
            "left join t_brand b on t.brand_id = b.brand_id " +
            "left join t_toy_type y on FIND_IN_SET(y.toy_type_id, REPLACE(t.toy_type_ids,';',',')) " +
            "LEFT JOIN t_ability a on FIND_IN_SET(a.ability_id, REPLACE(t.ability_ids,';',',')) " +
            "WHERE t.is_publish = 1 " +
            "group by d.toy_id,d.depot_id order by d.id ")
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

    @Select("select distinct t.toy_name keywords, d.depot_id depotId " +
            "from tt_toy_depot d inner join t_toy t on t.toy_id = d.toy_id " +
            "WHERE t.is_publish = 1 group by d.toy_id,d.depot_id " +
            "union " +
            "select distinct b.brand_name keywords, d.depot_id depotId " +
            "from tt_toy_depot d inner join t_toy t on t.toy_id = d.toy_id " +
            "left join t_brand b on t.brand_id = b.brand_id " +
            "WHERE t.is_publish = 1 group by d.toy_id,d.depot_id " +
            "union " +
            "select distinct y.toy_type_name keywords, d.depot_id depotId " +
            "from tt_toy_depot d inner join t_toy t on t.toy_id = d.toy_id " +
            "left join t_toy_type y on FIND_IN_SET(y.toy_type_id, REPLACE(t.toy_type_ids,';',',')) " +
            "WHERE t.is_publish = 1 group by d.toy_id,d.depot_id " +
            "union " +
            "select distinct a.ability_name keywords, d.depot_id depotId " +
            "from tt_toy_depot d inner join t_toy t on t.toy_id = d.toy_id " +
            "LEFT JOIN t_ability a on FIND_IN_SET(a.ability_id, REPLACE(t.ability_ids,';',',')) " +
            "WHERE t.is_publish = 1 group by d.toy_id,d.depot_id ")
    List<Keywords> getToyKeyword();

    @Select("SELECT toy_id FROM t_order_toy WHERE retention_num < toy_num and " +
            "order_id IN (SELECT order_id FROM t_order WHERE user_id = #{userId} AND status IN (2,3,4,5,6) AND is_delete = 0 ORDER BY create_time) " +
            "UNION " +
            "SELECT toy_id FROM t_suite_toy_relation r " +
            "left join t_order o on r.suite_id = o.suite_id " +
            "WHERE o.user_id = #{userId} AND o.status IN (2,3,4,5,6) AND o.is_delete = 0 and o.suite_id > 0 " +
            "UNION " +
            "SELECT toy_id FROM t_order_toy WHERE retention_num < toy_num and " +
            "order_id = (SELECT order_id FROM t_times_card_toy_order WHERE user_id = #{userId} AND status IN (2,3,4) ORDER BY create_time DESC LIMIT 1) ")
    List<Long> getUserInRentToyIds(long userId);
}
