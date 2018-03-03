<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- BEGIN 页面头部 -->
<header class="header navbar navbar-inverse navbar-fixed-top">
    <!-- BEGIN 顶部导航条 -->
    <div class="navbar-inner">
        <div class="container-fluid">
            <!-- BEGIN LOGO -->
            <a class="brand" href="/"> <img width="30" height="30" src="${ctx}/static/images/music.png" alt="logo" /> <span class="font-small">sloth后台管理系统</span> </a>
            <!-- END LOGO -->
            <!-- BEGIN 响应式时 菜单按钮 -->
            <a href="javascript:;" class="btn-navbar collapsed"
                data-toggle="collapse" data-target=".nav-collapse"> <img src="${ctx}/static/images/menu-toggler.png" alt="" /> </a>
            <!-- END 响应式时 菜单按钮 -->
            <!-- BEGIN 导航条右侧菜单 -->
            <ul class="nav pull-right">
                <!-- BEGIN 用户信息 -->
                <li class="dropdown user"><a href="#" class="dropdown-toggle"
                    data-toggle="dropdown" data-hover="dropdown"
                    data-close-others="true"> <!-- <img width="28" height="28" alt="" src="${ctx}/static/images/avatar.png" /> -->
                        欢迎您：<span class="username" id="userInfo">${sessionScope.admin_user.name}</span> <i class="icon-angle-down"></i> </a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:;"><i class="icon-user"></i> 资料</a>
                        </li>
                        <li class="ip-address"><span><i
                                class="icon-map-marker"></i> <b id="serverIps"></b> </span></li>
                        <li><a href="javascript:;" id="trigger_fullscreen"><i
                                class="icon-move"></i> 全屏</a></li>
                        <li><a href="#" id="J_logout"><i class="icon-key"></i>
                                退出</a></li>
                    </ul>
                </li>
                <!-- END 用户信息 -->
            </ul>
            <!-- END 导航条右侧菜单 -->
        </div>
    </div>
    <!-- END 顶部导航条 -->
</header>
<!-- END 页面头部 -->
<script>
    $(function(){
        $('#J_logout').click(function(){
            window.location.href ="/logout";
            //$.get("/logout");
        });
    });

</script>