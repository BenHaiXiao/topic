<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- BEGIN 左边栏 -->
<div class="page-sidebar nav-collapse collapse">
    <!-- BEGIN 左边栏 菜单 -->
    <!-- BEGIN 左边栏 显示隐藏按钮 -->
    <div class="sidebar-toggler hidden-phone" style="display:none;"></div>
    <!-- BEGIN 左边栏 显示隐藏按钮 -->
    <ul class="page-sidebar-menu" id="menu-container">
        <li class="start">
            <a href="/">
                <i class="icon-home"></i><span class="title">home</span>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);">
                <i class="icon-thumbs-up-alt"></i><span class="title">sloth后台管理</span><span class="arrow"></span>
            </a>
            <ul class="sub-menu" style="display:block">
                <li>
                    <a href="/sloth/folder/mgr"><span>目录管理</span></a>
                </li>
            </ul>
            <ul class="sub-menu" style="display:block">
                <li>
                    <a href="/sloth/article/mgr"><span>文章管理</span></a>
                </li>
            </ul>
            <ul class="sub-menu" style="display:block">
                <li>
                    <a href="/sloth/post/mgr"><span>评论管理</span></a>
                </li>
            </ul>
            <ul class="sub-menu" style="display:block">
                <li>
                    <a href="/sloth/acclaim/mgr"><span>点赞管理</span></a>
                </li>
            </ul>
            <ul class="sub-menu" style="display:block">
                <li>
                    <a href="/sloth/follow/mgr"><span>关注管理</span></a>
                </li>
            </ul>
            <a href="javascript:void(0);">
                <i class="icon-thumbs-up-alt"></i><span class="title">sloth权限管理</span><span class="arrow"></span>
            </a>
            <ul class="sub-menu" style="display:block">
                <li>
                    <a href="/sloth/acl/mgr"><span>权限管理</span></a>
                </li>
                <li>
                    <a href="/sloth/acl/user"><span>用户管理</span></a>
                </li>
                <li>
                    <a href="/sloth/acl/user2role"><span>用户角色关系管理</span></a>
                </li>
                <li>
                    <a href="/sloth/acl/role"><span>角色管理</span></a>
                </li>
                <li>
                    <a href="/sloth/acl/role2permission"><span>角色权限关系管理</span></a>
                </li>
            </ul>
            <ul class="sub-menu" style="display:block">
                <li>
                    <a href="/test/mgr"><span>测试</span></a>
                </li>
            </ul>
        </li>
     </ul>
    <!-- END 左边栏 MENU -->
</div>
<!-- END 左边栏 -->
