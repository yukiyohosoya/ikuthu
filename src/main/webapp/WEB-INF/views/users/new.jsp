<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="acAU" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="comSHLOGIN" value="${ForwardConst.CMD_SHOW_LOGIN.getValue()}" />


<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>新規ユーザー登録</h2>

        <form method="POST" action="<c:url value='?action=${action}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
        </form>
        <p><a href="<c:url value='?action=${acAu}&command=${comShLo}' />">ログインに戻る</a></p>
    </c:param>
</c:import>