package com.github.benhaixiao.topic.event.app;

import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class AppDeleteEvent extends Event {
    private App app;

    public AppDeleteEvent() {
        this.type = Type.APP_DELETE;
    }

    public AppDeleteEvent(App app) {
        this.type = Type.APP_DELETE;
        this.app = app;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
