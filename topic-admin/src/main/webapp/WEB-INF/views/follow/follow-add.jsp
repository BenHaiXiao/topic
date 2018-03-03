<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!--begin 添加关注-->
<div id="follow-add-dialog" class="hide">
    <div class="portlet box blue">
        <div class="portlet-title">
            <div class="caption"><i class="icon-reorder"></i>关注信息</div>
            <div class="tools"></div>
        </div>
        <div class="portlet-body">
            <form action="#" class="form-horizontal form-bordered" id="acclaimForm" role="form">
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
                <div class="control-group  ">
                    <label class="control-label">关注范围：</label>
                    <div class="controls">
                        <select id="typeId">
                            <option value="Article">文章</option>
                            <option value="Post">评论</option>
                            <option value="Folder">目录</option>
                        </select>
                    </div>
                </div>
                <div class="control-group ">
                    <label class="control-label">关注对象ID<span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="targetId" placeholder="精确匹配关注对象ID">
                    </div>
                </div>
                <div class="control-group ">
                    <label class="control-label">用户Uid<span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="uid">
                    </div>
                </div>
                <div class="form-actions">
                    <button type="button" class="btn blue" id="follow-save"><i class="icon-ok"></i> 保存</button>
                    <button type="button" class="btn" id="follow-cancel"><i class="icon-color-close"></i>关闭</button>
                </div>
            </form>
        </div>
    </div>

</div>
<!--end 专辑评论-->