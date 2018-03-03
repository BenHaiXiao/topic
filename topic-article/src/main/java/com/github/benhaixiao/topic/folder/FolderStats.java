package com.github.benhaixiao.topic.folder;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
@Document(collection = "folder_stats")
public class FolderStats {
    @Field("fdrCnt")
    private long folderCount;
    @Field("tocCnt")
    private long topicCount;

    public long getFolderCount() {
        return folderCount;
    }

    public void setFolderCount(long folderCount) {
        this.folderCount = folderCount;
    }

    public long getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(long topicCount) {
        this.topicCount = topicCount;
    }
}
