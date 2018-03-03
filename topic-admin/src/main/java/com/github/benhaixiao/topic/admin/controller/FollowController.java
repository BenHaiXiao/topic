package com.github.benhaixiao.topic.admin.controller;

import com.github.benhaixiao.topic.admin.util.*;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.AppManager;
import com.github.benhaixiao.topic.admin.json.FollowJsonDeserializer;
import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.exception.IllegalParameterException;
import com.github.benhaixiao.topic.follow.Follow;
import com.github.benhaixiao.topic.shared.Status;
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
@RequestMapping("/sloth/follow")
public class FollowController {
    private static final Logger LOG = LoggerFactory.getLogger(FollowController.class);
    @Autowired
    private AppManager appManager;
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        //对于需要转换为Date类型的属性，使用DateEditor进行处理
        DateFormat minuteDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        binder.registerCustomEditor(Date.class, new DateEditor(minuteDateFormat));
    }

    @RequestMapping(value = "mgr", method = RequestMethod.GET)
    public String mgr(Model model) {return "follow/follow-mgr";}

    @RequestMapping(value = "mgr2", method = RequestMethod.GET)
    public String mgr2(Model model,HttpServletRequest request) {
        LOG.info("post mgr invoked..");
        String appId=request.getParameter("appId");
        String targetId=request.getParameter("targetId");
        String typeId=request.getParameter("typeId");
        model.addAttribute("appId", appId);
        model.addAttribute("targetId", targetId);
        model.addAttribute("typeId",typeId);
        return "follow/follow-mgr";
    }
    /**
     * 表格数据listFolderFollow
     */
    @RequestMapping(value = "listFolderFollow")
    @ResponseBody
    public TinyGridBean listFolderFollow(String appId , String targetId, HttpServletRequest request) {
        LOG.info("list doing,param condition:{}", appId,targetId);
        TinyGridParam param = new TinyGridParam(request);
        int pageSize = param.getResultsPerPage();
        int curPage = param.getPage();
        pageSize=pageSize<0?0:pageSize;
        curPage=curPage<0?0:curPage;
        App app = appManager.find(appId);
        //此处书写查询函数
        List<Follow> follows=Lists.newArrayList();
        int count=0;
        if (StringUtils.isBlank(targetId)) {
            follows=app.getFollowService().listAllFolderFollow((curPage - 1) * pageSize, pageSize);
            count=app.getFollowService().getTotalCountFolderFollow();
        }else {
            follows=app.getFollowService().listFolderFollowByTargetId(targetId,(curPage-1)*pageSize,pageSize);
            count=app.getFollowService().getFolderFollowCountByTargetId(targetId);
        }
        TinyGridBean tinyGridBean= TinyGridUtils.convert(follows, param.getBeanProperties(), curPage, count, TinyGridUtils.DEFAULT_DATE_FORMAT);
        return  tinyGridBean;
    }
    /**
     * 表格数据listArticleFollow
     */
    @RequestMapping(value = "listArticleFollow")
    @ResponseBody
    public TinyGridBean listArticleFollow(String appId ,String targetId,HttpServletRequest request) {
        LOG.info("list doing,param condition:{}", appId,targetId);
        TinyGridParam param = new TinyGridParam(request);
        int pageSize = param.getResultsPerPage();
        int curPage = param.getPage();
        pageSize=pageSize<0?0:pageSize;
        curPage=curPage<0?0:curPage;
        App app = appManager.find(appId);
        //此处书写查询函数
        List<Follow> follows=Lists.newArrayList();
        int count=0;
        if (StringUtils.isBlank(targetId)) {
            follows=app.getFollowService().listAllArtcileFollow((curPage - 1) * pageSize, pageSize);
            count=app.getFollowService().getTotalCountArtcileFollow();
        }else {
            follows=app.getFollowService().listFolderFollowByTargetId(targetId,(curPage-1)*pageSize,pageSize);
            count=app.getFollowService().getArtcileFollowCountByTargetId(targetId);
        }
        TinyGridBean tinyGridBean= TinyGridUtils.convert(follows, param.getBeanProperties(), curPage, count, TinyGridUtils.DEFAULT_DATE_FORMAT);
        return  tinyGridBean;
    }
    /**
     * 表格数据save
     */
    @RequestMapping(value = "saveArticleFollow")
    @ResponseBody
    public SlothResponse saveArticleFollow(String appId, String targetId, HttpServletRequest request, String ticket)
    {
        App app = appManager.find(appId);
        String uid = ticket;
        Follow follow=new Follow();
        follow.setUid(uid);
        follow.setTargetId(targetId);
        follow.setCreateTime(new Date());
        long followCount=app.getFollowService().followArticle(follow);
        return new SlothResponse().code(Status.SUCCESS).add("followCount",followCount);
    }
    /**
     * 表格数据save
     */
    @RequestMapping(value = "saveFolderFollow")
    @ResponseBody
    public SlothResponse saveFolderFollow(String appId,String targetId,HttpServletRequest request,String ticket)
    {
        App app = appManager.find(appId);
        String uid = ticket;
        Follow follow=new Follow();
        follow.setUid(uid);
        follow.setTargetId(targetId);
        follow.setCreateTime(new Date());
        long followCount=app.getFollowService().followFolder(follow);
        return new SlothResponse().code(Status.SUCCESS).add("followCount",followCount);
    }
    @RequestMapping(value = "deleteArticleFollow")
       @ResponseBody
       public SlothResponse deleteArticleFollow(String appId,String followJson,HttpServletRequest request,String ticket)
    {
        LOG.info("delete doing,param ids:{}", appId,followJson);
        App app = appManager.find(appId);
        String uid = ticket;
        try {
            followJson = URLDecoder.decode(followJson, "UTF-8");
        } catch (Exception e) {
            throw new IllegalParameterException("illegal data parameter.");
        }
        List<Follow> follows = FollowJsonDeserializer.deserialize(followJson);
        long followCount=0;
        for (Follow follow : follows) {
            followCount= app.getFollowService().unFollowArticle(follow);
        }
        return new SlothResponse().code(Status.SUCCESS).add("acclaimCount", followCount);
    }
    @RequestMapping(value = "deleteFolderFollow")
    @ResponseBody
    public SlothResponse deleteFolderFollow(String appId,String followJson,HttpServletRequest request,String ticket)
    {
        LOG.info("delete doing,param ids:{}", appId,followJson);
        App app = appManager.find(appId);
        String uid = ticket;
        try {
            followJson = URLDecoder.decode(followJson, "UTF-8");
        } catch (Exception e) {
            throw new IllegalParameterException("illegal data parameter.");
        }
        List<Follow> follows = FollowJsonDeserializer.deserialize(followJson);
        long followCount=0;
        for (Follow follow : follows) {
            followCount= app.getFollowService().unFollowFolder(follow);
        }
        return new SlothResponse().code(Status.SUCCESS).add("acclaimCount", followCount);
    }

}


