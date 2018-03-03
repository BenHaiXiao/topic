<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8"> <![endif]-->
<!--[if IE 9]> <html class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
    <title><sitemesh:title default="Admin" /> -sloth后台管理系统</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />

    <link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
    <!-- BEGIN 全局样式 -->
    <link href="${ctx}/lib/jqueryui/css/jquery-ui.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/bootstrap/css/bootstrap-responsive.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/font-awesome/css/font-awesome.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/metronic/css/style-metro.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/metronic/css/style.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/metronic/css/style-responsive.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/metronic/css/default.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/uniform/css/uniform.default.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/select2/css/select2.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/select2/css/select2-bootstrap.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/tinygrid/css/tinygrid.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/fancybox/css/jquery.fancybox.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/artdialog/5.0.4/skins/default.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/form-datetimepicker/css/bootstrap-datetimepicker.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/lib/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/static/css/custom.css" type="text/css" rel="stylesheet" />
    <!-- END 全局样式 -->
    <script src="${ctx}/lib/jquery/js/jquery-2.0.3.js" type="text/javascript"></script>
    <script src="${ctx}/lib/jqueryui/js/jquery-ui.js" type="text/javascript"></script>
    <script src="${ctx}/lib/bootstrap/js/bootstrap.js" type="text/javascript"></script>
    <script src="${ctx}/lib/jquery/js/jquery.blockui.js" type="text/javascript"></script>
    <script src="${ctx}/lib/jquery/js/jquery.cookie.js" type="text/javascript"></script>
    <script src="${ctx}/lib/jquery/js/jquery.slimscroll.js" type="text/javascript"></script>
    <script src="${ctx}/lib/uniform/js/jquery.uniform.js" type="text/javascript"></script>
    <script src="${ctx}/lib/select2/js/select2.js" type="text/javascript"></script>
    <script src="${ctx}/lib/fancybox/js/jquery.fancybox.js" type="text/javascript"></script>
    <script src="${ctx}/lib/tinygrid/js/jquery.tinygrid.js" type="text/javascript"></script>
    <script src="${ctx}/lib/artdialog/5.0.4/jquery.artDialog.js" type="text/javascript"></script>
    <script src="${ctx}/lib/base/js/app.js" type="text/javascript"></script>
    <script src="${ctx}/lib/form-datetimepicker/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
    <script src="${ctx}/lib/form-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/songlib/tool.js" type="text/javascript"></script>
    <script type="text/javascript">
        document.write("<script type='text/javascript' "
                + "src='${ctx}/lib/uploadify/jquery.uploadify.js?" + new Date()
                + "'></s" + "cript>");

        window.ctx="${ctx}";
        $(function() { App.init(); });
    </script>
    <sitemesh:head/>
</head>

<body class="page-header-fixed page-sidebar-fixed">
	<%@ include file="/WEB-INF/layouts/header.jsp"%>
	<!-- BEGIN 页面主容器 -->
	<div class="page-container row-fluid">
	    <%@ include file="/WEB-INF/layouts/menu.jsp"%>
	    <!-- BEGIN PAGE -->
        <div class="page-content">
            <!-- BEGIN 右边容器-->
            <div class="container-fluid">
	            <sitemesh:body/>
            </div>
            <!-- END 右边容器-->
        </div>
        <!-- END PAGE -->
    </div>
	<%@ include file="/WEB-INF/layouts/footer.jsp"%>
</body>
</html>