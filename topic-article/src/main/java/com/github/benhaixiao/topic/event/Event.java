package com.github.benhaixiao.topic.event;

import com.github.benhaixiao.topic.event.app.AppCreateEvent;
import com.github.benhaixiao.topic.event.app.AppDeleteEvent;
import com.github.benhaixiao.topic.event.article.*;
import com.github.benhaixiao.topic.event.folder.*;
import com.github.benhaixiao.topic.event.node.NodeMetaModifyEvent;
import com.github.benhaixiao.topic.event.app.AppModifyEvent;
import com.github.benhaixiao.topic.event.node.NodeMetaCreateEvent;
import com.github.benhaixiao.topic.event.node.NodeMetaDeleteEvent;

/**
 * @author xiaobenhai
 *
 */
public abstract class Event {


    protected Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        NULL(NullEvent.class),
        APP_CREATE(AppCreateEvent.class),
        APP_DELETE(AppDeleteEvent.class),
        APP_MODIFY(AppModifyEvent.class),
        ARTICLE_CREATE(ArticleCreateEvent.class),
        ARTICLE_DELETE(ArticleDeleteEvent.class),
        ARTICLE_MODIFY(ArticleModifyEvent.class),
        ARTICLE_ACCLAIM(ArticleAcclaimEvent.class),
        ARTICLE_UNACCLAIM(ArticleUnAcclaimEvent.class),
        ARTICLE_POST_CREATE(ArticlePostCreateEvent.class),
        ARTICLE_POST_DELETE(ArticlePostDeleteEvent.class),
        ARTICLE_POST_MODIFY(ArticlePostModifyEvent.class),
        ARTICLE_POST_ACCLAIM(ArticlePostAcclaimEvent.class),
        ARTICLE_POST_UNACCLAIM(ArticlePostUnAcclaimEvent.class),
        ARTICLE_FOLLOW(ArticleFollowEvent.class),
        ARTICLE_UNFOLLOW(ArticleUnFollowEvent.class),
        FOLDER_CREATE(FolderCreateEvent.class),
        FOLDER_DELETE(FolderDeleteEvent.class),
        FOLDER_MODIFY(FolderModifyEvent.class),
        FOLDER_FOLLOW(FolderFollowEvent.class),
        FOLDER_UNFOLLOW(FolderUnFollowEvent.class),
//        FOLDER_ACCLAIM(),
//        FOLDER_UNACCLAIM(),
        NODE_META_CREATE(NodeMetaCreateEvent.class),
        NODE_META_DELETE(NodeMetaDeleteEvent.class),
        NODE_META_MODIFY(NodeMetaModifyEvent.class);

        private Class eventClazz;

        private Type(Class eventClazz){
            this.eventClazz = eventClazz;
        }
        public Class getEventClazz(){
            return eventClazz;
        }
    }
}
