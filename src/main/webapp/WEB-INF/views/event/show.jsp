<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actLeg" value="${ForwardConst.ACT_LMEVEGOODS.getValue()}" />
<c:set var="actEv" value="${ForwardConst.ACT_EVENT.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commSel" value="${ForwardConst.CMD_SELECT.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DELETE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <fmt:parseDate value="${event.eventday}" pattern="yyyy-MM-dd" var="eventDay" type="date" />
        <h2 class="title2"><c:out value="${event.name}"/>　詳細</h2>

                <div class="event_view">
                    <p class="event_text">イベント日付</p>
                    <p class="event_view_title"><fmt:formatDate value="${eventDay}" pattern='yyyy/MM/dd' /></p>
                    <div class="event_view_mainbox">
                        <div class="event_view_main">
                            <p class="event_view_content_title">販売商品数</p>
                            <p class="event_view_content_con">${lmevgoodss_count}</p>
                        </div>
                        <div class="event_view_main">
                            <p class="event_view_content_title"> 総売り上げ額</p>
                            <p class="event_view_content_con">￥ ${lmevgoods_sellingprice}</p>
                        </div>
                    </div>
                </div>



        <p><a href="<c:url value='?action=${actEv}&command=${commEdt}&ev_id=${event.id}' />">イベント情報変更</a></p>
        <p><a href="<c:url value='?action=${actLeg}&command=${commSel}&ev_id=${event.id}' />">販売商品登録</a></p>

        <p><a href="#" onclick="confirmDestroy();"><c:out value="${event.name}"/>を削除する</a></p>
         <form method="POST" action="<c:url value='?action=${actEv}&command=${commDel}' />">
                <input type="hidden" name="${AttributeConst.EV_ID.getValue()}" value="${event.id}" />
                <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            </form>
        <script>
            function confirmDestroy() {
                if (confirm("本当に削除してよろしいですか？")) {
                    document.forms[0].submit();
                }
            }
        </script>

        <div id="goods_view_box">
            <div id="goods_view-head"><c:out value="${event.name}"/>　登録グッズ一覧</div>
            <div id="goods_view_box_wrap">

    <c:choose>
    <c:when test="${lmevgoodss_count == 0}">
        <div class="in_main">
           <p class="event_view_title">グッズがありません</p>
        </div>

    </c:when>
    <c:otherwise>

                <c:forEach var="lmevgoods" items="${lmevgoodss}" varStatus="status">
                <div class="goods_view">
                <!--  <img src="<c:url value='/uploaded/'/>${goods.picture}" >-->
                    <img src="https://ikuthu.s3.ap-northeast-1.amazonaws.com/uploaded/${lmevgoods.goods.picture}" >
                    <p class="goods_view_title">${lmevgoods.goods.name}</p>
                    <div class="goods_view_mainbox">
                        <div class="goods_view_main">
                            <p class="content_title">${event.name}<br>販売価格</p>
                            <p class="content_con">${lmevgoods.lm_sellingprice}</p>
                        </div>
                        <div class="goods_view_main">
                            <p class="content_title"><br>販売数</p>
                            <p class="content_con">${lmevgoods.soldgoods}</p>
                        </div>
                    </div>
                </div>
                </c:forEach>

    </c:otherwise>
    </c:choose>
             </div>
        </div>
        <c:if test="${lmevgoodss_count != 0}">
            <p><a href="<c:url value='?action=${actLeg}&command=${commEdt}&ev_id=${event.id}' />">販売商品編集</a></p>
        </c:if>

    </c:param>
</c:import>