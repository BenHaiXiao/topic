package com.github.benhaixiao.topic.shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.domain.*;
import com.github.benhaixiao.topic.exception.JacksonDeserializeException;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author xiaobenhai
 *
 */
public class ArticleJacksonDeserializer {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(ArticleJacksonDeserializer.class);
    public static Article deserialize(String articleJson) throws JacksonDeserializeException {
        try {
            Article article = new Article();
            JsonNode rootNode = objectMapper.readTree(articleJson);

            article.setTitle(rootNode.get("title").asText());
            article.setBrief(rootNode.path("brief").asText());
            //tags
            if (rootNode.has("tags")) {
                JsonNode tagNodes = rootNode.path("tags");
                Marks marks = new Marks();
                for (int i = 0; i < tagNodes.size(); i++) {
                    JsonNode tagNode = tagNodes.get(i);
                    marks.add(new Tag(tagNode.get("name").asText()));
                }
                article.setMarks(marks);
            }
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
                        image.setWidth(imageNode.path("width").asInt());
                        image.setHeight(imageNode.path("height").asInt());
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
            article.setContent(content);
                 article.setValid(1);
            return article;
        }catch (Exception e){
            LOG.warn("deserialize article error. article:{} msg:{}", articleJson, e);
            throw new JacksonDeserializeException("deserialize article error.");
        }
    }
}
