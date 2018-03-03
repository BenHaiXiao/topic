package com.github.benhaixiao.topic.web.controller;

import com.github.benhaixiao.topic.AppManager;
import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.follow.Follow;
import com.github.benhaixiao.topic.shared.MarkUtils;
import com.github.benhaixiao.topic.shared.Status;
import com.github.benhaixiao.topic.web.domain.SlothResponse;
import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.exception.IllegalParameterException;
import com.github.benhaixiao.topic.follow.FollowService;
import com.github.benhaixiao.topic.shared.FolderJacksonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Date;

/**
 * @author xiaobenhai
 *
 */
@Controller
@RequestMapping("/")
public class FolderController {

    @Autowired
    private AppManager appManager;

    @RequestMapping(value = "app/{appid}/folder/{folderPath}", method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse getFolder(@PathVariable String appid, @PathVariable String folderPath){
        App app = appManager.find(appid);
        Folder folder = app.getFolder(folderPath);
        return new SlothResponse().code(Status.SUCCESS).add("folder",folder);
    }

    @RequestMapping(value = "app/{appid}/folder",method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse saveFolder(@PathVariable String appid,@RequestParam(value = "pfolder",required = false) String parentFolderPath,@RequestParam("data") String folderJson,@RequestParam("ticket") String ticket){
        App app = appManager.find(appid);
        Folder pFolder = app.getFolder(parentFolderPath);
        String uid =  ticket;
        try {
            folderJson = URLDecoder.decode(folderJson, "UTF-8");
        }catch (Exception e){
            throw new IllegalParameterException("illegal data parameter.");
        }
        Folder folder = FolderJacksonDeserializer.deserialize(folderJson);
        //设置marks
        folder.setMarks(MarkUtils.supplement(folder.getMarks(),app.getDict()));
        folder.setCreator(app.getUserStore().findOne(uid));
        folder.setUpdateTime(new Date());
        folder.setCreateTime(new Date());
        pFolder.storeFolder(folder);
        return new SlothResponse().code(Status.SUCCESS).add("folder",folder);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/follow",method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse doFollow(@PathVariable String appid,@PathVariable String folderPath,@RequestParam("ticket") String ticket){

        App app = appManager.find(appid);
        String uid =  ticket;

        Folder folder = app.getFolder(folderPath);
        FollowService followService = app.getFollowService();
        long followCount = followService.followFolder(uid,folder);
        return new SlothResponse().code(Status.SUCCESS).add("fowCnt",followCount);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/follow",method = RequestMethod.DELETE)
    @ResponseBody
    public SlothResponse removeFollow(@PathVariable String appid,@PathVariable String folderPath,@RequestParam("ticket") String ticket){

        App app = appManager.find(appid);
        String uid =  ticket;

        Folder folder = app.getFolder(folderPath);
        FollowService followService = app.getFollowService();
        long followCount = followService.unFollowFolder(uid,folder);
        return new SlothResponse().code(Status.SUCCESS).add("fowCnt",followCount);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/follow",method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse getFollow(@PathVariable String appid,@PathVariable String folderPath,@RequestParam("ticket")String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        Folder folder = app.getFolder(folderPath);
        FollowService followService = app.getFollowService();
        Follow follow = followService.getFollowFolder(uid,folder);
        if(follow == null){
            return new SlothResponse().code(Status.NO_SUCH_FOLLOW).add("message","no such follow");
        }
        follow.setTargetId(null);
        follow.setUid(null);
        return new SlothResponse().code(Status.SUCCESS).add("follow",follow);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/subfolders",method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse listSubFolders(@PathVariable String appid,@PathVariable String folderPath,@RequestParam(required = false) Integer offset,@RequestParam(required = true) Integer limit){
        App app = appManager.find(appid);
        if("root".equals(folderPath)){
            folderPath = null;
        }
        Folder folder = app.getFolder(folderPath);
        return new SlothResponse().code(Status.SUCCESS).add("folders",folder.listSubFolder(offset,limit));
    }


}
