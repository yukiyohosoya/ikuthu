<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUs" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actSh" value="${ForwardConst.ACT_SHOP.getValue()}" />

<c:set var="commSl" value="${ForwardConst.CMD_SELECT.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
    <c:out value="${loginError}"/>
    <div class="in_main">

          <p class="title2">ショップ選択</p>

          <c:choose>
            <c:when test="${shops_count == 0}">
                    <div class="exp">
                        <p>ショップがありません</p>
                        <p>ショップを作成してみてください！</p>
                    </div>
            </c:when>
            <c:otherwise>
                <div class="exp">
                    <p>管理するショップを選んでください。</p>
                </div>
                <div class="listdesign2">
                <ol class="listdesign2">
                    <c:forEach var="shop" items="${shops}" varStatus="status">
                        <li><a href="<c:url value='?action=${actSh}&command=${commSl}&sh_id=${shop.id}' />">${shop.name}</a></li>
                    </c:forEach>
                </ol>
                </div>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='?action=${actSh}&command=${commNew}' />">新規ショップ登録</a></p>

    </div>

    </c:param>
</c:import>