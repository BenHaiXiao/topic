<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style text="css/text">
    .layout-left{
        float: left;
        width: 400px;
    }
    .layout-right{
        float: right;
        /*width: 300px;*/
    }
    .layout-up{
        height: 300px;
    }
    textarea{
        height: 200px;
        width: 220px;
    }
</style>
<div id="article-add-dialog" class="hide">
    <form action="#" class="form-horizontal" id="articleForm" role="form">
        <input type="hidden" id="id">
        <div class="control-group ">
            <label class="control-label">appid：</label>
            <div class="controls">
                <select id="appId">
                    <option value="erd_app">耳洞</option>
                    <option value="juba_app">剧吧</option>
                    <option value="tieba_app">贴吧</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">目录路径:folderPath<span class="required">*</span></label>
            <div class="controls"><input type="text" id="folderPath" data-required="1"  ></div>
        </div>
        <div class="control-group">
            <label class="control-label">题目title:</label>
            <div class="controls"><input type="text" id="title" data-required="1"  ></div>
        </div>
        <div class="control-group">
            <label class="control-label">简介brief:</label>
            <div class="controls">
                <textarea type="text" id="brief" rows="3" style="width:300px;height: 150px"></textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">标签:</label>
            <div class="controls"><input type="text" id="tags" data-required="1"  ></div>
        </div>

        <%--<div class="control-group">
            <label class="control-label">上传图片id:imageToContent;上传至Content</label>
            <div class="controls">
                <img id="imageToContent" src="" style="width:300px;height: 300px">
                <div id="imageToContent_upload"style="color: #005580"></div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">上传文本id:textToContent;上传文本至Content</label>
            <div class="controls">
             <textarea type="text" id="textContent" rows="3" style="width: 300px"aria-multiline="true"></textarea>
               <div><span id="textContent_upload"style="color: #665580">文章上传按钮</span></div>
            </div>
        </div>--%>
        <!-- Content容器-->
        <%--<div class="row-fluid" id="articleContentsDiv">
          <ol></ol>
        </div>--%>
        <div class="control-group">
            <label class="control-label">有效标识valid<span class="required">*</span></label>
            <div class="controls">
                <select id="validFlag">
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">创建者uid</label>
            <div class="controls"><input type="text" id="uid" ></div>
        </div>

        <%--qupeng 图片内容上传窗口--%>
        <div class="control-group">
            <label class="control-label">图片与文本</label>
            <div class="controls">
                <button type="button" class="btn" id="thumb-content-btn" >打开窗口</button>
            </div>
        </div>

        <div class="form-actions">
            <button type="button" class="btn blue" id="save-btn"><i class="icon-ok"></i>保存</button>
            <button type="button" class="btn" id="cancel-btn">关闭</button>
        </div>
    </form>
</div>

<div id="thumb-upload-dialog" class="hide" style="width: 1000px">
        <form action="#" class="form-horizontal" id="articleThumbForm" role="form">
            <div class="layout-up">
                <div class="layout-left">
                    <div class="control-group">
                        <%--上传图片id:imageToContent;上传至Content--%>
                        <label class="control-label">上传图片</label>
                        <div class="controls">
                            <img id="imageToContent" src="" style="width:200px;height: 200px">
                            <div id="imageToContent_upload"style="color: #005580"></div>
                        </div>
                    </div>
                </div>
                <div class="layout-right">
                    <div class="control-group">
                        <%--上传文本id:textToContent;上传文本至Content--%>
                        <label class="control-label">上传文本</label>
                        <div class="controls">
                            <textarea type="text" id="textContent" rows="3" aria-multiline="true"></textarea>
                            <div><span id="textContent_upload"style="color: #665580">文章上传按钮</span></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <%--<button type="button" class="btn blue" id="thumb-save-btn"><i class="icon-ok"></i>保存</button>--%>
                <button type="button" class="btn" id="thumb-cancel-btn">关闭</button>
            </div>
        </form>
        <div class="row-fluid" id="articleContentsDiv">
            <%--<ol></ol>--%>
        </div>
        <%--<div>
            <img src="#" style="width:200px;height: 200px"><a id="1" class="thumb-content-delete" href="#"><i class="icon-trash"></i></a>
            <textarea id="thumb-content-1" style="height: 180px">1argwthbasfgbgbwr</textarea><a id="1" class="thumb-content-delete" href="#"><i class="icon-trash"></i></a>
            <textarea style="height: 180px">2</textarea><a id="2" class="thumb-content-delete" href="#"><i class="icon-trash"></i></a>
            <textarea style="height: 180px">3</textarea><a id="3" class="thumb-content-delete" href="#"><i class="icon-trash"></i></a>
            <img src="#" style="width:200px;height: 200px">
            <textarea style="height: 180px">4</textarea><a id="4" class="thumb-content-delete" href="#"><i class="icon-trash"></i></a>
            <textarea style="height: 180px">5</textarea><a id="5" class="thumb-content-delete" href="#"><i class="icon-trash"></i></a>
            <textarea style="height: 180px">1</textarea>
            <img src="#" style="width:200px;height: 200px">
            <textarea style="height: 180px">2</textarea>
            <textarea style="height: 180px">3</textarea>
            <textarea style="height: 180px">4</textarea>
            <textarea style="height: 180px">5</textarea>
            <img src="#" style="width:200px;height: 200px">
            <textarea style="height: 180px">1</textarea>
            <textarea style="height: 180px">2</textarea>
            <textarea style="height: 180px">3</textarea>
            <textarea style="height: 180px">4</textarea>
            <textarea style="height: 180px">5</textarea>
            <img src="#" style="width:200px;height: 200px">
        </div>--%>
    <a onclick="contentRemove()"></a>
</div>
<script src="${ctx}/static/js/article/article-add.js" type="text/javascript"></script>
