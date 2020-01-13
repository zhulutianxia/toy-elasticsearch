package com.toy.search.repository;

import com.toy.search.domain.Toy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: weierde
 * @Date: 2019-12-06 10:38
 * @Description:
 */
@Component
public interface SearchRepository extends ElasticsearchRepository<Toy, Integer> {
}
