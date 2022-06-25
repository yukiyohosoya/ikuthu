<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUs" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actShop" value="${ForwardConst.ACT_SHOP.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />

<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<!DOCTYPE html>
<html lang="ja">
    <head>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <meta charset="UTF-8">
        <title><c:out value="Ikuthu？"/></title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
            <c:choose>
                <c:when test="${sessionScope.select_shop !=null}"><h1><a href="<c:url value='?action=${actShop}&command=${commIdx}' />">Ikuthu？</a></h1></c:when>
                <c:otherwise><h1>Ikuthu？</h1></c:otherwise>
            </c:choose>

                <div id="header_menu">
                        <c:if test="${sessionScope.login_user !=null}">
                            <div id="user_name">
                                <c:out value="${sessionScope.login_user.name}"/>
                                 &nbsp;さん&nbsp;&nbsp;
                                <a href="<c:url value='?action=${actAuth}&command=${commOut}' />">ログアウト</a>
                                &nbsp;&nbsp;&nbsp;
                                <a href="<c:url value='?action=${actUs}&command=${commEdt}' />">情報編集</a>
                            </div>
                        </c:if>


            　
                   　   　<c:if test="${sessionScope.select_shop !=null}">
                         <div id="shop_name">
                             <c:out value="${sessionScope.select_shop.name}"/>
                             &nbsp;&nbsp;&nbsp;&nbsp;
                             <a href="<c:url value='?action=${actTop}&command=${commIdx}' />">ショップ変更</a>&nbsp;
                             &nbsp;&nbsp;&nbsp;&nbsp;
                             <a href="<c:url value='?action=${actShop}&command=${commShow}' />">ショップ詳細</a>&nbsp;
                        </div>
                        </c:if>


                </div>
            </div>
                <div id="main">
                    <div id="content">${param.content}</div>
                </div>
            </div>

            <div id="footer">by taro kirameki.</div>
        </div>

    </body>
</html>