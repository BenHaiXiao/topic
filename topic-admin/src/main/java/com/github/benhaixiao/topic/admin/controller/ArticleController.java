package com.github.benhaixiao.topic.admin.controller;

import com.github.benhaixiao.topic.admin.util.*;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.AppManager;
import com.github.benhaixiao.topic.acclaim.AcclaimService;

import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.exception.IllegalParameterException;
import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.follow.FollowService;
import com.github.benhaixiao.topic.shared.ArticleJacksonDeserializer;
import com.github.benhaixiao.topic.shared.PathUtils;
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
@RequestMapping("/sloth/article")
public class ArticleController {
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
    public String mgr(Model model) {return "article/article-mgr";}

    @RequestMapping(value = "mgr2", method = RequestMethod.GET)
    public String mgr2(Model model,HttpServletRequest request) {
        LOG.info("post mgr invoked..");
        String appId=request.getParameter("appId");
        String folderPath=request.getParameter("folderPath");
        model.addAttribute("appId", appId);
        model.addAttribute("folderPath", folderPath);
        return "article/article-mgr";
    }
    /**
     * 表格数据list
     */
    @RequestMapping(value = "listArticle")
    @ResponseBody
    public TinyGridBean listArticle(String appId , String folderPath, String articleId, HttpServletRequest request) {
        LOG.info("list doing,param condition:{}", appId,folderPath);
        TinyGridParam param = new TinyGridParam(request);
        int pageSize = param.getResultsPerPage();
        int curPage = param.getPage();
        pageSize=pageSize<0?0:pageSize;
        curPage=curPage<0?0:curPage;
        App app = appManager.find(appId);
        //此处书写查询函数
        List<Article> articles= Lists.newArrayList();
        if(StringUtils.isBlank(folderPath))
            folderPath=PathUtils.FOLDER_ROOT_PATH;
        Folder pFolder=app.getFolder(folderPath);
        int count =0;
        if(StringUtils.isNotBlank(articleId)){
            Article article=pFolder.getArticle(articleId);
            count=1;
            articles.add(article);
        }else {
             if (folderPath.equals(PathUtils.FOLDER_ROOT_PATH)){
                   articles=pFolder.findArticleAll((curPage - 1) * pageSize, pageSize);
                   count=pFolder.findArticleAll().size();
              }else{
                  articles = pFolder.listArticle((curPage - 1) * pageSize, pageSize);
                   count = pFolder.listArticle().size();
               }
        }
        TinyGridBean tinyGridBean= TinyGridUtils.convert(articles, param.getBeanProperties(), curPage, count, TinyGridUtils.DEFAULT_DATE_FORMAT);

        return  tinyGridBean;
    }




    /**
     * 表格数据save
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public SlothResponse save(String appId, String folderPath, String articleJson, HttpServletRequest request, String ticket)
    {
        App app = appManager.find(appId);
        Folder folder = app.getFolder(folderPath);
        String uid = ticket;
        try {
            articleJson = URLDecoder.decode(articleJson, "UTF-8");
        }catch (Exception e){
            throw new IllegalParameterException("illegal data parameter.");
        }
        Article article = ArticleJacksonDeserializer.deserialize(articleJson);
        //设置marks
        article.setFolderPath(folderPath);
        folder.storeArticle(article);
        return new SlothResponse().code(Status.SUCCESS);
    }
    /**
     * 表格数据save
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public SlothResponse delete(String appId,String folderPath,String articleIds,HttpServletRequest request,String ticket)
    {
        LOG.info("delete doing,param ids:{}", appId,folderPath,articleIds);
        App app = appManager.find(appId);
        if(StringUtils.isBlank(folderPath))
            folderPath=PathUtils.FOLDER_ROOT_PATH;
        Folder folder = app.getFolder(folderPath);
        String uid = ticket;
        if (StringUtils.isEmpty(articleIds)) {
            return new SlothResponse().code(Status.ILLEGAL_PARAMETER);
        }
        try {
            String[] idStr = articleIds.split(",");
            for (String id : idStr) {
                folder.removeArticle(id);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return  new  SlothResponse().code(Status.NO_SUCH_ARTICLE);
        }
        return new SlothResponse().code(Status.SUCCESS);
    }

    /**
     * 获取文章点赞总数
     * @author QuPeng
     * @param appId
     * @param folderPath
     * @param articleId
     * @param request
     * @param ticket
     * @return
     */
    @RequestMapping(value = "getAcclaimNum")
    @ResponseBody
    public SlothResponse getAcclaimNum(String appId, String folderPath, String articleId, HttpServletRequest request,  String ticket) {
        App app = appManager.find(appId);
        AcclaimService acclaimService = app.getAcclaimService();
        long acclaimNum = acclaimService.getArticleAcclaimCountByTargetId(articleId);
        return new SlothResponse().code(Status.SUCCESS).add("acclaimNum",acclaimNum);
    }

    /**
     * 获取文章的关注列表
     * @author QuPeng
     * @param appId
     * @param folderPath
     * @param articleId
     * @param request
     * @param ticket
     * @return
     */
    @RequestMapping(value = "getFollowers")
    @ResponseBody
    public SlothResponse getFollowers(String appId, String folderPath, String articleId, HttpServletRequest request,  String ticket){
        App app = appManager.find(appId);
        FollowService followService = app.getFollowService();
        //获取关注用户uid

        //获取用户信息

        return new SlothResponse().code(Status.SUCCESS);
    }
}
