package com.github.benhaixiao.topic.event.node;

import com.github.benhaixiao.topic.node.NodeMeta;

/**
 * @author xiaobenhai
 *
 */
public class NodeMetaEventUtils {

    public static NodeMetaCreateEvent createNodeMetaCreateEvent(String appid,NodeMeta nodeMeta){
        return new NodeMetaCreateEvent(appid,nodeMeta);
    }

    public static NodeMetaDeleteEvent createNodeMetaDeleteEvent(String appid,NodeMeta nodeMeta){
        return new NodeMetaDeleteEvent(appid,nodeMeta);
    }

    public static NodeMetaModifyEvent createNodeMetaModifyEvent(String appid,NodeMeta oldNodeMeta,NodeMeta newNodeMeta){
        return new NodeMetaModifyEvent(appid,oldNodeMeta,newNodeMeta);
    }

}
