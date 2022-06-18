<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="command" value="${ForwardConst.CMD_LOGIN.getValue()}" />
<c:set var="actUs" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="comNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <div class="in_main">

        <h3>在庫！いまいくつだっけ…</h3>
        <div class="exp">
            <p>そんな時に『Ikuthu？』</p>
            <p>オンラインで簡単管理</p>
        </div>



        <c:if test="${loginError}">
            <div id="flush_error">
                メールアドレスかパスワードが間違っています。
            </div>
        </c:if>
       <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ログイン</h2>

        <form method="POST" action="<c:url value='?action=${action}&command=${command}' />">
            <label for="${AttributeConst.US_MAIL.getValue()}">メールアドレス</label><br/>
            <input type="text" name="${AttributeConst.US_MAIL.getValue()}" value="${mailaddress}" />
            <br/><br/>
            <label for="${AttributeConst.US_PASS.getValue()}">パスワード</label><br />
            <input type="password" name="${AttributeConst.US_PASS.getValue()}" />
            <br/><br/>
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit" class="button">ログイン</button>
        </form>
         <p  class="button007"><a href="<c:url value='?action=${actUs}&command=${comNew}' />" class="btn btn--orange btn--radius">ユーザー登録</a></p>
    </div>
    </c:param>
    </c:import>