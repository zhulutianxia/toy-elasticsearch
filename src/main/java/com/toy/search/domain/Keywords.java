package com.toy.search.domain;

import com.toy.search.constant.Constants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: weierde
 * @Date: 2020-01-06 11:44
 * @Description:
 */
@Data
@Document(indexName = Constants.INDEX_NAME.KEYWORDS_INDEX, type = Constants.TOY_INDEX_TYPE_NAME.KEYWORDS)
@Setting(settingPath = "/json/keywords-setting.json")
public class Keywords implements Serializable {

    @Id
    private String uuid;
    @MultiField(
            mainField = @Field(type = FieldType.Keyword),
            otherFields = {
                    @InnerField(type = FieldType.Text, suffix = "ik", analyzer = "ik_max_word", searchAnalyzer = "ik_max_word"),
                    @InnerField(type = FieldType.Text, suffix = "ik_pinyin", analyzer = "ik_pinyin_analyzer", searchAnalyzer = "ik_pinyin_analyzer"),
                    @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin_analyzer", searchAnalyzer = "pinyin_analyzer")
            }
    )
    private String keywords;
    private long depotId;

    public String getUuid() {
        return UUID.randomUUID().toString();
    }
}
