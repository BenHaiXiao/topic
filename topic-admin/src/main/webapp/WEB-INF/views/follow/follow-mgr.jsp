<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>关注管理</title>
</head>
<body>
<!-- BEGIN 右容器头部-->
<div class="row-fluid">
    <div class="span12">
        <!-- BEGIN 页面标题和面包屑导航 -->
        <h3 class="page-title">
            关注管理
            <small>包含对所有关注的管理操作</small>
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
                        <label class="control-label">app:</label>
                        <div class="controls">
                            <select id="query-appId">
                                <option value="erd_app">耳洞</option>
                                <option value="juba_app">剧吧</option>
                                <option value="tieba_app">贴吧</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group  ">
                        <label class="control-label">关注范围：</label>
                        <div class="controls">
                            <select id="query-typeId">
                                <option value="Article">文章</option>
                                <option value="Folder">目录</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group  ">
                        <label class="control-label">关注对象:</label>
                        <div class="controls">
                            <input type="text" id="query-targetId" placeholder="精确匹配点赞对象ID"  onkeyup="value=this.value.replace(/[^\w\.\/]/ig,'')">
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
            <div id="follow-grid"></div>
        </div>
    </div>
</div>
    <%@ include file="follow-add.jsp"%>
<!-- END 右容器 main-->
 <script src="${ctx}/static/js/follow/follow-add.js" type="text/javascript"></script>
<script src="${ctx}/static/js/follow/follow-mgr.js" type="text/javascript"></script>
</body>
</html>
