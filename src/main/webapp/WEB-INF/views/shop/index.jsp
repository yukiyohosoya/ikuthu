<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actEv" value="${ForwardConst.ACT_EVENT.getValue()}" />
<c:set var="actGd" value="${ForwardConst.ACT_GOODS.getValue()}" />
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

        <h2 class="title1"><c:out value="${sessionScope.select_shop.name}"/></h2>

        <div id="index_shop_list_div">
        <table id="index_shop_list">
            <tbody>
                <c:forEach var="event" items="${events}" varStatus="status">
                    <fmt:parseDate value="${event.eventday}" pattern="yyyy-MM-dd" var="eventDay" type="date" />
                    <tr>
                        <th>●　　<fmt:formatDate value="${eventDay}" pattern='yyyy/MM/dd' /></th>
                        <td>${event.name}</td>
                    </tr>
                </c:forEach>
                    <c:if test="${events_count==0}">
                     <tr>
                        <th>登録イベントはありません</th>
                     </tr>
                    </c:if>
            </tbody>
        </table>
        </div>
        <p class="button1"><a href="<c:url value='?action=${actEv}&command=${commIdx}' />">イベント一覧</a></p>

        <div id="goods_view_box">
            <c:forEach var="goods" items="${goodss}" varStatus="status">
            <div class="goods_view">
                <p class="goods_view_title">画像</p>
                <p class="goods_view_title">${goods.name}</p>
                <div class="goods_view_mainbox">
                    <div class="goods_view_main">
                        <p>a</p>
                        <p>a</p>
                    </div>
                    <div class="goods_view_main">
                        <p>a</p>
                        <p>a</p>
                    </div>
                </div>
                <p>詳細</p>
            </div>
            </c:forEach>
        </div>

        <p><a href="<c:url value='?action=${actGd}&command=${commIdx}' />">グッズ一覧</a></p>
    </c:param>
</c:import>