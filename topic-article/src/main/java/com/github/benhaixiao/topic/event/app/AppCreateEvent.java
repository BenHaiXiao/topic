package com.github.benhaixiao.topic.event.app;

import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.event.Event;

/**
 * @author xiaobenhai
 *
 */
public class AppCreateEvent extends Event {

    private App app;

    public AppCreateEvent() {
        this.type = Type.APP_CREATE;
    }

    public AppCreateEvent(App app) {
        this.type = Type.APP_CREATE;
        this.app = app;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
