package com.github.benhaixiao.topic.web.controller;

import com.github.benhaixiao.topic.acclaim.AcclaimService;
import com.github.benhaixiao.topic.domain.Section;
import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.shared.ArticleJacksonDeserializer;
import com.github.benhaixiao.topic.web.domain.Page;
import com.github.benhaixiao.topic.web.domain.SlothResponse;
import com.google.common.collect.Maps;
import com.github.benhaixiao.topic.AppManager;
import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.article.Article;
import com.github.benhaixiao.topic.domain.ImageSection;
import com.github.benhaixiao.topic.exception.IllegalParameterException;
import com.github.benhaixiao.topic.exception.NoSuchAcclaimException;
import com.github.benhaixiao.topic.shared.MarkUtils;
import com.github.benhaixiao.topic.shared.Status;
import com.github.benhaixiao.topic.web.bs2.FileUploadService;
import com.github.benhaixiao.topic.web.exception.FileUploadException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 *
 */
@Controller
@RequestMapping("/")
public class ArticleController {

    @Autowired
    private AppManager appManager;
    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}", method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse getArticle(@PathVariable String appid, @PathVariable("folderPath") String folderPath, @PathVariable String articleId){
        App app = appManager.find(appid);
        Article article = app.getFolder(folderPath).getArticle(articleId);
        return new SlothResponse().code(Status.SUCCESS).add("article",article);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article", method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse saveArticle(@PathVariable String appid,@PathVariable("folderPath") String folderPath,@RequestParam("data") String articleJson,HttpServletRequest request,@RequestParam("ticket")String ticket){
        App app = appManager.find(appid);
        Folder folder = app.getFolder(folderPath);
        String uid =  ticket;
        try {
            articleJson = URLDecoder.decode(articleJson, "UTF-8");
        }catch (Exception e){
            throw new IllegalParameterException("illegal data parameter.");
        }
        Article article = ArticleJacksonDeserializer.deserialize(articleJson);
        //设置marks
        article.setMarks(MarkUtils.supplement(article.getMarks(),app.getDict()));
        article.setFolderPath(folderPath);
        article.setCreator(app.getUserStore().findOne(uid));
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());

        Map<String,MultipartFile> fileMap = Maps.newHashMap();
        for(Section section : article.getContent().getSections()){
            if(Section.Type.IMAGE != section.getType()){
                continue;
            }
            String fileIndex = ((ImageSection)section).getImage().getUrl();
            if(!fileIndex.startsWith("@")){
                continue;
            }
            MultipartFile file = ((MultipartHttpServletRequest)request).getFile(fileIndex);
            try {
                BufferedImage imageFile = ImageIO.read(file.getInputStream());
                ImageSection.Attr attr = new ImageSection.Attr();
                attr.setWidth(imageFile.getWidth());
                attr.setHeight(imageFile.getHeight());
                ((ImageSection) section).setAttr(attr);
            }catch (Exception e){
                throw new FileUploadException("read image width and height error.");
            }
            fileMap.put(fileIndex, ((MultipartHttpServletRequest)request).getFile(fileIndex));
        }
        Map<String,String> fileUploadResult = fileUploadService.uploadToBs2(fileMap);
        for(Section section : article.getContent().getSections()){
            if(Section.Type.IMAGE != section.getType()){
                continue;
            }
            String fileIndex = ((ImageSection)section).getImage().getUrl();
            ((ImageSection) section).getImage().setUrl(fileUploadResult.get(fileIndex));
        }
        folder.storeArticle(article);
        return new SlothResponse().code(Status.SUCCESS).add("article",article);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}",method = RequestMethod.DELETE)
    @ResponseBody
    public SlothResponse deleteArticle(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId){
        App app = appManager.find(appid);
        Folder folder = app.getFolder(folderPath);
        folder.removeArticle(articleId);
        return new SlothResponse().code(Status.SUCCESS);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/articles",method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse listArticle(@PathVariable String appid,@PathVariable String folderPath,@RequestParam(value = "last",required = false) Integer last,@RequestParam Integer limit){
        App app = appManager.find(appid);
        Folder folder = app.getFolder(folderPath);
        last = last == null ? 0 : last;
        List<Article> articles = folder.listArticle(last, limit + 1);
        Page page = new Page().setTotal(folder.getStats().getTopicCount());
        if(CollectionUtils.isEmpty(articles)){
            return new SlothResponse().code(Status.SUCCESS).add("page", page);
        }
        if(articles.size() >= limit){
            page.setCount(articles.size() - 1).setLast(last + articles.size()).setMore(true);
        }else{
            page.setCount(articles.size()).setLast(last + articles.size()).setMore(false);
        }
        return new SlothResponse().code(Status.SUCCESS).add("page",page).add("articles", articles);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/acclaim",method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse doAcclaim(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId,@RequestParam("ticket") String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        AcclaimService acclaimService = app.getAcclaimService();
        long acclaimCount = acclaimService.acclaimArticle(uid,articleId);
        return new SlothResponse().code(Status.SUCCESS).add("acmCnt",acclaimCount);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/acclaim",method = RequestMethod.DELETE)
    @ResponseBody
    public SlothResponse removeAcclaim(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId,@RequestParam("ticket") String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        AcclaimService acclaimService = app.getAcclaimService();
        long acclaimCount = acclaimService.unAcclaimArticle(uid, articleId);
        return new SlothResponse().code(Status.SUCCESS).add("acmCnt",acclaimCount);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/acclaim",method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse getAcclaim(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId,@RequestParam("ticket") String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        AcclaimService acclaimService = app.getAcclaimService();
        Acclaim acclaim;
        try {
             acclaim = acclaimService.getArticleAcclaim(uid, articleId);
        }catch (NoSuchAcclaimException e) {
            return new SlothResponse().code(Status.NO_SUCH_ACCLAIM).add("msg",e.getMessage());
        }
            acclaim.setUid(null);
            acclaim.setTargetId(null);
            return new SlothResponse().code(Status.SUCCESS).add("acclaim", acclaim);
    }
}
