package com.github.benhaixiao.topic.node;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 *
 *         0代表目录（Folder）、1代表话题（Article）、2代表评论（Post）、3代表关注（follow）、4代表点赞（acclaim）
 */

public enum  NodeType {
    DIR(0), ARTICLE(1), POST(2), FOLLOW(3), ACCLAIM(4), UNKONWN(99);

    private Integer value;

    private static Map<Integer,NodeType> allNodeType = Maps.newHashMap();

    static {
        for(NodeType nodeType : EnumSet.allOf(NodeType.class)){
            allNodeType.put(nodeType.value(),nodeType);
        }
    }

    private NodeType(Integer value){
        this.value = value;
    }

    public Integer value(){
        return this.value;
    }

    public static NodeType valueOf(Integer code){
        NodeType nodeType = allNodeType.get(code);
        if(nodeType == null){
            return UNKONWN;
        }
        return nodeType;
    }
}
