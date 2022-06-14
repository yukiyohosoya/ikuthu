<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEv" value="${ForwardConst.ACT_EVENT.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />


<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>イベント 一覧</h2>
        <table id="event_list">
            <tbody>
                <tr>
                    <th class="event_name">イベント日付</th>
                    <th class="event_date">イベント名</th>
                </tr>
                 <%--    ${event.eventday} のeventは下で定義してるvar。 eventdayはEventviewの変数名。間違えないように！--%>
                <c:forEach var="event" items="${events}" varStatus="status">
                    <fmt:parseDate value="${event.eventday}" pattern="yyyy-MM-dd" var="eventDay" type="date" />
                    <tr class="row${status.count % 2}">
                        <td class="event_date"> <fmt:formatDate value="${eventDay}" pattern='yyyy/MM/dd' /></td>
                        <td class="event_title">${event.name}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${events_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((events_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actRep}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actEv}&command=${commNew}' />">新規イベントの登録</a></p>

    </c:param>
</c:import>