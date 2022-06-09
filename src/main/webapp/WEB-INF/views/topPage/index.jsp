<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUs" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actSh" value="${ForwardConst.ACT_SHOP.getValue()}" />

<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ようこそ</h2>
        <h3>ログインするショップを選んでください。</h3>
            <ul>
            <c:forEach var="shop" items="${shops}" varStatus="status">
                 <li>${shop.name}</li>
                 <c:if test="${status.count==0}">
                       <p>ショップがありません</p>
                 </c:if>
            </c:forEach>
            </ul>
        <p><a href="<c:url value='?action=${actUs}&command=${commNew}' />">新規ユーザー登録</a></p>

    </c:param>
</c:import>