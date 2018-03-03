package com.github.benhaixiao.topic.app;

import com.github.benhaixiao.topic.domain.Tag;
import com.github.benhaixiao.topic.shared.TokenType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "app_config")
public class AppConfig {
    @Field("appid")
    private String appid;
    @Field("appSecret")
    private String appSecret;
    @Field("tokenType")
    private TokenType tokenType;
    @Field("name")
    private String name;
    @Field("tags")
    private List<Tag> tags;
    @Field("desc")
    private String description;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
