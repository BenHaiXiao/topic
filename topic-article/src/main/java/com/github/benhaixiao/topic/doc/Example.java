package com.github.benhaixiao.topic.doc;

import com.github.benhaixiao.topic.doc.ds.CommentSource;
import com.github.benhaixiao.topic.doc.ds.TopicSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class Example {

    public List<Map<String,Object>> test(){
        List<Map<String,Object>>  ret = null;
        DocListBuilder docListBuilder = new DocListBuilder();
        FieldMapper topicMapper = new FieldMapper();
        topicMapper.addMappedField("name","nick");
        topicMapper.addMappedField("desc","description");
        topicMapper.addMappedField("content","ct");
        FieldSource topicSource = new TopicSource();

        FieldMapper commentMapper = new FieldMapper();
        commentMapper.addMappedField("total","count");
        commentMapper.addMappedField("id","targetId");
        FieldSource commentSource = new CommentSource();
        commentSource.setDependency(new FieldSource.Dependency(topicSource, "id", "id"));

        ret = docListBuilder.setMainRef(new HashMap<String, Object>()).setOffset(0).setLimit(10).addMainFields(topicSource, topicMapper).addDepFields(commentSource, commentMapper).build();
        return ret;
    }
}
