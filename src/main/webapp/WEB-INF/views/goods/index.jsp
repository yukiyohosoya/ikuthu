<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actGs" value="${ForwardConst.ACT_GOODS.getValue()}" />
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
        <h2>グッズ 一覧</h2>
        <table id="goods_list">
            <tbody>
                <tr>
                    <th class="goods_name">グッズ名</th>
                    <th class="goods_price">デフォルト価格</th>
                </tr>
                 <%--    ${goods.goodsday} のgoodsは下で定義してるvar。 goodsdayはEventviewの変数名。間違えないように！--%>
                <c:forEach var="goods" items="${goodss}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="goods_name">${goods.name}</td>
                        <td class="goods_price">${goods.sellingprice}<img src="<c:url value='/uploaded/'/>${goods.picture}" ></td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
                <c:out value="${goodss_count }"></c:out>
                <div id="pagination">
                    （全 ${goodss_count} 件）<br />
                    <c:forEach var="i" begin="1" end="${((goodss_count - 1) / maxRow) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='?action=${actGs}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
        <p><a href="<c:url value='?action=${actGs}&command=${commNew}' />">新規グッズ登録</a></p>

    </c:param>
</c:import>