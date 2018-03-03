<%--
  Created by IntelliJ IDEA.
  User: xiaobenhai
  Date: 2015/9/28
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="folder-add-dialog" class="hide">
  <div class="portlet box blue">
    <div class="portlet-title">
      <div class="caption"><i class="icon-reorder"></i>目录信息</div>
      <div class="tools"></div>
    </div>
    <div class="portlet-body">
      <form action="#" class="form-horizontal form-bordered" id="folderForm" role="form">
          <input type="hidden" id="id">
          <div class="control-group ">
              <label class="control-label">appId:</label>
              <div class="controls">
                  <select id="appId">
                      <option value="erd_app">耳洞</option>
                      <option value="juba_app">剧吧</option>
                      <option value="tieba_app">贴吧</option>
                  </select>
              </div>
          </div>
          <div class="control-group">
          <label class="control-label">父目录路径</label>
          <div class="controls"><input type="text" id="parentFolderPath" ></div>
         </div>
          <div class="control-group">
              <label class="control-label">目录名称name<span class="required">*</span></label>
              <div class="controls"><input type="text" id="name" data-required="1"  ></div>
          </div>
          <div class="control-group">
          <label class="control-label">目录封面</label>
          <div class="controls">
               <img id="icon" src="" style="width:150px;height: 150px">
              <div id="icon_upload"></div>
          </div>
      </div>
          <div class="control-group">
              <label class="control-label">目录描述</label>
              <div class="controls">
                  <textarea type="text" id="desc" rows="3" style="width:300px;height: 150px"></textarea>
              </div>
          </div>
          <div class="control-group">
              <label class="control-label">标签（用英文逗号隔开）</label>
              <div class="controls">
                  <textarea type="text" id="tags"  style="width:300px;height: 150px"></textarea>
              </div>
          </div>
          <div class="control-group">
              <label class="control-label">图片信息thumbs</label>
              <div class="controls">
                  <img id="thumbs" src="" style="width:200px;height: 200px">
                  <div id="thumbs_upload"></div>
              </div>
          </div>
          <div class="row-fluid" id="folderContentsDiv">
              <ol>

              </ol>
          </div>
          <div class="control-group">
              <label class="control-label">有效标识validFlag<span class="required">*</span></label>
              <div class="controls">
                  <select id="validFlag">
                      <option value="1">有效</option>
                      <option value="0">无效</option>
                  </select>
              </div>
          </div>
          <div class="control-group">
              <label class="control-label">用户ID-userId<span class="required">*</span></label>
              <div class="controls"><input type="text" id="userId"  onkeyup="value=this.value.replace(/\D+/g,'')" >
              </div>
          </div>
        <div class="form-actions">
          <button type="button" class="btn blue" id="folder-save-btn"><i class="icon-ok"></i> 保存</button>
          <button type="button" class="btn" id="folder-cancel-btn"><i class="icon-color-close"></i>关闭</button>

        </div>
      </form>
    </div>
  </div>

</div>

