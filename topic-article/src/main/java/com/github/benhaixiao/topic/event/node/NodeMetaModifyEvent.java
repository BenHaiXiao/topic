package com.github.benhaixiao.topic.event.node;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.node.NodeMeta;

/**
 * @author xiaobenhai
 *
 */
public class NodeMetaModifyEvent extends Event {
    private String appid;
    private NodeMeta oldNodeMeta;
    private NodeMeta newNodeMeta;

    public NodeMetaModifyEvent() {
        this.type = Type.NODE_META_MODIFY;
    }

    public NodeMetaModifyEvent(String appid, NodeMeta oldNodeMeta, NodeMeta newNodeMeta) {
        this.type = Type.NODE_META_MODIFY;
        this.appid = appid;
        this.oldNodeMeta = oldNodeMeta;
        this.newNodeMeta = newNodeMeta;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public NodeMeta getOldNodeMeta() {
        return oldNodeMeta;
    }

    public void setOldNodeMeta(NodeMeta oldNodeMeta) {
        this.oldNodeMeta = oldNodeMeta;
    }

    public NodeMeta getNewNodeMeta() {
        return newNodeMeta;
    }

    public void setNewNodeMeta(NodeMeta newNodeMeta) {
        this.newNodeMeta = newNodeMeta;
    }
}
