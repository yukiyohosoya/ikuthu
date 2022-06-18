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

        <table id="lmevgoods_list">
            <tbody>
                <tr>
                    <th class="goods_name">追加</th>
                    <th class="goods_name">グッズ名</th>
                    <th class="goods_price">販売価格</th>
                </tr>
                 <%--    ${goods.goodsday} のgoodsは下で定義してるvar。 goodsdayはEventviewの変数名。間違えないように！--%>
                <c:forEach var="goods" items="${goodss}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="goods_name"><input type="checkbox" name="${AttributeConst.LMEVGS_CHECK.getValue()}" value="${goods.id}"/></td>
                        <td class="goods_name">${goods.name}</td>
                        <td class="goods_price"><input type="number" name="${AttributeConst.LMEVGS_SELLINGPRICE.getValue()}" value="${goods.sellingprice}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:out value="${_token}" />

<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>