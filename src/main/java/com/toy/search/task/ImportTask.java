package com.toy.search.task;

import com.toy.search.dao.ToyMapper;
import com.toy.search.domain.Toy;
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

    @Async
    @Scheduled(cron = "0 0 2 ? * *")
    public void importTravel() {
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
}
