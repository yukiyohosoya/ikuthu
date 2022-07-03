<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actLmg" value="${ForwardConst.ACT_LMEVEGOODS.getValue()}" />
<c:set var="actEv" value="${ForwardConst.ACT_EVENT.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<%--    ${event.eventday} のeventは下で定義してるvar。 eventdayはEventviewの変数名。間違えないように！--%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <div class="in_main">

        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2 class="title2">イベント一覧</h2>


            <div class="divTable redTable2">
                <div class="divTableHeading">
                        <div class="divTableRow">
                        <div class="divTableHead">イベント日付</div>
                        <div class="divTableHead">イベント名</div>
                        <div class="divTableHead">詳細</div>
                    </div>
                    </div>
                    <div class="divTableBody">

                        <c:choose>
                            <c:when test="${events_count == 0}">
                                <div class="in_main">
                                   <p>登録イベントがありません</p>
                                </div>
                            </c:when>
                            <c:otherwise>

                    <c:forEach var="event" items="${events}" varStatus="status">
                    <fmt:parseDate value="${event.eventday}" pattern="yyyy-MM-dd" var="eventDay" type="date" />
                    <div class="divTableRow">
                        <div class="divTableCell"><fmt:formatDate value="${eventDay}" pattern='yyyy/MM/dd' /></div>
                        <div class="divTableCell">${event.name}</div>
                        <div class="divTableCell"><a href="<c:url value='?action=${actEv}&command=${commShow}&ev_id=${event.id}' />">詳細</a></div>
                    </div>
                    </c:forEach>
                         </c:otherwise>
                         </c:choose>
                </div>
            </div>
            <div class="redTable2 outerTableFooter">
                <div class="tableFootStyle">
                    <div class="links">
                       （全 ${events_count} 件）　
                        <c:forEach var="i" begin="1" end="${((events_count - 1) / maxRow) + 1}" step="1">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <c:out value="${i}" />&nbsp;
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='?action=${actEv}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </div>

        <p><a href="<c:url value='?action=${actEv}&command=${commNew}' />">新規イベントの登録</a></p>
        </div>
    </c:param>
</c:import>