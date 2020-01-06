package com.toy.search.domain;

import com.toy.search.constant.Constants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: weierde
 * @Date: 2020-01-06 11:44
 * @Description:
 */
@Data
@Document(indexName = Constants.INDEX_NAME.KEYWORD_INDEX, type = Constants.TOY_INDEX_TYPE_NAME.KEYWORD)
public class Keyword implements Serializable {

    @Id
    private String uuid;
    private String keyword;
    private long depotId;

    public String getUuid() {
        return UUID.randomUUID().toString();
    }
}
