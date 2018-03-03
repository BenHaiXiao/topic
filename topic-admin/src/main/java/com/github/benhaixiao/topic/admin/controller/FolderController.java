package com.github.benhaixiao.topic.admin.controller;

import com.github.benhaixiao.topic.admin.json.FolderJacksonDeserializer;
import com.github.benhaixiao.topic.admin.util.*;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.AppManager;
import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.exception.IllegalParameterException;
import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.follow.FollowService;
import com.github.benhaixiao.topic.shared.MarkUtils;
import com.github.benhaixiao.topic.shared.PathUtils;
import com.github.benhaixiao.topic.shared.Status;
import com.github.benhaixiao.topic.user.User;
import com.github.benhaixiao.topic.user.UserStore;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xiaobenhai
 */
@Controller
@RequestMapping("/sloth/folder")
public class FolderController {
    private static final Logger LOG = LoggerFactory.getLogger(FolderController.class);
    @Autowired
    private AppManager appManager;
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        //对于需要转换为Date类型的属性，使用DateEditor进行处理
        DateFormat minuteDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        binder.registerCustomEditor(Date.class, new DateEditor(minuteDateFormat));
    }

    @RequestMapping(value = "mgr", method = RequestMethod.GET)
    public String mgr(Model model) {
        LOG.info("post mgr invoked..");
        return "folder/folder-mgr";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public TinyGridBean list(String appId , String folderPath, HttpServletRequest request, String ticket) {
        LOG.info("list doing,param condition:{}",appId,folderPath);
        TinyGridParam param = new TinyGridParam(request);
        int pageSize = param.getResultsPerPage();
        int curPage = param.getPage();
        pageSize=pageSize<0?0:pageSize;
        curPage=curPage<0?0:curPage;
        App app = appManager.find(appId);
        Folder pfolder = app.getFolder(folderPath);
        List<Folder> folders = Lists.newArrayList();
        int count =0;
        if (StringUtils.isBlank(folderPath)){
             folders=pfolder.findFolderAll((curPage-1)*pageSize,pageSize);
             count = pfolder.findFolderAll().size();
        }else {
            folders.add(pfolder);
            count =1;
        }
        TinyGridBean tinyGridBean= TinyGridUtils.convert(folders, param.getBeanProperties(), curPage, count, TinyGridUtils.DEFAULT_DATE_FORMAT);
        return  tinyGridBean;


/*
       FolderVo folderVo= new FolderVo();
        folderVo.setNodeId("nodeId");
        folderVo.setPath("FolderPath");
        folderVo.setName("目录");
        folderVo.setIcon("http://pic.nipic.com/2007-11-09/2007119121849495_2.jpg");
        folderVo.setDescription("好好好啊哈好顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶大大大");
        Marks marks=new Marks();
        marks.setSize(2);
        Tag tag1=new Tag();
        tag1.setId(1);
        tag1.setName("激情");
        Tag tag2=new Tag();
        tag2.setId(2);
        tag2.setName("经典");
        marks.add(tag1);
        marks.add(tag2);
        folderVo.setMarks(marks);
        List<Thumbs> listThumbs=new ArrayList<>();
        Thumbs thumbs=new Thumbs();
        Image image1=new Image();
        image1.setHeight(100);
        image1.setWidth(100);
        image1.setUrl("http://img.taopic.com/uploads/allimg/130501/240451-13050106450911.jpg");
        image1.setAltText("ooooooooooooo");
        Image image2=new Image();
        image2.setHeight(100);
        image2.setWidth(100);
        image2.setAltText("oopopopopopopop");
        image2.setUrl("http://pic1a.nipic.com/2008-12-04/2008124215522671_2.jpg");
        List<Image> listImage=new ArrayList<>();
        listImage.add(image1);
        listImage.add(image2);
        thumbs.setSize(2);
        thumbs.setImages(listImage);
        folderVo.setThumbs(thumbs);
        Map<String,String> map1=new HashMap<>();
        map1.put("1234", "好往好往 顶顶顶顶顶顶顶顶顶顶");
        map1.put("43211", "ququ   dddll;h好啊哈地对地导弹");
        folderVo.setExts(map1);
        Date date1=new Date();
        folderVo.setCreateTime(date1);
        folderVo.setUpdateTime(date1);
        FolderStats folderStats =new FolderStats();
        folderStats.setFolderCount(10L);
        folderStats.setTopicCount(20L);
        folderVo.setStats(folderStats);
        User user =new User();
        user.setUid("123456789");
        user.setNick("肖本海");
        user.setAvatar("烧饼");
        folderVo.setCreator(user);
        List<FolderVo> data =new ArrayList<>();
        data.add(folderVo);
        data.add(folderVo);
        data.add(folderVo);
        int count =data.size();

        TinyGridBean tinyGridBean=TinyGridUtils.convert(data, param.getBeanProperties(), curPage, count, TinyGridUtils.DEFAULT_DATE_FORMAT);
       return  tinyGridBean;
*/

    }

    /**
     * 表格数据listSubFolder
     */
    @RequestMapping(value = "listSubFolder")
    @ResponseBody
    public TinyGridBean listSubFolder(String appId ,String parentfolderPath,HttpServletRequest request,String ticket) {
        LOG.info("list doing,param condition:{}",appId,parentfolderPath);
        TinyGridParam param = new TinyGridParam(request);
        int pageSize = param.getResultsPerPage();
        int curPage = param.getPage();
        pageSize=pageSize<0?0:pageSize;
        curPage=curPage<0?0:curPage;
        App app = appManager.find(appId);
        Folder pfolder = app.getFolder(parentfolderPath);
        List<Folder> folders= pfolder.listSubFolder((curPage - 1) * pageSize, pageSize);
        int count =pfolder.listSubFolder().size();
        TinyGridBean tinyGridBean=TinyGridUtils.convert(folders, param.getBeanProperties(), curPage,count, TinyGridUtils.DEFAULT_DATE_FORMAT);
        return  tinyGridBean;
    }
    /**
     * 数据更新或新增
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse save(String appId, String parentFolderPath , String folderJson, String ticket) {
        LOG.info("save doing,param record:{}",appId,parentFolderPath,folderJson);
        App app=appManager.find(appId);

        Folder pFolder = app.getFolder(parentFolderPath);
        String uid = ticket;
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
        return new SlothResponse().code(Status.SUCCESS);
    }
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse  delete(String appId,String folderPaths,String ticket) {
        LOG.info("delete doing,param ids:{}", appId,folderPaths);
        App app = appManager.find(appId);
        Folder pFolder=app.getFolder(PathUtils.FOLDER_ROOT_PATH);
        if (StringUtils.isEmpty(folderPaths)) {
            return new SlothResponse().code(Status.ILLEGAL_PARAMETER);
        }
        try {
            String[] idStr = folderPaths.split(",");
            for (String id : idStr) {
             pFolder.removeFolder(id);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return  new  SlothResponse().code(Status.NO_SUCH_ARTICLE);
        }

        return new SlothResponse().code(Status.SUCCESS);
    }
    @RequestMapping(value = "doFollow",method = RequestMethod.POST)
        @ResponseBody
        public SlothResponse doFollow( String appId, String folderPath,String ticket, String action){

        App app = appManager.find(appId);
        String uid = ticket;

        Folder folder = app.getFolder(folderPath);
        FollowService followService = app.getFollowService();

        if("cancel".equalsIgnoreCase(action)){
            long followCount = followService.unFollowFolder(uid,folder);
            return new SlothResponse().code(Status.SUCCESS).add("fowCnt",followCount);
        }else {
            long followCount = followService.followFolder(uid,folder);
            return new SlothResponse().code(Status.SUCCESS).add("fowCnt",followCount);

        }
    }

    /**
     * 获取目录的关注列表
     * @author QuPeng
     * @param appId
     * @param folderPath
     * @param ticket
     * @param action
     * @return followers, followNum
     */
    @RequestMapping(value = "getFollowers")
    @ResponseBody
    public SlothResponse getFollowers(String appId, String folderPath,String ticket, String action) {
        App app = appManager.find(appId);
        UserStore userStore = app.getUserStore();
        FollowService followService = app.getFollowService();
        Folder folder = app.getFolder(folderPath);
        List<User> followers =  userStore.find(followService.getFolderFlwUids(folder));
        return new SlothResponse().code(Status.SUCCESS).add("followers",followers).add("followNum",followers.size());
    }

}
