package com.github.benhaixiao.topic.shared;

import com.github.benhaixiao.topic.node.NodeType;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xiaobenhai
 *
 */
public class PathUtils {

    private static final String FOLDER_PREFIX = "0";
    private static final String ARTICLE_PREFIX = "1";
    private static final String POST_PREFIX = "2";
    private static final String FOLLOW_PREFIX = "3";
    private static final String ACCLAIM_PREFIX = "4";

    private static final String CONCAT_SYMBOL = "-";

    public static final String FOLDER_ROOT_PATH = "";

    public static String buildFolderPath(String parentPath,String pathName){
        if (StringUtils.isBlank(parentPath)){
            return FOLDER_PREFIX + pathName;
        }
        return parentPath + CONCAT_SYMBOL + FOLDER_PREFIX + pathName;
    }

    public static String buildArticlePath(String parentPath,String pathName){
        if (StringUtils.isBlank(parentPath)){
            return FOLDER_PREFIX + pathName;
        }
        return parentPath + CONCAT_SYMBOL + ARTICLE_PREFIX + pathName;    }

    public static String buildPostPath(String parentPath,String pathName){
        if (StringUtils.isBlank(parentPath)){
            return FOLDER_PREFIX + pathName;
        }
        return parentPath + CONCAT_SYMBOL + POST_PREFIX + pathName;
    }

    public static String buildFollowPath(String parentPath,String pathName){
        if (StringUtils.isBlank(parentPath)){
            return FOLDER_PREFIX + pathName;
        }
        return parentPath + CONCAT_SYMBOL + FOLLOW_PREFIX + pathName;
    }

    public static String buildAcclaimPath(String parentPath,String pathName){
        if (StringUtils.isBlank(parentPath)){
            return FOLDER_PREFIX + pathName;
        }
        return parentPath + CONCAT_SYMBOL + ACCLAIM_PREFIX + pathName;
    }

    public static NodeType judgeNodeType(String pathName){
        try{
            Integer nodeTypeCode = Integer.valueOf(pathName.charAt(0));
            return NodeType.valueOf(nodeTypeCode);
        }catch (Exception e){
            return NodeType.UNKONWN;
        }
    }

    public static String getParentFolderPath(String folderPath) {
        if(folderPath == null){
            return FOLDER_ROOT_PATH;
        }
        int index = folderPath.lastIndexOf(CONCAT_SYMBOL);
        if(index == -1){
            return FOLDER_ROOT_PATH;
        }
        return folderPath.substring(0,index);
    }
}
