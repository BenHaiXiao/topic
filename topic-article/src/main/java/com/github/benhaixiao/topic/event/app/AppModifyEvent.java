package com.github.benhaixiao.topic.event.app;

import com.github.benhaixiao.topic.event.Event;
import com.github.benhaixiao.topic.app.App;

/**
 * @author xiaobenhai
 *
 */
public class AppModifyEvent extends Event {

    private App oldApp;
    private App newApp;

    public AppModifyEvent(){
        this.type = Type.APP_MODIFY;
    }

    public AppModifyEvent(App oldApp, App newApp) {
        this.type = Type.APP_MODIFY;
        this.oldApp = oldApp;
        this.newApp = newApp;
    }

    public App getOldApp() {
        return oldApp;
    }

    public void setOldApp(App oldApp) {
        this.oldApp = oldApp;
    }

    public App getNewApp() {
        return newApp;
    }

    public void setNewApp(App newApp) {
        this.newApp = newApp;
    }
}
