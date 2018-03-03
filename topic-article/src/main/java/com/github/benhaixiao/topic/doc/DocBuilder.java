package com.github.benhaixiao.topic.doc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public class DocBuilder {

    private Map<String,Object> mainRef;

    private MainProcessor mainProcessor;

    private List<DepProcessor> depProcessors = Lists.newArrayList();

    public DocBuilder setMainRef(Map<String,Object> mainRef){
        this.mainRef = mainRef;
        return this;
    }


    public DocBuilder addMainFields(FieldSource fieldSource,FieldMapper fieldMapper){
        this.mainProcessor = new MainProcessor(fieldSource,fieldMapper);
        return this;
    }

    public DocBuilder addDepFields(FieldSource fieldSource,FieldMapper fieldMapper){
        this.depProcessors.add(new DepProcessor(fieldSource,fieldMapper));
        return this;
    }

    public Map<String,Object> build(){

        Map<String,Object> doc;
        mainProcessor.process();
        doc = mainProcessor.getFieldMapper().convert(mainProcessor.getResult());
        for(DepProcessor depProcessor : depProcessors){
            depProcessor.setDepData(mainProcessor.getFieldValues(depProcessor.getDepFields()));
            depProcessor.process();
            doc.put(depProcessor.getFieldMapper().getField(),depProcessor.getFieldMapper().convert(depProcessor.getResult()));
        }
        return doc;
    }

    private interface Processor{
        void process();

        FieldSource getFieldSource();

        <T> T getResult();

        FieldMapper getFieldMapper();
    }

    private class MainProcessor implements Processor{
        private FieldSource fieldSource;
        private FieldMapper fieldMapper;
        private Map<String,Object> result;
        public MainProcessor(FieldSource fieldSource,FieldMapper fieldMapper){
            this.fieldSource = fieldSource;
            this.fieldMapper = fieldMapper;
        }

        public void process(){
            result = fieldSource.findOneByParams(mainRef);
        }

        @Override
        public FieldSource getFieldSource() {
            return this.fieldSource;
        }

        @Override
        public Map<String,Object> getResult() {
            return result;
        }

        @Override
        public FieldMapper getFieldMapper() {
            return this.fieldMapper;
        }
        public Map<String,Object> getFieldValues(List<String> fields){
            Map<String,Object> ret = Maps.newHashMap();

            for(String field : fields){
                ret.put(field,result.get(field));
            }

            return ret;
        }

    }

    private class DepProcessor implements Processor{
        private FieldSource fieldSource;
        private FieldMapper fieldMapper;
        private Map<String,Object> result;

        private Map<String,Object> depData;

        public DepProcessor(FieldSource fieldSource,FieldMapper fieldMapper){
            this.fieldSource = fieldSource;
            this.fieldMapper = fieldMapper;
        }

        @Override
        public void process() {
            result = fieldSource.findOneByParams(depData);
        }

        @Override
        public FieldSource getFieldSource() {
            return this.fieldSource;
        }

        @Override
        public Map<String,Object> getResult() {
            return this.result;
        }

        @Override
        public FieldMapper getFieldMapper() {
            return this.fieldMapper;
        }
        public Map<String, Object> getDepData() {
            return depData;
        }

        public void setDepData(Map<String, Object> depData) {
            this.depData = depData;
        }
        public Map<String,Object> getFieldValues(List<String> fields){
            Map<String,Object> ret = Maps.newHashMap();

            for(String field : fields){
                ret.put(field,result.get(field));
            }

            return ret;
        }

        public List<String> getDepFields() {
            return this.fieldSource.getDependency().getDepFields();
        }

    }

}
