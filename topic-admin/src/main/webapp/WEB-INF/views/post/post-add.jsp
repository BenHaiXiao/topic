<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!--begin 专辑小组-->
<div id="post-add-dialog" class="hide">
    <div class="portlet box blue">
        <div class="portlet-title">
            <div class="caption"><i class="icon-reorder"></i>评论信息</div>
            <div class="tools"></div>
        </div>
        <div class="portlet-body">
            <form action="#" class="form-horizontal form-bordered" id="postForm" role="form">
                <input type="hidden" id="id">
                <div class="control-group  ">
                    <label class="control-label">APPID：</label>
                    <div class="controls">
                        <select id="appid">
                            <option value="erd_app">耳洞</option>
                            <option value="juba_app">剧吧</option>
                            <option value="tieba_app">贴吧</option>
                        </select>
                    </div>
                </div>
                <div class="control-group ">
                    <label class="control-label">文章ID，评论对象ID<span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="targetId">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">评论者ID</label>
                    <div class="controls">
                        <input type="text" id="userId" readonly/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">评论内容<span class="required">*</span></label>
                    <div class="controls">
                        <textarea type="text" id="content" style="width:250px;height: 200px"></textarea>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">上传评论图片</label>
                    <div class="controls">
                        <img id="postImageToContent" src="" style="width:300px;height: 300px">
                        <span id="postImageToContent_upload"style="color: #005580"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">上传评论文字</label>
                    <div class="controls">
                        <textarea type="text" id="postTextContent" rows="3" style="width: 300px"aria-multiline="true"></textarea>
                         <label id="postTextContent_upload"style="color: #665580">文章上传按钮</label>
                    </div>

                </div>

                <div class="row-fluid" id="postContentsDiv">
                    <ol></ol>
                </div>
                <div class="form-actions">
                    <button type="button" class="btn blue" id="post-save"><i class="icon-ok"></i> 保存</button>
                    <button type="button" class="btn" id="post-cancel"><i class="icon-color-close"></i>关闭</button>
                </div>
            </form>
        </div>
    </div>

</div>
<!--end 专辑评论-->