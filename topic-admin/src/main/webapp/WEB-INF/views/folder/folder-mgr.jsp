<%--
  Created by IntelliJ IDEA.
  User: xiaobenhai
  Date: 2015/9/28
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>目录管理</title>
</head>
<body>
<!-- BEGIN 右容器头部-->
<div class="row-fluid">
  <div class="span12">
    <!-- BEGIN 页面标题和面包屑导航 -->
    <h3 class="page-title">
      目录管理
      <small>包含对所有目录管理的操作</small>
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
          <div class="control-group ">
            <label class="control-label">appId:</label>
            <div class="controls">
              <select id="query-appId">
                <option value="erd_app">耳洞</option>
                <option value="juba_app">剧吧</option>
                <option value="tieba_app">贴吧</option>
              </select>
            </div>
          </div>
          <div class="control-group ">
            <label class="control-label"><span class="imageClosex">x</span>父路径：</label>
            <div class="controls">
              <input type="text" id="query-parentFolderPath" placeholder="精确匹配父目录路径" onkeyup="value=this.value.replace(/[^\w\.\/]/ig,'')" >
            </div>
          </div>
          <div class="control-group ">
            <label class="control-label">目录路径：</label>
            <div class="controls">
              <input type="text" id="query-folderPath" placeholder="精确匹配目录路径" onkeyup="value=this.value.replace(/[^\w\.\/]/ig,'')" >
            </div>



    <div class="form-actions">
      <button type="button" class="btn blue" id="searchData">普通查询确定</button>
      <button type="reset" class="btn" id="resetbtn">重置</button>
      <button type="button" class="btn blue" id="searchDataSubFolder">子目录查询确定</button>
      <button type="button" class="btn" id="folder-preview-btn"><i class="icon-color-close"></i>预览</button>
    </div>
        </div>
          </div>
    </form>
</div>
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
    <div id="folder-grid"></div>
  </div>
</div>
</div>
<%@ include file="folder-add.jsp"%>
<%@ include file="folder-preview.jsp"%>
<!-- END 右容器 main-->
<script src="${ctx}/static/js/folder/folder-mgr.js" type="text/javascript"></script>
<script src="${ctx}/static/js/folder/folder-add.js" type="text/javascript"></script>
</body>
</html>
