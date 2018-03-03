package com.github.benhaixiao.topic.web.controller;

import com.github.benhaixiao.topic.AppManager;
import com.github.benhaixiao.topic.node.NodeMeta;
import com.github.benhaixiao.topic.post.Post;
import com.github.benhaixiao.topic.post.PostService;
import com.github.benhaixiao.topic.shared.PostJacksonDeserializer;
import com.github.benhaixiao.topic.shared.Status;
import com.github.benhaixiao.topic.web.domain.Page;
import com.github.benhaixiao.topic.web.domain.SlothResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.benhaixiao.topic.acclaim.Acclaim;
import com.github.benhaixiao.topic.app.App;
import com.github.benhaixiao.topic.exception.IllegalParameterException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
public class PostController {

    @Autowired
    private AppManager appManager;

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/post",method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse savePost(@PathVariable String appid, @PathVariable String folderPath, @PathVariable String articleId, @RequestParam("data")String postJson, @RequestParam("ticket") String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        try {
            postJson = URLDecoder.decode(postJson, "UTF-8");
        }catch (Exception e){
            throw new IllegalParameterException("illegal data parameter.");
        }
        PostService postService = app.getPostService();
        Post post= PostJacksonDeserializer.deserialize(postJson);
        post.setCreator(app.getUserStore().findOne(uid));
        post.setTargetId(articleId);
        post.setUpdateTime(new Date());
        post.setCreateTime(new Date());
        postService.postArticle(post, articleId);
        List<Post> posts = Lists.newArrayList();
        posts.add(post);
        List<Post> ancestorPosts = postService.findAncestors(posts);
        if(CollectionUtils.isEmpty(ancestorPosts)){
            return new SlothResponse().code(Status.SUCCESS).add("post", post);
        }
        Map<String,Post> ancestorPostMap = Maps.newHashMap();
        for(Post p : posts){
            ancestorPostMap.put(p.getId(),p);
        }
        return new SlothResponse().code(Status.SUCCESS).add("post", post).add("ancestorPosts", ancestorPostMap);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/post/{postId}",method = RequestMethod.DELETE)
    @ResponseBody
    public SlothResponse deletePost(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId,@PathVariable String postId,@RequestParam("ticket") String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        PostService postService = app.getPostService();
        postService.unPostArticle(postId, articleId);
        return new SlothResponse().code(Status.SUCCESS);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/posts",method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse listPost(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId,@RequestParam(value = "last",required = false) Integer last,@RequestParam Integer limit){
        App app = appManager.find(appid);
        PostService postService = app.getPostService();
        last = last == null ? 0 : last;
        List<Post> posts = postService.find(articleId, last, limit + 1, null);
        if(posts == null){
            posts = Lists.newArrayList();
        }
        NodeMeta articleNodeMeta = app.getFolder(folderPath).getNodeMeta(articleId);
        Page page = new Page().setTotal(articleNodeMeta.getStats().getPostCount());
        if(posts.size() >= limit){
            page.setCount(posts.size() - 1).setLast(last + posts.size() - 1).setMore(true);
            posts = posts.subList(0,posts.size() - 1);
        }else{
            page.setCount(posts.size()).setLast(last + posts.size()).setMore(false);
        }
        List<Post> ancestorPosts = postService.findAncestors(posts);
        if(CollectionUtils.isEmpty(ancestorPosts)){
            return new SlothResponse().code(Status.SUCCESS).add("posts", posts);
        }
        Map<String,Post> ancestorPostMap = Maps.newHashMap();
        for(Post post : posts){
            ancestorPostMap.put(post.getId(),post);
        }
        return new SlothResponse().code(Status.SUCCESS).add("posts", posts).add("ancestorPosts", ancestorPostMap);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/post/{postId}/acclaim",method = RequestMethod.POST)
    @ResponseBody
    public SlothResponse doAcclaim(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId,@PathVariable("postId")String postId,@RequestParam(value = "ticket") String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        PostService postService = app.getPostService();
        long acclaimCount = postService.acclaimPost(uid, postId);
        return new SlothResponse().code(Status.SUCCESS).add("acmCnt",acclaimCount);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/post/{postId}/acclaim",method = RequestMethod.DELETE)
    @ResponseBody
    public SlothResponse removeAcclaim(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId,@PathVariable("postId")String postId,@RequestParam(value = "ticket") String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        PostService postService =  app.getPostService();
        long acclaimCount = postService.unAcclaimPost(uid, postId);
        return new SlothResponse().code(Status.SUCCESS).add("acmCnt",acclaimCount);
    }

    @RequestMapping(value = "app/{appid}/folder/{folderPath}/article/{articleId}/post/{postId}/acclaim",method = RequestMethod.GET)
    @ResponseBody
    public SlothResponse getAcclaim(@PathVariable String appid,@PathVariable String folderPath,@PathVariable String articleId,@PathVariable("postId")String postId,@RequestParam("ticket") String ticket){
        App app = appManager.find(appid);
        String uid =  ticket;
        PostService postService = app.getPostService();
        Acclaim acclaim = postService.getPostAcclaim(uid, postId);
        acclaim.setUid(null);
        acclaim.setTargetId(null);
        return new SlothResponse().code(Status.SUCCESS).add("acclaim", acclaim);
    }

}
