package com.github.benhaixiao.topic.post;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "post_index")
public class PostIndex {
    @Id
    private String id;
    @Field("ids")
    private List<String> ids;//盖楼
    @Field("topId")
    private String topId;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

}
