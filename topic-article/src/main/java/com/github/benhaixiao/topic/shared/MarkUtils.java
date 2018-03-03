package com.github.benhaixiao.topic.shared;

import com.github.benhaixiao.topic.domain.Tag;
import com.github.benhaixiao.topic.domain.Dict;
import com.github.benhaixiao.topic.domain.Marks;

/**
 * @author xiaobenhai
 *
 */
public class MarkUtils {

    public static Marks supplement(Marks marks,Dict dict){
        if(marks == null || marks.getTags() == null){
            return marks;
        }
        Marks newMarks = new Marks();
        for(Tag tag : marks.getTags()){
            Tag newTag = dict.findTag(tag.getName());
            if(newTag == null){
                continue;
            }
            newMarks.add(newTag);
        }
        return newMarks;
    }
}
