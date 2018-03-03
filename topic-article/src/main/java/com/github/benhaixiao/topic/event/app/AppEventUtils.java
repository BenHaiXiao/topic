package com.github.benhaixiao.topic.event.app;

import com.github.benhaixiao.topic.app.App;

/**
 * @author xiaobenhai
 *
 */
public class AppEventUtils {

    public static AppCreateEvent createAppCreateEvent(App app){
        return new AppCreateEvent(app);
    }

    public static AppDeleteEvent createAppDeleteEvent(App app){
        return new AppDeleteEvent(app);
    }

    public static AppModifyEvent createAppModifyEvent(App oldApp,App newApp){
        return new AppModifyEvent(oldApp,newApp);
    }
}
