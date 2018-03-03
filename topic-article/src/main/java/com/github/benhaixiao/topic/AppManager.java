package com.github.benhaixiao.topic;

import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.exception.NoSuchAppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */

@Component
public class AppManager {

    @Autowired
    @Qualifier("erdMongoTemplate")
    private MongoTemplate erdMongoTemplate;
    @Autowired
    @Qualifier("jubaMongoTemplate")
    private MongoTemplate jubaMongoTemplate;
    @Autowired
    @Qualifier("tiebaMongoTemplate")
    private MongoTemplate tiebaMongoTemplate;

    private Map<String, App> appStore = new HashMap<>();

    @PostConstruct
    public void init() {
        App erdApp = new App("erd_app", erdMongoTemplate);
        App jubaApp = new App("juba_app", jubaMongoTemplate);
        App tiebaApp = new App("tieba_app", tiebaMongoTemplate);
        appStore.put("erd_app", erdApp);
        appStore.put("juba_app", jubaApp);
        appStore.put("tieba_app", tiebaApp);
    }

    public App find(String appid) throws NoSuchAppException {
        App app = appStore.get(appid);
        if (app == null) {
            throw new NoSuchAppException();
        }
        return app;
    }
}
