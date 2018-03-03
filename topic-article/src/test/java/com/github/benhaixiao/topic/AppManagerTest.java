package com.github.benhaixiao.topic;

import com.github.benhaixiao.topic.folder.Folder;
import com.github.benhaixiao.topic.user.User;
import com.github.benhaixiao.topic.app.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xiaobenhai
 *         on 9/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class AppManagerTest {
    @Autowired
    private AppManager appManager;

//    @Test
//    public void findTest() {
//        App app = appManager.find("erd_app");
//        Folder folder = app.getFolder("001.0001.0001");
//        Article article = new Article();
//        article.setFolderPath(folder.getPath());
//        article.setTitle("topic测试");
//        article.setBrief("概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要概要");
//        article.setCreator(new User("123456789", "nick", "http://www.baidu.com"));
//        Marks marks = new Marks();
//        Tag tagA = new Tag("A");
//        Tag tagB = new Tag("B");
//        marks.add(tagA);
//        marks.add(tagB);
//        article.setMarks(marks);
//        Thumbs thumbs = new Thumbs();
//        thumbs.addImage(new Image("http://image.png", 10, 100));
//        thumbs.addImage(new Image("http://image.jpg", 10000, 290));
//        article.setThumbs(thumbs);
//
//        Content content = new Content();
//        ImageSection imageSection = new ImageSection();
//        ImageSection.Attr imageAttr = new ImageSection.Attr();
//        imageAttr.setWidth(102);
//        imageAttr.setHeight(103);
//        imageSection.setAttr(imageAttr);
//        Image image = new Image();
//        image.setUrl("http://121344555");
//        image.setAltText("altText");
//        imageSection.setImage(image);
//        content.addSection(imageSection);
//
//        TextSection textSection = new TextSection();
//        TextSection.Attr textAttr = new TextSection.Attr();
//        textAttr.setLength("color".length());
//        textSection.setAttr(textAttr);
//        textSection.setText("AAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBCCCCCCCCCCCCCCCCCCC");
//        content.addSection(textSection);
//
//        article.setContent(content);
//        article.setCreateTime(new Date());
//        article.setUpdateTime(new Date());
//
//        folder.storeArticle(article);
//
//        folder.getArticle(article.getNodeId());
//
//    }

    @Test
    public void testSaveFolder() {
        App app = appManager.find("erd_app");
        Folder folder = new Folder(app.getAppContext(), ".");
        folder.setName("test folder");
        folder.setCreator(new User("123456789", "nick", "http://www.baidu.com"));
        folder.setDescription("description...");
        folder.setPath("001.0001.0001");
        folder.setValid(1);
        folder.storeFolder(folder);
    }
}
