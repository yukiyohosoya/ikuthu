<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="${AttributeConst.EV_NAME.getValue()}" >販売名</label><br />
<input type="text" name="${AttributeConst.EV_NAME.getValue()}" value="${event.name}" />
<br /><br />

<label for="${AttributeConst.EV_DAY.getValue()}" >イベント日付</label><br />
<input type="date" name="${AttributeConst.EV_DAY.getValue()}" value= "${event.eventday}" />
<br /><br />

<br /><br />


<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>