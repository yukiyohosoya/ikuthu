<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actGd" value="${ForwardConst.ACT_GOODS.getValue()}" />
<c:set var="actSh" value="${ForwardConst.ACT_SHOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2><c:out value="${goods.name}"/>　詳細ページ</h2>

            <div class="goodinput_box">
                <div class="goodinput_box_Left">
                    <div class="goodinput_box_light_content">
                        <p>商品名</p>
                        <c:out value="${goods.name}"/>
                    </div>
                    <div class="goodinput_box_light_content">
                    <img src="https://ikuthu.s3.ap-northeast-1.amazonaws.com/uploaded/${goods.picture}" >
                    </div>
                </div>
                <div class="goodinput_box_light">
                    <div class="goodinput_box_light_content">
                        <fmt:parseDate value="${goods.create_day}" pattern="yyyy-MM-dd" var="goodscreateDay" type="date" />
                        <p>グッズ作成日</p>
                        <fmt:formatDate value="${goodscreateDay}" pattern='yyyy-MM-dd' />
                    </div>
                    <div class="goodinput_box_light_content">
                        <p>デフォルト販売価格</p>
                        <c:out value="${goods.sellingprice}"/>
                    </div>
                    <div class="goodinput_box_light_content">
                        <p>在庫数（初期仕入れ数）</p>
                        <c:out value="${goods.stock}"/>
                    </div>
                    <div class="goodinput_box_light_content">
                        <p>仕入れ価格</p>
                        <c:out value="${goods.purchaseprice}"/>
                    </div>
                </div>
            </div>
        <p><a href="<c:url value='?action=${actGd}&command=${commEdt}&gd_id=${goods.id}' />">商品の編集</a></p>

        <p><a href="#" onclick="confirmDestroy();"><c:out value="${goods.name}"/>を削除する</a></p>
         <form method="POST" action="<c:url value='?action=${actGd}&command=${commDel}' />">
                <input type="hidden" name="${AttributeConst.GS_ID.getValue()}" value="${goods.id}" />
                <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            </form>
        <script>
            function confirmDestroy() {
                if (confirm("本当に削除してよろしいですか？")) {
                    document.forms[0].submit();
                }
            }
        </script>

        <p><a href="<c:url value='?action=${actSh}&command=${commIdx}' />">トップに戻る</a></p>
    </c:param>
</c:import>