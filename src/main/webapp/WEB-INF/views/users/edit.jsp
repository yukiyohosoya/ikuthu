<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="action" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actShop" value="${ForwardConst.ACT_SHOP.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commInd" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="comSHLOGIN" value="${ForwardConst.CMD_SHOW_LOGIN.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>ユーザー情報 編集ページ</h2>
        <p>（パスワードは変更する場合のみ入力してください）</p>
        <form method="POST" action="<c:url value='?action=${action}&command=${commUpd}' />">
            <c:import url="_form.jsp" />
        </form>

        <p>
            <a href="#" onclick="confirmDestroy();">アカウントを削除する</a>
        </p>
        <form method="POST" action="<c:url value='?action=${action}&command=${commDel}' />">
            <input type="hidden" name="${AttributeConst.US_ID.getValue()}" value="${user.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        </form>
        <script>
            function confirmDestroy() {
                if (confirm("本当に削除してよろしいですか？")) {
                    document.forms[1].submit();
                }
            }
        </script>


        <p><a href="<c:url value='?action=${actShop}&command=${commInd}' />">ショップに戻る</a></p>


    </c:param>
</c:import>