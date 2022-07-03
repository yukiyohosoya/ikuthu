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
            <div class="in_main" id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

            <h2 class="title2"><c:out value="${sessionScope.select_shop.name}"/></h2>

        <div class="compare-box">
        <div class="compare-wrap">
        <div class="compare-head">イベント</div>
        <div class="compare">
        <ul class="list">
                <c:forEach var="event" items="${events}" varStatus="status">
                    <fmt:parseDate value="${event.eventday}" pattern="yyyy-MM-dd" var="eventDay" type="date" />
                    <li><fmt:formatDate value="${eventDay}" pattern='yyyy/MM/dd' />　　${event.name}</li>
                </c:forEach>
                <c:if test="${events_count==0}">
                   <li>登録イベントはありません</li>
                </c:if>
        </ul>
        </div>
        </div>
        </div>

<div class="button002">
    <a href="<c:url value='?action=${actEv}&command=${commIdx}' />">イベント一覧</a>
</div>


        <div id="goods_view_box">
            <div id="goods_view-head">グッズ一覧</div>
            <div id="goods_view_box_wrap">

    <c:choose>
    <c:when test="${goodss_count == 0}">
        <div class="in_main">
           <p class="event_view_title">グッズがありません</p>
        </div>

    </c:when>
    <c:otherwise>


                <c:forEach var="goods" items="${goodss}" varStatus="status">
                <div class="goods_view">
                <!--  <img src="<c:url value='/uploaded/'/>${goods.picture}" >-->
                    <img src="https://ikuthu.s3.ap-northeast-1.amazonaws.com/uploaded/${goods.picture}" >
                    <p class="goods_view_title">${goods.name}</p>
                    <div class="goods_view_mainbox">
                        <div class="goods_view_main">
                            <p class="content_title">販売価格</p>
                            <p class="content_con">${goods.sellingprice}</p>
                        </div>
                        <div class="goods_view_main">
                            <p class="content_title">在庫数</p>
                            <p class="content_con">${goods.stock}</p>
                        </div>
                    </div>
                    <p><a href="<c:url value='?action=${actGd}&command=${commShow}&gd_id=${goods.id}' />">詳細</a></p>
                </div>
                </c:forEach>

    </c:otherwise>
    </c:choose>

             </div>
        </div>

           <p><a href="<c:url value='?action=${actGd}&command=${commNew}' />">新規グッズ登録</a></p>

    </c:param>
</c:import>