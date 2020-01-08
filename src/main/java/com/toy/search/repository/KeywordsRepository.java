package com.toy.search.repository;

import com.toy.search.domain.Keywords;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: weierde
 * @Date: 2019-12-06 10:38
 * @Description:
 */
@Component
public interface KeywordsRepository extends ElasticsearchRepository<Keywords, String> {
}
