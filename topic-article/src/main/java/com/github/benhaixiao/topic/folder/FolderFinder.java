package com.github.benhaixiao.topic.folder;

import com.github.benhaixiao.topic.context.AppContext;
import com.github.benhaixiao.topic.exception.NoSuchFolderException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author xiaobenhai
 *
 */
public class FolderFinder {

    private AppContext appContext;

    private MongoTemplate folderMongoTemplate;

    public FolderFinder(AppContext appContext){
        this.appContext = appContext;
        this.folderMongoTemplate = appContext.getSlothMongoTemplate();
    }

    public Folder find(String path) throws NoSuchFolderException {
        if(StringUtils.isBlank(path)){
            Folder folder = new Folder();
            folder.setPath("");
            folder.setValid(1);
            folder.setDescription("根目录");
            folder.setName("root");
            folder.setAppContext(appContext);
            return folder;
        }
        Folder folder = folderMongoTemplate.findOne(new Query(Criteria.where("path").is(path).and("valid").is(1)), Folder.class);
        if(folder == null){
            throw new NoSuchFolderException();
        }
        folder.setAppContext(appContext);
        return folder;
    }
}
