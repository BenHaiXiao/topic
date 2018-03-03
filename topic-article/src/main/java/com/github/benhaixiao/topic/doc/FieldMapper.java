package com.github.benhaixiao.topic.doc;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class FieldMapper {

    private String field = null;
    private Map<String,String> MappedFields = Maps.newHashMap();

    public FieldMapper addMappedField(String srcField,String destField){
        MappedFields.put(srcField,destField);
        return this;
    }

    public String get(String field) {
        return MappedFields.get(field);
    }

    public void setField(String field){
        this.field = field;
    }

    public String getField(){
        return this.field;
    }

    public Map<String,Object> convert(Map<String,Object> srcMap){
        Map<String,Object> destMap = Maps.newHashMap();
        for(Map.Entry<String,String> item : MappedFields.entrySet()){
            Object value = srcMap.get(item.getKey());
            destMap.put(item.getValue(),value);
        }
        return destMap;
    }
}
