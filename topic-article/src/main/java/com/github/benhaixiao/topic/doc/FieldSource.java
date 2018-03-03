package com.github.benhaixiao.topic.doc;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
public abstract class FieldSource {

    protected String sourceId;
    private Dependency dependency;

    public abstract Map<String,Object> findOneByParams(Map<String, Object> params);

    public abstract List<Map<String,Object>> findByParams(Map<String, ? extends Object> params,int offset,int limit);

    public String getSourceId() {
        return sourceId;
    }

    public FieldSource setDependency(Dependency dependency){
        this.dependency = dependency;
        return this;
    }

    public Dependency getDependency() {
        return dependency;
    }

    public static class Dependency {
        private FieldSource fieldSource;
        private List<String> depFields = Lists.newArrayList();
        private String resultMapField;

        public Dependency(FieldSource fieldSource,String resultMapField, String... fields){
            this.fieldSource = fieldSource;
            this.resultMapField = resultMapField;
            if(fields != null){
                for(String field : fields){
                    if(field == null){
                        continue;
                    }
                    this.depFields.add(field);
                }
            }
        }

        public Dependency(FieldSource fieldSource, String resultMapField, List<String> depFields){
            this.fieldSource = fieldSource;
            this.depFields.addAll(depFields);
            this.resultMapField = resultMapField;
        }

        public FieldSource getFieldSource() {
            return fieldSource;
        }

        public List<String> getDepFields() {
            return depFields;
        }

        public String getResultMapField() {
            return resultMapField;
        }
    }
}
