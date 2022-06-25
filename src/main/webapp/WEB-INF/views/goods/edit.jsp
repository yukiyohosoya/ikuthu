<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actGd" value="${ForwardConst.ACT_GOODS.getValue()}" />
<c:set var="actSh" value="${ForwardConst.ACT_SHOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>グッズ編集ページ</h2>
        <form method="POST" action="<c:url value='?action=${actGd}&command=${commUpd}' />" enctype="multipart/form-data">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='?action=${actSh}&command=${commIdx}' />">トップに戻る</a></p>

    </c:param>
</c:import>