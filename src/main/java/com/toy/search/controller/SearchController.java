package com.toy.search.controller;

import com.toy.search.param.SearchParam;
import com.toy.search.service.SearchService;
import com.toy.search.utils.ReturnJsonUtil;
import com.toy.search.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: weierde
 * @Date: 2019-12-05 15:53
 * @Description:
 */
@RestController
@RequestMapping("es/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索数据
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping("list")
    public ReturnJsonUtil searchWord(HttpServletRequest request, SearchParam param) {
        long userId = TokenUtil.getUserIdFromCookie(request);
        return searchService.searchWord(param, userId);
    }

    /**
     * 搜索推荐关键词
     *
     * @param cityCode
     * @param keyword
     * @return
     */
    @PostMapping("recommendKeyword")
    public ReturnJsonUtil recommendKeyword(@RequestParam(defaultValue = "010") String cityCode, String keyword) {
        return searchService.recommendKeyword(cityCode, keyword);
    }

    /**
     * 删除toy下数据
     *
     * @param toyId
     * @return
     */
    @PostMapping("deleteToyDocument")
    public ReturnJsonUtil deleteToyDocument(Long toyId) {
        return searchService.deleteToyDocument(toyId);
    }
}
