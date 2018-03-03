package com.github.benhaixiao.topic.node;

import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.folder.Folder;

/**
 * @author xiaobenhai
 *
 */
public class NodeMetaUtils {

    public static NodeMeta createNodeMeta(Article article){
        NodeMeta nodeMeta = new NodeMeta();
        nodeMeta.setParentFolderPath(article.getFolderPath());
        NodeData nodeData = new NodeData();
        nodeData.setName(article.getTitle());
        nodeData.setDescription(article.getBrief());
        nodeMeta.setData(nodeData);
        nodeMeta.setType(NodeType.ARTICLE);
        nodeMeta.setMarks(article.getMarks());
        nodeMeta.setThumbs(article.getThumbs());
        nodeMeta.setCreator(article.getCreator());
        nodeMeta.setCreateTime(article.getCreateTime());
        nodeMeta.setUpdateTime(article.getUpdateTime());
        return nodeMeta;
    }

    public static NodeMeta createNodeMeta(Folder folder) {
        NodeMeta nodeMeta = new NodeMeta();
        nodeMeta.setParentFolderPath(folder.getPath());
        NodeData nodeData = new NodeData();
        nodeData.setName(folder.getName());
        nodeData.setDescription(folder.getDescription());
        nodeMeta.setData(nodeData);
        nodeMeta.setType(NodeType.DIR);
        nodeMeta.setMarks(folder.getMarks());
        nodeMeta.setThumbs(folder.getThumbs());
        nodeMeta.setCreator(folder.getCreator());
        nodeMeta.setCreateTime(folder.getCreateTime());
        nodeMeta.setUpdateTime(folder.getUpdateTime());
        return nodeMeta;
    }
}
