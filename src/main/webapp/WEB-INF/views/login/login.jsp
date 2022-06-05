<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="command" value="${ForwardConst.CMD_LOGIN.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
     <c:param name="content">
        <h2>ログイン</h2>
        <form method="POST" action="<c:url value='?action=${action}&command=${command}' />">
            <label for="${AttributeConst.US_MAIL.getValue()}">社員番号</label><br/>
            <input type="text" name="${AttributeConst.US_MAIL.getValue()}" value="${mailaddress}" />
            <br/><br/>
            <button type="submit">ログイン</button>
        </form>
    </c:param></c:import>