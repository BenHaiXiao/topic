package com.github.benhaixiao.topic.app;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "app_stats")
public class AppStats {
    @Field("tocCnt")
    private long topicCount;
    @Field("fdrCnt")
    private long folderCount;

    public long getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(long topicCount) {
        this.topicCount = topicCount;
    }

    public long getFolderCount() {
        return folderCount;
    }

    public void setFolderCount(long folderCount) {
        this.folderCount = folderCount;
    }
}
