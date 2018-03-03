package com.github.benhaixiao.topic.event.node;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.node.NodeMeta;

/**
 * @author xiaobenhai
 *
 */
public class NodeMetaDeleteEvent extends Event {
    private String appid;
    private NodeMeta nodeMeta;

    public NodeMetaDeleteEvent() {
        this.type = Type.NODE_META_DELETE;
    }

    public NodeMetaDeleteEvent(String appid, NodeMeta nodeMeta) {
        this.type = Type.NODE_META_DELETE;
        this.appid = appid;
        this.nodeMeta = nodeMeta;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public NodeMeta getNodeMeta() {
        return nodeMeta;
    }

    public void setNodeMeta(NodeMeta nodeMeta) {
        this.nodeMeta = nodeMeta;
    }
}
