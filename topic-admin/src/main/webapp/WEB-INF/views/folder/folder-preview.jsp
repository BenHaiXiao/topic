<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="folder-addDetail-dialog" class="hide">

            <!--容器-->
            <div class="container">
                <div class="span12">

                </div>

            </div>

        <div class="form-actions">
            <button type="button" class="btn blue" id="save-preview-btn"><i class="icon-ok"></i>保存</button>
            <button type="button" class="btn" id="cancel-preview-btn">关闭</button>
        </div>

</div>
<script src="${ctx}/static/js/folder/folder-preview.js" type="text/javascript"></script>
