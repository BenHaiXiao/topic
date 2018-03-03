package com.github.benhaixiao.topic.shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benhaixiao.topic.domain.Image;
import com.github.benhaixiao.topic.domain.Marks;
import com.github.benhaixiao.topic.domain.Tag;
import com.github.benhaixiao.topic.domain.Thumbs;
import com.github.benhaixiao.topic.exception.JacksonDeserializeException;
import com.github.benhaixiao.topic.folder.Folder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class FolderJacksonDeserializer {

    private static final Logger LOG = LoggerFactory.getLogger(FolderJacksonDeserializer.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Folder deserialize(String folderJson) throws JacksonDeserializeException {
        try {
            Folder folder = new Folder();
            JsonNode rootNode = objectMapper.readTree(folderJson);
            folder.setName(rootNode.get("name").asText());
            folder.setDescription(rootNode.path("desc").asText());
            folder.setIcon(rootNode.path("icon").asText());
            //marks
            JsonNode tagNodes = rootNode.path("tags");
            Marks marks = new Marks();
            for(int i = 0;i < tagNodes.size(); i++){
                JsonNode tagNode = tagNodes.get(i);
                marks.add(new Tag(tagNode.get("name").asText()));
            }
            folder.setMarks(marks);
            //thumbs
            JsonNode thumbNode = rootNode.path("thumbs");
            Thumbs thumbs = new Thumbs();
            JsonNode thumbImageNodes = thumbNode.path("images");
            List<Image> thumbImages = Lists.newArrayList();
            for(int i = 0; i< thumbImageNodes.size(); i++){
                JsonNode imageNode = thumbImageNodes.get(i);
                Image image = new Image();
                image.setUrl(imageNode.get("url").asText());
                image.setWidth(imageNode.path("width").asInt());
                image.setHeight(imageNode.path("height").asInt());
                image.setAltText(imageNode.path("altText").asText());
                thumbImages.add(image);
            }
            thumbs.setSize(thumbImages.size());
            thumbs.setImages(thumbImages);
            folder.setThumbs(thumbs);
            //exts
            JsonNode extNode = rootNode.path("exts");
            Iterator<Map.Entry<String, JsonNode>> extItor = extNode.fields();
            Map<String,String> exts = Maps.newHashMap();
            while (extItor.hasNext()){
                Map.Entry<String,JsonNode> entry = extItor.next();
                exts.put(entry.getKey(),entry.getValue().asText());
            }
            folder.setExts(exts);

            folder.setValid(1);
            return folder;
        }catch (Exception e){
            LOG.warn("deserialize folder error. folder:{} msg:{}", folderJson, e);
            throw new JacksonDeserializeException("deserialize folder error.");
        }
    }
}
