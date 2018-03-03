package com.github.benhaixiao.topic.node;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author xiaobenhai
 *
 */
public class NodeStats {
    @Field("fowCnt")
    private long followCount;
    @Field("acmCnt")
    private long acclaimCount;
    @Field("potCnt")
    private long postCount;
    @Field("breCnt")
    private long browseCount;
    @Field("sheCnt")
    private long shareCount;

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
    }

    public long getAcclaimCount() {
        return acclaimCount;
    }

    public void setAcclaimCount(long acclaimCount) {
        this.acclaimCount = acclaimCount;
    }

    public long getPostCount() {
        return postCount;
    }

    public void setPostCount(long postCount) {
        this.postCount = postCount;
    }

    public long getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(long browseCount) {
        this.browseCount = browseCount;
    }

    public long getShareCount() {
        return shareCount;
    }

    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
    }

    private static final NodeStats emptyStats = new NodeStats();
    public static NodeStats EmptyStats() {
        return emptyStats;
    }
}
