package com.github.benhaixiao.topic.shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benhaixiao.topic.domain.*;
import com.github.benhaixiao.topic.exception.JacksonDeserializeException;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.post.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class PostJacksonDeserializer {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(PostJacksonDeserializer.class);
    public static Post deserialize(String postJson) throws JacksonDeserializeException {
        try {
            Post post = new Post();
            JsonNode rootNode = objectMapper.readTree(postJson);

            JsonNode contentNode = rootNode.get("content");
            JsonNode sectionNodes = contentNode.get("sections");
            List<Section> sections = Lists.newArrayList();
            for(int i = 0; i< sectionNodes.size(); i++){
                JsonNode sectionNode = sectionNodes.get(i);
                Section.Type sectionType = Section.Type.valueOf(sectionNode.get("type").asText().toUpperCase());
                //TODO
                JsonNode sectionAttr = sectionNode.path("attr");
                switch (sectionType){
                    case IMAGE:
                        JsonNode imageNode = sectionNode.get("image");
                        ImageSection imageSection = new ImageSection();
                        Image image = new Image();
                        image.setUrl(imageNode.get("url").asText());
                        image.setAltText(imageNode.path("altText").asText());
                        imageSection.setImage(image);
                        sections.add(imageSection);
                        break;
                    case TEXT:
                        JsonNode textNode = sectionNode.get("text");
                        TextSection textSection = new TextSection();
                        textSection.setText(textNode.asText());
                        sections.add(textSection);
                        break;
                    case AUDIO:
                        JsonNode audioNode = sectionNode.get("audio");
                        Audio audio = new Audio();
                        audio.setUrl(audioNode.get("url").asText());
                        audio.setDuration(audioNode.path("duration").asLong());
                        audio.setImageUrl(audioNode.path("imageUrl").asText());
                        audio.setMedia(Audio.Media.valueOf(audioNode.get("media").asText()));
                        AudioSection audioSection = new AudioSection();
                        audioSection.setAudio(audio);
                        break;
                    case VIDEO:
                        JsonNode videoNode = sectionNode.get("video");
                        Video video = new Video();
                        video.setUrl(videoNode.get("url").asText());
                        video.setDuration(videoNode.path("duration").asLong());
                        video.setImageUrl(videoNode.path("imageUrl").asText());
                        video.setMedia(Video.Media.valueOf(videoNode.get("media").asText()));
                        VideoSection videoSection = new VideoSection();
                        videoSection.setVideo(video);
                        break;
                }
            }
            Content content = new Content();
            content.setSize(sections.size());
            content.setSections(sections);
            post.setContent(content);
           // post.setTargetId(rootNode.get("targetId").asText());
          //  post.setReplyId(rootNode.get("replyId").asText());
            post.setValid(1);
            return post;
        }catch (Exception e){
            LOG.warn("deserialize post error. post:{} msg:{}", postJson, e);
            throw new JacksonDeserializeException("deserialize post error.");
        }
    }
}
