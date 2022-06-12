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
<%--         <c:if test="${loginError}"> --%>
<!--             <div id="flush_error"> -->
<!--                 店舗ログインに失敗しました。 -->
<!--             </div> -->
<%--         </c:if> --%>
        <h2>ようこそ</h2>
        <c:choose>
            <c:when test="${shops_count == 0}">
                <h3>ショップがありません</h3>
                <p>ショップを作成してみてください！</p>
            </c:when>
            <c:otherwise>
               <h3>管理するショップを選んでください。</h3>
                <ul>
                <c:forEach var="shop" items="${shops}" varStatus="status">

                     <li><a href="<c:url value='?action=${actSh}&command=${commSl}&sh_id=${shop.id}' />">${shop.name}</a></li>


                </c:forEach>
                </ul>
            </c:otherwise>

        </c:choose>
        <p><a href="<c:url value='?action=${actSh}&command=${commNew}' />">新規ショップ登録</a></p>



    </c:param>
</c:import>