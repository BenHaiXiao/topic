package com.github.benhaixiao.topic.doc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class DocListBuilder {

    private Map<String,Object> mainRef;

    private int offset;

    private int limit;

    private MainProcessor mainProcessor;

    private List<DepProcessor> depProcessors = Lists.newArrayList();

    public DocListBuilder setMainRef(Map<String,Object> mainRef){
        this.mainRef = mainRef;
        return this;
    }

    public DocListBuilder setOffset(int offset){
        this.offset = offset;
        return this;
    }

    public DocListBuilder setLimit(int limit){
        this.limit = limit;
        return this;
    }

    public DocListBuilder addMainFields(FieldSource fieldSource,FieldMapper fieldMapper){
        mainProcessor = new MainProcessor(fieldSource,fieldMapper);
        return this;
    }

    public DocListBuilder addDepFields(FieldSource fieldSource,FieldMapper fieldMapper){
        depProcessors.add(new DepProcessor(fieldSource,fieldMapper));
        return this;
    }

    public List<Map<String,Object>> build(){
        List<Map<String,Object>> list = Lists.newArrayList();
        mainProcessor.process();
        for(DepProcessor depProcessor : depProcessors){
            depProcessor.setDepData(mainProcessor.getFieldValues(depProcessor.getDepFields()));
            depProcessor.process();
        }
        List<Map<String,Object>> mainResult = mainProcessor.getResult();
        for(Map<String,Object> item : mainResult){
            Map<String,Object> dest = mainProcessor.getFieldMapper().convert(item);
            for(DepProcessor depProcessor : depProcessors){
                String key = (String)item.get(depProcessor.getResultMapField());
                dest.put(depProcessor.getFieldMapper().getField(),depProcessor.getFieldMapper().convert(depProcessor.getResult(key)));
            }
            list.add(dest);
        }
        return list;
    }

    private interface Processor {
        void process();

        FieldSource getFieldSource();

        <T> T getResult();

        Map<String,List<Object>> getFieldValues(List<String> fields);

        FieldMapper getFieldMapper();
    }

    private class MainProcessor implements Processor{
        private FieldSource fieldSource;
        private FieldMapper fieldMapper;
        private List<Map<String,Object>> result;

        public MainProcessor(FieldSource fieldSource,FieldMapper fieldMapper){
            this.fieldSource = fieldSource;
            this.fieldMapper = fieldMapper;
        }

        @Override
        public void process() {
            result = fieldSource.findByParams(mainRef,offset,limit);
        }

        @Override
        public FieldSource getFieldSource() {
            return fieldSource;
        }

        @Override
        public List<Map<String,Object>> getResult() {
            return this.result;
        }

        @Override
        public Map<String, List<Object>> getFieldValues(List<String> fields) {
            Map<String,List<Object>> ret = Maps.newHashMap();
            for(Map<String,Object> dsItem : result) {
                for (String field : fields) {
                    List<Object> values = ret.get(field);
                    if(values == null){
                        values = Lists.newArrayList();
                        ret.put(field,values);
                    }
                    values.add(dsItem.get(field));
                }
            }
            return ret;
        }

        @Override
        public FieldMapper getFieldMapper() {
            return this.fieldMapper;
        }

    }

    private class DepProcessor implements Processor{
        private FieldSource fieldSource;
        private FieldMapper fieldMapper;
        private Map<String,Map<String,Object>> result;
        private Map<String,List<Object>> depData;
        private String resultMapField;

        public DepProcessor(FieldSource fieldSource,FieldMapper fieldMapper){
            this.fieldSource = fieldSource;
            this.fieldMapper = fieldMapper;
            this.resultMapField = fieldSource.getDependency().getResultMapField();
        }

        public void process(){
            List<Map<String,Object>> result = fieldSource.findByParams(depData,0,100);
            if(CollectionUtils.isEmpty(result)){
                return;
            }
            this.result = Maps.newHashMap();
            for(Map<String,Object> item : result){
                //TODO
                this.result.put(item.get(resultMapField).toString(), item);
            }
        }

        public FieldSource getFieldSource() {
            return fieldSource;
        }

        @Override
        public Map<String,Map<String,Object>> getResult() {
            return this.result;
        }

        public Map<String,Object> getResult(String key){
            return this.result.get(key);
        }

        @Override
        public FieldMapper getFieldMapper() {
            return this.fieldMapper;
        }

        public Map<String,List<Object>> getFieldValues(List<String> fields){
            Map<String,List<Object>> ret = Maps.newHashMap();
            for(Map<String,Object> item : result.values()) {
                for (String field : fields) {
                    List<Object> values = ret.get(field);
                    if(values == null){
                        values = Lists.newArrayList();
                        ret.put(field,values);
                    }
                    values.add(item.get(field));
                }
            }
            return ret;
        }

        public List<String> getDepFields() {
            return this.fieldSource.getDependency().getDepFields();
        }

        public void setDepData(Map<String,List<Object>> depData){
            this.depData = depData;
        }

        public String getResultMapField() {
            return resultMapField;
        }

        public void setResultMapField(String resultMapField) {
            this.resultMapField = resultMapField;
        }
    }
}
