package com.toy.search.task;

import com.toy.search.dao.ToyMapper;
import com.toy.search.domain.Keywords;
import com.toy.search.domain.Toy;
import com.toy.search.repository.KeywordsRepository;
import com.toy.search.repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: weierde
 * @Date: 2019-12-07 13:45
 * @Description: 定时更新索引库
 */
@Component
@EnableAsync
@Slf4j
public class ImportTask {

    @Autowired
    private ToyMapper toyMapper;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private KeywordsRepository keywordsRepository;

    @Async
    @Scheduled(cron = "0 7 0/1 * * ?")
    public void importToy() {
        try {
            log.info("开始导入玩具数据...");
            long startTime = System.currentTimeMillis();
            Iterable<Toy> data = searchRepository.saveAll(toyMapper.getToyList());
            if (data != null) {
                long endTime = System.currentTimeMillis() - startTime;
                log.info("【success】玩具数据导入完毕！耗时={}ms", endTime);
            }
        } catch (Exception e) {
            log.error("【error】玩具数据导入失败！", e);
            e.printStackTrace();
        }
    }

    @Async
    @Scheduled(cron = "30 0 2 ? * *")
    public void importKeyword() {
        try {
            log.info("开始导入玩具搜索关键词...");
            long startTime = System.currentTimeMillis();
            keywordsRepository.deleteAll();
            Iterable<Keywords> data = keywordsRepository.saveAll(toyMapper.getToyKeyword());
            if (data != null) {
                long endTime = System.currentTimeMillis() - startTime;
                log.info("【success】玩具搜索关键词数据导入完毕！耗时={}ms", endTime);
            }
        } catch (Exception e) {
            log.error("【error】玩具搜索关键词数据导入失败！", e);
            e.printStackTrace();
        }
    }
}
