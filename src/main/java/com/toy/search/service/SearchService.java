package com.toy.search.service;

import com.toy.search.param.SearchParam;
import com.toy.search.utils.ReturnJsonUtil;

/**
 * @Author: weierde
 * @Date: 2019-12-05 15:54
 * @Description:
 */
public interface SearchService {

    ReturnJsonUtil searchWord(SearchParam param, long userId);
}
