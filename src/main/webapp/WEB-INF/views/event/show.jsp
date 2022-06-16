<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actLeg" value="${ForwardConst.ACT_LMEVEGOODS.getValue()}" />
<c:set var="actEv" value="${ForwardConst.ACT_EVENT.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commSel" value="${ForwardConst.CMD_SELECT.getValue()}" />

<
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2><c:out value="${event.name}"/>　詳細</h2>
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



        <p><a href="<c:url value='?action=${actLeg}&command=${commSel}&ev_id=${event.id}' />">販売商品登録</a></p>

    </c:param>
</c:import>