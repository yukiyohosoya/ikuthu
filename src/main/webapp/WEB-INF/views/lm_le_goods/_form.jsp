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

<label for="${AttributeConst.GS_NAME.getValue()}" >商品名</label><br />
<input type="text" name="${AttributeConst.GS_NAME.getValue()}" value="${goods.name}" />
<br /><br />


<label for="${AttributeConst.GS_CREATEDAY.getValue()}" >作成日</label><br />
<input type="date" name="${AttributeConst.GS_CREATEDAY.getValue()}" value= "${goods.create_day}" />
<br /><br />
<br /><br />

<label for="${AttributeConst.GS_SELLINGPRICE.getValue()}" >デフォルト販売価格</label><br />
<input type="number" name="${AttributeConst.GS_SELLINGPRICE.getValue()}" value="${goods.sellingprice}" />
<br /><br />

<label for="${AttributeConst.GS_STOCK.getValue()}" >在庫数（初期仕入れ数）</label><br />
<input type="number" name="${AttributeConst.GS_STOCK.getValue()}" value="${goods.stock}" />
<br /><br />

<label for="${AttributeConst.GS_PURCHASEPRICE.getValue()}" >仕入れ価格</label><br />
<input type="number" name="${AttributeConst.GS_PURCHASEPRICE.getValue()}" value="${goods.purchaseprice}" />
<br /><br />

<label for="${AttributeConst.GS_PICTURE.getValue()}" >商品画像ファイル</label><br />
<input type="text" name="${AttributeConst.GS_PICTURE.getValue()}" value="${goods.picture}" />
<br /><br />

<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>