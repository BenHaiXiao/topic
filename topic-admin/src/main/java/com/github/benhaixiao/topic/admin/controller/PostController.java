package com.github.benhaixiao.topic.admin.controller;
import com.github.benhaixiao.topic.admin.util.*;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.AppManager;
import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.exception.IllegalParameterException;
import com.github.benhaixiao.topic.post.Post;
import com.github.benhaixiao.topic.post.PostAcclaimStore;
import com.github.benhaixiao.topic.post.PostStore;
import com.github.benhaixiao.topic.shared.PostJacksonDeserializer;
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
 * 评论管理
 * @author xiaobenhai
 *
 */
@Controller
@RequestMapping("/sloth/post")
public class PostController {

    private static final Logger LOG = LoggerFactory.getLogger(PostController.class);
    @Autowired
    private AppManager appManager;


    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {

        DateFormat minuteDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        binder.registerCustomEditor(Date.class, new DateEditor(minuteDateFormat));
    }
    @RequestMapping(value = "mgr", method = RequestMethod.GET)
    public String mgr(Model model) {
        LOG.info("post mgr invoked..");
        return "post/post-mgr";
    }
    @RequestMapping(value = "mgr2", method = RequestMethod.GET)
    public String mgr2(Model model,HttpServletRequest request) {
        LOG.info("post mgr invoked..");
        String appId=request.getParameter("appId");
        String articleId=request.getParameter("articleId");
        model.addAttribute("appId", appId);
        model.addAttribute("articleId", articleId);
        return "post/post-mgr";
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse savePost(String appId, String articleId, String postJson, String ticket){
        App app = appManager.find(appId);
        String uid = ticket;
        try {
            postJson = URLDecoder.decode(postJson, "UTF-8");
        }catch (Exception e){
            throw new IllegalParameterException("illegal data parameter.");
        }
        PostStore postStore = app.getAppContext().getPostStore();
        Post post= PostJacksonDeserializer.deserialize(postJson);
      //  post.setCreator(app.getUserStore().findOne(uid));
        post.setTargetId(articleId);
        post.setUpdateTime(new Date());
        post.setCreateTime(new Date());
        postStore.store(post);
        List<Post> posts = Lists.newArrayList();
        posts.add(post);
        return new SlothResponse().code(Status.SUCCESS);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public SlothResponse deletePost(String appId, String postIds, String ticket){
        App app = appManager.find(appId);
        String uid = ticket;
        PostStore postStore = app.getAppContext().getPostStore();
        if (StringUtils.isEmpty(postIds)) {
            return new SlothResponse().code(Status.ILLEGAL_PARAMETER);
        }
        try {
            String[] idStr = postIds.split(",");
            for (String id : idStr) {
                postStore.remove(id);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return  new  SlothResponse().code(Status.NO_SUCH_ARTICLE);
        }

        return new SlothResponse().code(Status.SUCCESS);
    }

    /**
     * 表格数据list
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public TinyGridBean listPost(String appId, String articleId, String postId, HttpServletRequest request){
        LOG.info("list doing,param condition:{}",appId,articleId);
        TinyGridParam param = new TinyGridParam(request);
        int pageSize = param.getResultsPerPage();
        int curPage =  param.getPage();
        pageSize=pageSize<0?0:pageSize;
        curPage=curPage<0?0:curPage;
        App app = appManager.find(appId);
        PostStore postStore = app.getAppContext().getPostStore();
        List<Post> posts= Lists.newArrayList();
        int count=0;
        if (StringUtils.isNotBlank(postId))
        {
            Post post =postStore.findOne(postId);
            count=1;
            posts.add(post);
        }else {
            if (StringUtils.isBlank(articleId)) {
                posts = postStore.findAll((curPage - 1) * pageSize, pageSize);
                count = postStore.findAll().size();
            }else{
                posts=postStore.find(articleId,(curPage-1)*pageSize,pageSize,null);
                count=postStore.find(articleId,null).size();
            }
        }
        TinyGridBean tinyGridBean= TinyGridUtils.convert(posts, param.getBeanProperties(), curPage,count, TinyGridUtils.DEFAULT_DATE_FORMAT);
        return  tinyGridBean;
    }

    @RequestMapping(value = "doAcclaim",method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse doAcclaim(String appId, String articleId,String action, String ticket) {
        App app = appManager.find(appId);
        String uid = ticket;
        PostAcclaimStore postAcclaimStore = app.getAppContext().getPostAcclaimStore();
        PostStore postStore = app.getAppContext().getPostStore();
        Acclaim acclaim = new Acclaim();
        acclaim.setUid(uid);
        acclaim.setTargetId(articleId);
        if ("cancel".equalsIgnoreCase(action)) {
            int ret = postAcclaimStore.remove(acclaim);
            if (ret == 1) {
                postStore.decAcclaimCount(articleId);
            }
            return new SlothResponse().code(Status.SUCCESS);
        } else {
            acclaim.setCreateTime(new Date());
            postAcclaimStore.store(acclaim);
            long acclaimCount = postStore.incAcclaimCount(articleId);
            acclaim.setUid(null);
            acclaim.setTargetId(null);
            return new SlothResponse().code(Status.SUCCESS).add("acmCnt", acclaimCount);
        }
    }

}


