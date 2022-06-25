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
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />



<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2 class="title2"><c:out value="${event.name}"/>　詳細</h2>

        <fmt:parseDate value="${event.eventday}" pattern="yyyy-MM-dd" var="eventDay" type="date" />
        <table>
            <tbody>
                <tr>
                    <th>イベント日付</th>
                    <td><fmt:formatDate value="${eventDay}" pattern='yyyy/MM/dd' /></td>
                </tr>
                <tr>
                    <th>販売商品数</th>
                    <td>00</td>
                </tr>
                <tr>
                    <th>総売り上げ額</th>
                    <td>00</td>
                </tr>
            </tbody>
        </table>
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
            <div id="goods_view-head">イベント</div>
            <div id="goods_view_box_wrap">
                <c:forEach var="lmevgoods" items="${lmevgoodss}" varStatus="status">
                <div class="goods_view">
                <!--  <img src="<c:url value='/uploaded/'/>${goods.picture}" >-->
                    <img src="https://ikuthu.s3.ap-northeast-1.amazonaws.com/uploaded/${lmevgoods.goods.picture}" >
                    <p class="goods_view_title">${lmevgoods.goods.name}</p>
                    <div class="goods_view_mainbox">
                        <div class="goods_view_main">
                            <p class="content_title">${event.name}販売価格</p>
                            <p class="content_con">${lmevgoods.lm_sellingprice}</p>
                        </div>
                        <div class="goods_view_main">
                            <p class="content_title">販売数</p>
                            <p class="content_con">${lmevgoods.soldgoods}</p>
                        </div>
                    </div>
                </div>
                </c:forEach>
             </div>
        </div>

        <div class="goods_shop_index">
            <c:forEach var="lmevgoods" items="${lmevgoodss}" varStatus="status">
            <div class="goods_index_view">
                <p>画像</p>
                <p>${lmevgoods.goods.name}</p>
                <div class="goods_main_view">
                    <div class="goods_main">
                        <p></p>
                        <p></p>
                    </div>
                    <div class="goods_main">
                        <p></p>
                        <p></p>
                    </div>
                </div>
                <p>詳細</p>
            </div>
            </c:forEach>
        </div>

    </c:param>
</c:import>