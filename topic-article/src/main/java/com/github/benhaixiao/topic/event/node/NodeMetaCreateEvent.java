package com.github.benhaixiao.topic.event.node;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.node.NodeMeta;

/**
 * @author xiaobenhai
 *
 */
public class NodeMetaCreateEvent extends Event {
    private String appid;
    private NodeMeta nodeMeta;

    public NodeMetaCreateEvent() {
        this.type = Type.NODE_META_CREATE;
    }

    public NodeMetaCreateEvent(String appid, NodeMeta nodeMeta) {
        this.type = Type.NODE_META_CREATE;
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
