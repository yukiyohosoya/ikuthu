<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="action" value="${ForwardConst.ACT_LMEVEGOODS.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>商品　新規登録ページ</h2>

        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}' />" enctype="multipart/form-data">
        販売価格<br />
        <input type="text" name="${AttributeConst.LMEVGS_SELLINGPRICE.getValue()}" value="${event.name}" />
        <br /><br />


        イベント日付<br />
        <input type="date" name="${AttributeConst.EV_DAY.getValue()}" value= "${event.eventday}" />
        <br /><br />

        画像<br/>
        <input type="file" name="picture">

        <br /><br />
        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        <button type="submit">投稿</button>

        </form>
        <p><a href="<c:url value='?action=${action}&command=${commIdx}' />">トップに戻る</a></p>
    </c:param>
</c:import>