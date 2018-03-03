package com.github.benhaixiao.topic.admin.controller;

import com.github.benhaixiao.topic.admin.util.*;
import com.google.common.collect.Lists;
import com.github.benhaixiao.topic.AppManager;
import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.admin.json.AcclaimJsonDeserializer;
import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.exception.IllegalParameterException;
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
@RequestMapping("/sloth/acclaim")
public class AcclaimController {
    private static final Logger LOG = LoggerFactory.getLogger(AcclaimController.class);
    @Autowired
    private AppManager appManager;
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        //对于需要转换为Date类型的属性，使用DateEditor进行处理
        DateFormat minuteDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        binder.registerCustomEditor(Date.class, new DateEditor(minuteDateFormat));
    }

    @RequestMapping(value = "mgr", method = RequestMethod.GET)
    public String mgr(Model model) {return "acclaim/acclaim-mgr";}

    @RequestMapping(value = "mgr2", method = RequestMethod.GET)
    public String mgr2(Model model,HttpServletRequest request) {
        LOG.info("post mgr invoked..");
        String appId=request.getParameter("appId");
        String targetId=request.getParameter("targetId");
        String typeId=request.getParameter("typeId");
        model.addAttribute("appId", appId);
        model.addAttribute("targetId", targetId);
        model.addAttribute("typeId",typeId);
        return "acclaim/acclaim-mgr";
    }
    /**
     * 表格数据listArticleAcclaim
     */
    @RequestMapping(value = "listArticleAcclaim")
    @ResponseBody
    public TinyGridBean listArticleAcclaim(String appId , String targetId, HttpServletRequest request) {
        LOG.info("list doing,param condition:{}", appId,targetId);
        TinyGridParam param = new TinyGridParam(request);
        int pageSize = param.getResultsPerPage();
        int curPage = param.getPage();
        pageSize=pageSize<0?0:pageSize;
        curPage=curPage<0?0:curPage;
        App app = appManager.find(appId);
        //此处书写查询函数
        List<Acclaim> acclaims=Lists.newArrayList();
        int count=0;
        if (StringUtils.isBlank(targetId)){
            acclaims=app.getAcclaimService().listAllArticleAcclaim((curPage-1)*pageSize,pageSize);
            count=app.getAcclaimService().getTotalCountArticleAcclaim();
        }else{
            acclaims=app.getAcclaimService().listArticleAcclaimByTargetId(targetId,(curPage-1)*pageSize,pageSize);
            count=app.getAcclaimService().getArticleAcclaimCountByTargetId(targetId);
        }
        TinyGridBean tinyGridBean= TinyGridUtils.convert(acclaims, param.getBeanProperties(), curPage, count, TinyGridUtils.DEFAULT_DATE_FORMAT);
        return  tinyGridBean;
    }
    /**
     * 表格数据listPostAcclaim
     */
    @RequestMapping(value = "listPostAcclaim")
    @ResponseBody
    public TinyGridBean listPostAcclaim(String appId ,String targetId,HttpServletRequest request) {
        LOG.info("list doing,param condition:{}", appId,targetId);
        TinyGridParam param = new TinyGridParam(request);
        int pageSize = param.getResultsPerPage();
        int curPage = param.getPage();
        pageSize=pageSize<0?0:pageSize;
        curPage=curPage<0?0:curPage;
        App app = appManager.find(appId);
        //此处书写查询函数
        List<Acclaim> acclaims=Lists.newArrayList();
        int count=0;
        if (StringUtils.isBlank(targetId)){
            acclaims=app.getPostService().listAllPostAcclaim((curPage - 1) * pageSize, pageSize);
            count=app.getPostService().getTotalCountPostAcclaim();
        }else{
            acclaims=app.getPostService().listPostAcclaimByTargetId(targetId, (curPage - 1) * pageSize, pageSize);
            count=app.getPostService().getPostAcclaimCountByTargetId(targetId);
        }
        TinyGridBean tinyGridBean= TinyGridUtils.convert(acclaims, param.getBeanProperties(), curPage, count, TinyGridUtils.DEFAULT_DATE_FORMAT);
        return  tinyGridBean;
    }

    /**
     * 保存文章点赞
     */
    @RequestMapping(value = "saveArticleAcclaim")
    @ResponseBody
    public SlothResponse saveArticleAcclaim(String appId, String targetId, HttpServletRequest request, String ticket)
    {
        App app = appManager.find(appId);
        String uid = ticket;
     long acclaimCount= app.getAcclaimService().acclaimArticle(uid, targetId);
        return new SlothResponse().code(Status.SUCCESS).add("acclaimCount",acclaimCount);
    }
    /**
     * 保存评论点赞
     */
    @RequestMapping(value = "savePostAcclaim")
    @ResponseBody
    public SlothResponse savePostAcclaim(String appId,String targetId,HttpServletRequest request,String ticket)
    {
        App app = appManager.find(appId);
        String uid = ticket;
        long acclaimCount= app.getPostService().acclaimPost(uid, targetId);
        return new SlothResponse().code(Status.SUCCESS).add("acclaimCount",acclaimCount);
    }


    @RequestMapping(value = "deleteArticleAcclaim")
    @ResponseBody
    public SlothResponse deleteArticleAcclaim(String appId,String acclaimJson,HttpServletRequest request,String ticket) {
        LOG.info("delete doing,param ids:{}", appId, acclaimJson);
        App app = appManager.find(appId);
        String uid = ticket;
        try {
            acclaimJson = URLDecoder.decode(acclaimJson, "UTF-8");
        } catch (Exception e) {
            throw new IllegalParameterException("illegal data parameter.");
        }
        List<Acclaim> acclaims = AcclaimJsonDeserializer.deserialize(acclaimJson);
        long acclaimCount=0;
        for (Acclaim acclaim : acclaims) {
            acclaimCount= app.getAcclaimService().unAcclaimArticle(acclaim.getUid(), acclaim.getTargetId());
        }
        return new SlothResponse().code(Status.SUCCESS).add("acclaimCount",acclaimCount);
    }
    @RequestMapping(value = "deletePostAcclaim")
    @ResponseBody
    public SlothResponse deletePostAcclaim(String appId,String acclaimJson,HttpServletRequest request,String ticket) {
        LOG.info("delete doing,param ids:{}", appId, acclaimJson);
        App app = appManager.find(appId);
        String uid = ticket;
        try {
            acclaimJson = URLDecoder.decode(acclaimJson, "UTF-8");
        } catch (Exception e) {
            throw new IllegalParameterException("illegal data parameter.");
        }
        List<Acclaim> acclaims = AcclaimJsonDeserializer.deserialize(acclaimJson);
        long acclaimCount=0;
        for (Acclaim acclaim : acclaims) {
            acclaimCount= app.getPostService().unAcclaimPost(acclaim.getUid(), acclaim.getTargetId());
        }
        return new SlothResponse().code(Status.SUCCESS).add("acclaimCount",acclaimCount);
    }

}


