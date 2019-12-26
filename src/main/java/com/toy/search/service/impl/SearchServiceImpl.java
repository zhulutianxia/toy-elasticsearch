package com.toy.search.service.impl;

import com.toy.search._enum.ResultEnum;
import com.toy.search._enum.ToySizeType;
import com.toy.search._enum.ToySortType;
import com.toy.search.constant.Constants;
import com.toy.search.dao.BabyMapper;
import com.toy.search.dao.CityMapper;
import com.toy.search.dao.ToyMapper;
import com.toy.search.domain.SpecialToy;
import com.toy.search.domain.Toy;
import com.toy.search.param.SearchParam;
import com.toy.search.repository.SearchRepository;
import com.toy.search.service.SearchService;
import com.toy.search.utils.AgeRangeUtil;
import com.toy.search.utils.DateUtil;
import com.toy.search.utils.ReturnJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: weierde
 * @Date: 2019-12-05 15:54
 * @Description:
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private ToyMapper toyMapper;

    @Autowired
    private BabyMapper babyMapper;

    @Override
    public ReturnJsonUtil searchWord(SearchParam param, long userId) {
        long start = System.currentTimeMillis();
        Map<String, Object> result = new HashMap<>(3);
        try {

            Long depotId = cityMapper.getDepotId(param.getCityCode());

            // 构建查询条件
            BoolQueryBuilder boolQueryBuilder = buildQuery(param, depotId);

            // 构建排序
            List<ScriptSortBuilder> sortBuilderList = buildQuerySort(param.getToySort(), userId);

            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            queryBuilder.withIndices(Constants.INDEX_NAME.TOY_INDEX)
                    .withTypes(Constants.TOY_INDEX_TYPE_NAME.TOY)
                    .withQuery(boolQueryBuilder)
                    .withPageable(PageRequest.of(param.getPageNumber() - 1, param.getPageSize()));

            // 插入排序规则
            if (StringUtils.isNotBlank(param.getKeyword())) {
                queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
            }
            sortBuilderList.forEach(queryBuilder::withSort);

            Page<Toy> page = searchRepository.search(queryBuilder.build());

            // 构建toy
            List<Map<String, Object>> toyList = buildToy(page.getContent(), depotId);

            result.put("toyList", toyList);
            result.put("pageNumber", param.getPageNumber());
            result.put("totalPages", page.getTotalPages());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ReturnJsonUtil.error(ResultEnum.SERVER_ERROR);
        }
        log.info("耗时--------------{}", System.currentTimeMillis() - start);
        return ReturnJsonUtil.success(result);
    }

    /**
     * 构建toy
     *
     * @param toyList
     * @param depotId
     * @return
     */
    private List<Map<String, Object>> buildToy(List<Toy> toyList, Long depotId) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(toyList)) {
            Set<Long> recommendToyIds = toyMapper.getRecommendToyIds(depotId);
            List<Long> latestToyIds = toyMapper.getLatestToyIds(depotId);
            List<Long> hotToyIds = toyMapper.getHotToyIds(depotId);
            List<SpecialToy> specialToyIds = toyMapper.getSpecialToyIds(depotId);
            toyList.forEach(toy -> {
                Map<String, Object> map = new HashMap<>(13);
                map.put("toyId", toy.getToyId());
                map.put("toyName", toy.getToyName());
                map.put("image", Constants.CDN_IMG_URL_ONLINE + toy.getImage());
                map.put("price", toy.getPrice());
                map.put("rentMoney", toy.getRentMoney());
                map.put("toySize", ToySizeType.getToySizeTypeByOrigin(toy.getToySize()).getEngName());
                map.put("rentType", toy.getRentType());
                map.put("ageRangeString", AgeRangeUtil.getAgeRangeString(toy.getMinAgeRange(), toy.getMaxAgeRange()));
                map.put("isLatest", latestToyIds.contains(toy.getToyId()));
                map.put("isRecommend", recommendToyIds.contains(toy.getToyId()));
                map.put("isHot", hotToyIds.contains(toy.getToyId()));
                if (CollectionUtils.isNotEmpty(specialToyIds)) {
                    for (SpecialToy specialToy : specialToyIds) {
                        Long specialToyId = specialToy.getToyId();
                        if (toy.getToyId().equals(specialToyId)) {
                            map.put("isSpecialMoney", true);
                            map.put("specialMoney", specialToy.getToyDailyMoney());
                            break;
                        } else {
                            map.put("isSpecialMoney", false);
                        }
                    }
                }
                map.put("stockNum", toy.getStockNum());
                map.put("rentNum", toy.getRentNum());
                map.put("purchaseTime", DateUtil.toStrings(new Date(toy.getPurchaseTime())));
                list.add(map);
            });
        }
        return list;
    }

    private List<ScriptSortBuilder> buildQuerySort(Integer toySort, long userId) {
        List<ScriptSortBuilder> sortBuilderList = new ArrayList<>();
        sortBuilderList.add(SortBuilders.scriptSort(new Script("doc['stockNum'].value > 0 ? 1 : 0"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC));

        if (ToySortType.NORMAL_SORT.getType() == toySort) {
            Date babyBirth = babyMapper.getUserBabyBirth(userId);
            if (babyBirth != null) {
                int monthAge = DateUtil.getMonthAge(babyBirth.getTime());
                Script script = new Script("doc['minAgeRange'].value <= " + monthAge + " ? 1 : 0 + doc['maxAgeRange'].value >= " + monthAge + "? 1 : 0");
                sortBuilderList.add(SortBuilders.scriptSort(script, ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC));
            }
            sortBuilderList.add(SortBuilders.scriptSort(new Script("doc['rentMoney'].value"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.ASC));
            sortBuilderList.add(SortBuilders.scriptSort(new Script("doc['toyId'].value"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC));

        } else if (ToySortType.POPULAR_SORT.getType() == toySort) {
            sortBuilderList.add(SortBuilders.scriptSort(new Script("doc['rentNum'].value"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC));

        } else if (ToySortType.LATEST_SORT.getType() == toySort) {
            sortBuilderList.add(SortBuilders.scriptSort(new Script("doc['purchaseTime'].value"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC));

        } else if (ToySortType.PRICE_MAX_SORT.getType() == toySort) {
            sortBuilderList.add(SortBuilders.scriptSort(new Script("doc['rentMoney'].value"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC));

        } else if (ToySortType.PRICE_MIN_SORT.getType() == toySort) {
            sortBuilderList.add(SortBuilders.scriptSort(new Script("doc['rentMoney'].value"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.ASC));
        }

        return sortBuilderList;
    }

    /**
     * 构建查询条件
     *
     * @param param
     * @param depotId
     * @return
     */
    private BoolQueryBuilder buildQuery(SearchParam param, Long depotId) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("depotId", depotId));

        // 关键字搜索
        if (StringUtils.isNotBlank(param.getKeyword())) {
            MultiMatchQueryBuilder queryBuilder1 = QueryBuilders.multiMatchQuery(param.getKeyword(), "toyName", "brandName", "typeName", "abilityName");
            boolQueryBuilder.must(queryBuilder1);
        }
        // 年龄筛选
        if (StringUtils.isNotBlank(param.getAgeRange())) {
            String[] ageRanges = param.getAgeRange().split(",");
            BoolQueryBuilder queryBuilder2 = QueryBuilders.boolQuery();
            for (String ageRange : ageRanges) {
                Integer minAge = toyMapper.getMinAge(ageRange);
                Integer maxAge = toyMapper.getMaxAge(ageRange);
                queryBuilder2.should(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.rangeQuery("minAgeRange").gte(minAge))
                        .filter(QueryBuilders.rangeQuery("minAgeRange").lte(maxAge)))
                        .should(QueryBuilders.boolQuery()
                                .filter(QueryBuilders.rangeQuery("maxAgeRange").gte(minAge))
                                .filter(QueryBuilders.rangeQuery("maxAgeRange").lte(maxAge)));
            }
            boolQueryBuilder.must(queryBuilder2);
        }
        // 品牌筛选
        if (StringUtils.isNotBlank(param.getBrand())) {
            String[] brands = param.getBrand().split(",");
            BoolQueryBuilder queryBuilder3 = QueryBuilders.boolQuery();
            for (String brand : brands) {
                queryBuilder3.should(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.matchQuery("brandId", brand)));
            }
            boolQueryBuilder.must(queryBuilder3);
        }
        // 类别筛选
        if (StringUtils.isNotBlank(param.getToyType())) {
            String[] types = param.getToyType().split(",");
            BoolQueryBuilder queryBuilder4 = QueryBuilders.boolQuery();
            for (String typeId : types) {
                queryBuilder4.should(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.matchQuery("toyTypeIds", typeId)))
                        .should(QueryBuilders.boolQuery()
                                .filter(QueryBuilders.wildcardQuery("toyTypeIds", "*;" + typeId + ";*")))
                        .should(QueryBuilders.boolQuery()
                                .filter(QueryBuilders.wildcardQuery("toyTypeIds", "*;" + typeId + "*")))
                        .should(QueryBuilders.boolQuery()
                                .filter(QueryBuilders.wildcardQuery("toyTypeIds", "*" + typeId + ";*")));
            }
            boolQueryBuilder.must(queryBuilder4);
        }
        // 能力筛选
        if (StringUtils.isNotBlank(param.getAbility())) {
            String[] abilitys = param.getAbility().split(",");
            BoolQueryBuilder queryBuilder5 = QueryBuilders.boolQuery();
            for (String ability : abilitys) {
                queryBuilder5.should(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.matchQuery("abilityIds", ability)))
                        .should(QueryBuilders.boolQuery()
                                .filter(QueryBuilders.wildcardQuery("abilityIds", "*;" + ability + ";*")))
                        .should(QueryBuilders.boolQuery()
                                .filter(QueryBuilders.wildcardQuery("abilityIds", "*;" + ability + "*")))
                        .should(QueryBuilders.boolQuery()
                                .filter(QueryBuilders.wildcardQuery("abilityIds", "*" + ability + ";*")));
            }
            boolQueryBuilder.must(queryBuilder5);
        }
        // 尺寸筛选
        if (StringUtils.isNotBlank(param.getToySize())) {
            String[] sizes = param.getToySize().split(",");
            BoolQueryBuilder queryBuilder6 = QueryBuilders.boolQuery();
            for (String size : sizes) {
                queryBuilder6.should(QueryBuilders.boolQuery()
                        .filter(QueryBuilders.matchQuery("toySize", size)));
            }
            boolQueryBuilder.must(queryBuilder6);
        }
        // 配送方式筛选
        if (param.getRentType() != null) {
            BoolQueryBuilder queryBuilder7 = QueryBuilders.boolQuery();
            queryBuilder7.should(QueryBuilders.boolQuery().filter(QueryBuilders.matchQuery("rentType", param.getRentType())));
            boolQueryBuilder.must(queryBuilder7);
        }

        return boolQueryBuilder;
    }
}
