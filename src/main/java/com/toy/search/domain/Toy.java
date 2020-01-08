package com.toy.search.domain;

import com.toy.search.constant.Constants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


/**
 * @Author: weierde
 * @Date: 2019-12-06 10:17
 * @Description: 配置完，启动项目会自动创建索引
 */
@Data
@Document(indexName = Constants.INDEX_NAME.TOY_INDEX, type = Constants.TOY_INDEX_TYPE_NAME.TOY)
@Mapping(mappingPath = "/json/toy-mapping.json")
public class Toy implements Serializable {

    @Id
    private String uuid;
    private Long toyId;
    private String toyName;
    private String image;
    private int brandId;
    private String brandName;
    private String brandImg;
    private int price;
    private int rentMoney;
    private int toySize;
    private int minAgeRange;
    private int maxAgeRange;
    private int rentType;
    private String toyTypeIds;
    private String typeName;
    private String abilityIds;
    private String abilityName;
    private long depotId;
    private int stockNum;
    private int rentNum;
    private Date purchaseTime;

    public String getUuid() {
        return UUID.randomUUID().toString();
    }

    public long getPurchaseTime() {
        return purchaseTime == null ? 0 : purchaseTime.getTime();
    }
}
