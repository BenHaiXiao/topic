<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>评论管理</title>
</head>
<body>
<!-- BEGIN 右容器头部-->
<div class="row-fluid">
    <div class="span12">
        <!-- BEGIN 页面标题和面包屑导航 -->
        <h3 class="page-title">
            评论管理
            <small>包含对所有评论的管理操作</small>
        </h3>
        <!-- END 页面标题和面包屑导航 -->
    </div>
</div>
<!-- END 右容器头部-->
<!-- BEGIN 右容器 main-->
<div class="row-fluid">
    <div class="portlet box default">
        <div class="portlet-title">
            <div class="caption"><i class="icon-reorder"></i>查找</div>
            <div class="tools"><a href="javascript:;" class="expand"></a></div>
        </div>
        <div class="portlet-body" style="display: none;">
            <form id="queryForm" action="#" class="form form-horizontal">
                <div class="row-fluid">
                    <div class="control-group  ">
                        <label class="control-label">评论对象类型:</label>
                        <div class="controls">
                            <select id="query-appId">
                                <option value="erd_app">耳洞</option>
                                <option value="juba_app">剧吧</option>
                                <option value="tieba_app">贴吧</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group  ">
                        <label class="control-label">文章ID:</label>
                        <div class="controls">
                            <input type="text" id="query-articleId" placeholder="精确匹配ID" onkeyup="value=this.value.replace(/[^\w\.\/]/ig,'')" >
                        </div>
                    </div>
                    <div class="control-group  ">
                        <label class="control-label">评论ID:</label>
                        <div class="controls">
                            <input type="text" id="query-postId" placeholder="精确匹配ID"  onkeyup="value=this.value.replace(/[^\w\.\/]/ig,'')">
                        </div>
                    </div>
                <div class="form-actions">
                    <button type="button" class="btn blue" id="searchData">确定</button>
                    <button type="reset" class="btn" id="resetbtn">重置</button>
                </div>
                </div>
            </form>
        </div>
    </div>

    <div class="portlet box blue" id="J_result">
        <div class="portlet-title">
            <div class="caption"><i class="icon-reorder"></i>结果</div>
            <div class="tools"><a href="javascript:;" class="collapse"></a></div>
            <div class="actions">
                <a href="javascript:;" class="btn green mini" id="add-btn"><i class="icon-plus"></i> 新增</a>
                <a href="javascript:;" class="btn red mini" id="delete-selected"><i class="icon-trash"></i> 删除</a>
            </div>
        </div>
        <div class="portlet-body">
            <div id="post-grid"></div>
        </div>
    </div>
</div>
<%@ include file="post-add.jsp"%>
<!-- END 右容器 main-->
<script src="${ctx}/static/js/post/post-mgr.js" type="text/javascript"></script>
<script src="${ctx}/static/js/post/post-add.js" type="text/javascript"></script>
</body>
</html>
