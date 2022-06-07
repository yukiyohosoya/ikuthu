<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="${AttributeConst.US_MAIL.getValue()}">メールアドレス</label><br />
<input type="text" name="${AttributeConst.US_MAIL.getValue()}" value="${user.mailaddress}" />
<br /><br />

<label for="${AttributeConst.US_NAME.getValue()}">名前</label><br />
<input type="text" name="${AttributeConst.US_NAME.getValue()}" value="${user.name}" />
<br /><br />

<label for="${AttributeConst.US_PASS.getValue()}">パスワード</label><br />
<input type="password" name="${AttributeConst.US_PASS.getValue()}" />
<br />
<label for="${AttributeConst.US_PASS_K.getValue()}">確認用パスワード</label><br />
<input type="password" name="${AttributeConst.US_PASS_K.getValue()}" />
<br /><br />


<input type="hidden" name="${AttributeConst.US_ID.getValue()}" value="${user.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">登録</button>