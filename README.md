# topic （通用话题系统）

类似今日头条、网易新闻、新浪新闻、YY贴吧等等这类产品具有内容（话题）层级分类、内容展示通常为简单的流状，不存在复杂的内容排版等特点。
并且配套简单的评论、点赞、关注及消息通知等服务。这类型的产品具有一定的共性，特抽象出一套通用模型匹配，减少相关研发工作量，

##  模型定义

topic将内容（话题）的层级分类使用类文件系统的模型进行描述，模型图如下：
![](https://github.com/BenHaiXiao/topic/架构设计/design.jpg)

1. App：描述具体的业务类型。如：业务基本信息描述、权限控制描述、资源限制信息等；
2. Folder：描述具体的分类信息。如：网易新闻的分类（热门、头条、财经等），百度贴吧的各类吧（头条、fun兵、故事趴等）。topic支持对Folder的关注和点赞。Folder路径命名规范见下方（路径命名规范）；
3. Article：描述话题信息。支持简单的流状图片文字声音视频混排（后期增加支持在话题中增加投票和选项等交互模板）。topic支持对Article的点赞和评论操作。

## 功能模块

1.	话题详情
a)	标题
b)	简要
c)	标签
d)	缩略图
e)	发布者
f)	发布时间
g)	修改时间
2.	关注服务
a)	关注总数
b)	是否已关注
c)	关注及取消关注操作
3.	评论服务
a)	评论总数
b)	精选评论
c)	我的评论
d)	查看评
4.	点赞服务
a)	点赞总数
b)	是否已点赞
c)	点赞及取消点赞操作
5.	浏览统计
a)	真实浏览数
b)	加权浏览数
6.	分类
a)	话题总数
b)	最近插入话题
c)	最近更新话题
d)	热门话题
e)	话题列表（插入时间排序、更新时间排序）


注：路径命名规范
[(类型)(ID)]-[(类型)(ID)]- [(类型)(ID)]
	路径以[(类型)(ID)]]元组构成，每一个元组代表一个层级。元组由类型和ID构成，类型表示当前元组所代表的元素，如：0代表目录（Folder）、1代表话题（Article）、2代表评论（Post）、3代表关注（follow）、4代表点赞（acclaim），ID表示元素的唯一标识。
例如：
00001-0000a-1acdedfafafdfda，表示0001目录下的000a子目录的acdedfafafdfda话题。
0000c-00008-1ac23dfdfdfds3-21723dfdvdfd，表示000c目录下的0008子目录的ac23dfdfdfds3话题的1723dfdvdfd评论。

## 系统总体构架

![](https://github.com/BenHaiXiao/topic/架构设计/design.jpg)
## 数据流图

![](https://github.com/BenHaiXiao/topic/数据流图/query.jpg)

![](https://github.com/BenHaiXiao/topic/数据流图/insert.jpg)

![](https://github.com/BenHaiXiao/topic/数据流图/delete.jpg)


## 存储结构
详细见 doc目录 话题存储结构


##  话题接口

详细见 doc目录 的topic接口协议.txt

##  附属功能（topic-message消息服务）

消息服务模块结构设计(基于kafaka)：

![](https://github.com/BenHaiXiao/topic/消息中心/message.jpg)

1. MessageDeliver：负责消息分发
2. MessageBoxFinder：负责用户消息箱查找
3. MsgBox：负责维护和组织所有的用户消息
4. FolderFinder：负责消息目录查找
5. Folder：消息目录，用于消息分类
6. MessageStore：负责消息存储


### 消息中心数据结构模型设计

1、	消息表（reply_message、notice_message）
 
  ``` 
    /**
     * {
     *     id:xxxxxx,      //消息ID
     *     content:{        //消息内容
     *         data:xxxxx,
     *         ...
     *     },
     *     refer:{
     *         type:xxxxxx, //参考类型
     *         id:xxxxxx,   //参考ID
     *         name:xxxxx,  //参考名称
     *         desc:xxxxx,  //参考描述
     *         targetType:xxxx,  //跳转ID类型
     *         targetId:xxxx    //跳转ID
     *     }
     *     sender:{         //发件人
     *        uid:xxxxxx,   //用户ID
     *     },
     *     recipients:[     //收件人列表
     *         {
     *             uid:xxxxxx,
     *         }
     *     ],
     *     attachments:[
     *          {
     *              type:xxxxx,     //附件类型
     *              ...             //附件内容
     *          }
     *     ],
     *     source:{
     *         type:xxxxxx,     //消息源类型
     *         id:xxxxxx,       //消息源ID
     *         name:xxxxxx,     //消息源名称
     *         desc:xxxxxx      //消息源描述
     *     },
     *     status:xxxxxx,       //消息状态
     *     timestamp:xxxxx         //发消息时间
     * }
     */
    ```
2、	消息状态表（box_status）

    ```
    /**
     * {
     *     uid:xxxxxxx
     *     totalMsg:200	//总消息数
     *     newMsg:100 //新消息数
     *     notice:{
     *         totalMsg:100 //总消息数
     *         newMsg:200 //新消息数
     *     }
     *     reply:{
     *         totalMsg:100 //总消息数
     *         mewMsg:200 //新消息数
     *     }
     * }
     */
     ``` 


