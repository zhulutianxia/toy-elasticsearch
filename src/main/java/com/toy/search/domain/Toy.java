package com.toy.search.domain;

import com.toy.search.constant.Constants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.Date;


/**
 * @Author: weierde
 * @Date: 2019-12-06 10:17
 * @Description: 配置完，启动项目会自动创建索引
 */
@Data
@Document(indexName = Constants.INDEX_NAME.TOY_INDEX, type = Constants.TOY_INDEX_TYPE_NAME.TOY)
@Setting(settingPath = "/json/toy-setting.json")
public class Toy implements Serializable {

    @Id
    @Field(type = FieldType.Integer)
    private Integer id;
    @Field(type = FieldType.Long)
    private Long toyId;
    @MultiField(
            mainField = @Field(type = FieldType.Keyword),
            otherFields = {
                    @InnerField(type = FieldType.Text, suffix = "ik", analyzer = "ik_max_word", searchAnalyzer = "ik_max_word"),
                    @InnerField(type = FieldType.Text, suffix = "ik_pinyin", analyzer = "ik_pinyin_analyzer", searchAnalyzer = "ik_pinyin_analyzer"),
                    @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin_analyzer", searchAnalyzer = "pinyin_analyzer")
            }
    )
    private String toyName;
    @Field(type = FieldType.Text)
    private String image;
    @Field(type = FieldType.Integer)
    private Integer brandId;
    @MultiField(
            mainField = @Field(type = FieldType.Keyword),
            otherFields = {
                    @InnerField(type = FieldType.Text, suffix = "ik", analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
                    @InnerField(type = FieldType.Text, suffix = "ik_pinyin", analyzer = "ik_pinyin_analyzer", searchAnalyzer = "ik_pinyin_analyzer"),
                    @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin_analyzer", searchAnalyzer = "pinyin_analyzer")
            }
    )
    private String brandName;
    @Field(type = FieldType.Text)
    private String brandImg;
    @Field(type = FieldType.Integer)
    private Integer price;
    @Field(type = FieldType.Integer)
    private Integer rentMoney;
    @Field(type = FieldType.Integer)
    private Integer toySize;
    @Field(type = FieldType.Integer)
    private Integer minAgeRange;
    @Field(type = FieldType.Integer)
    private Integer maxAgeRange;
    @Field(type = FieldType.Integer)
    private Integer rentType;
    @Field(type = FieldType.Text)
    private String toyTypeIds;
    @MultiField(
            mainField = @Field(type = FieldType.Keyword),
            otherFields = {
                    @InnerField(type = FieldType.Text, suffix = "ik", analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
                    @InnerField(type = FieldType.Text, suffix = "ik_pinyin", analyzer = "ik_pinyin_analyzer", searchAnalyzer = "ik_pinyin_analyzer"),
                    @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin_analyzer", searchAnalyzer = "pinyin_analyzer")
            }
    )
    private String typeName;
    @Field(type = FieldType.Text)
    private String abilityIds;
    @MultiField(
            mainField = @Field(type = FieldType.Keyword),
            otherFields = {
                    @InnerField(type = FieldType.Text, suffix = "ik", analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
                    @InnerField(type = FieldType.Text, suffix = "ik_pinyin", analyzer = "ik_pinyin_analyzer", searchAnalyzer = "ik_pinyin_analyzer"),
                    @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin_analyzer", searchAnalyzer = "pinyin_analyzer")
            }
    )
    private String abilityName;
    @Field(type = FieldType.Long)
    private Long depotId;
    @Field(type = FieldType.Integer)
    private Integer stockNum;
    @Field(type = FieldType.Integer)
    private Integer rentNum;
    @Field(type = FieldType.Long)
    private Long publishTime;
    @Field(type = FieldType.Date)
    private Date updateTime;
}
