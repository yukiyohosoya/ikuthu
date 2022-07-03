<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actLmev" value="${ForwardConst.ACT_LMEVEGOODS.getValue()}" />
<c:set var="commUp" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

 <%--    ${goods.goodsday} のgoodsは下で定義してるvar。 goodsdayはEventviewの変数名。間違えないように！--%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2 class="title2">イベントごとグッズ情報編集</h2>

         <form method="POST" action="<c:url value='?action=${actLmev}&command=${commUp}' />">
            <c:if test="${errors != null}">
	            <div class="in_main">
		            <div id="flush_error">
		                入力内容にエラーがあります。<br />
		            <c:forEach var="error" items="${errors}">
		                ・<c:out value="${error}" /><br />
		            </c:forEach>
		
		            </div>
	            </div>
            </c:if>

            <div class="divTable redTable">
                <div class="divTableHeading">
                        <div class="divTableRow">
                        <div class="divTableHead">編集</div>
                        <div class="divTableHead">グッズ</div>
                        <div class="divTableHead">販売価格</div>
                        <div class="divTableHead">販売数</div>
                    </div>
                    </div>
                    <div class="divTableBody">
                        <c:forEach var="lmevgoods" items="${lmevgoodss}" varStatus="status">
                                <fmt:parseDate value="" pattern="yyyy-MM-dd" var="eventDay" type="date" />
                                <div class="divTableRow">
                                <div class="divTableCell"><input type="checkbox" name="${AttributeConst.LMGS_ID.getValue()}" value="${lmevgoods.id}"  id="checkbox${lmevgoods.id}"/> </div>
                                <div class="divTableCell">
                                    <div class="divcon">${lmevgoods.goods.name}</div>
                                    <div class="divcon"><img class="mini" src="https://ikuthu.s3.ap-northeast-1.amazonaws.com/uploaded/${lmevgoods.goods.picture}" ></div>
                                </div>
                                <div class="divTableCell"><input class="number1" type="number" name="${AttributeConst.LMEVGS_SELLINGPRICE.getValue()}" value="${lmevgoods.lm_sellingprice}" id="price${lmevgoods.id}" /></div>
                                <div class="divTableCell"><input class="number1" type="number" name="${AttributeConst.LMEVGS_SOLDGOODS.getValue()}" value="${lmevgoods.soldgoods}" id="soldgoods${lmevgoods.id}" /></div>
                                </div>
                        </c:forEach>
                </div>
            </div>
            <div class="redTable outerTableFooter">
                <div class="tableFootStyle">
                    <div class="links">
                       （全 ${lmevgoodss_count} 件）　

                    </div>
                </div>
            </div>
        <input type="hidden" name="${AttributeConst.EV_ID.getValue()}" value="${ev_id}" />
        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        <button type="submit">修正</button>
        <input type="submit" formaction="<c:url value='?action=${actLmev}&command=${commDel}' />" value="削除" >
        </form>

            <script>
            $(function() {
                <c:forEach var="lmevgoods" items="${lmevgoodss}" varStatus="status">
                $('#price${lmevgoods.id}').attr('disabled', 'disabled');
                $('#checkbox${lmevgoods.id}').click(function() {
                     if ($(this).prop('checked') == false) {
                     $('#price${lmevgoods.id}').attr('disabled', 'disabled');
                     } else {
                     $('#price${lmevgoods.id}').removeAttr('disabled');
                     }
                     });
                $('#soldgoods${lmevgoods.id}').attr('disabled', 'disabled');
                $('#checkbox${lmevgoods.id}').click(function() {
                     if ($(this).prop('checked') == false) {
                     $('#soldgoods${lmevgoods.id}').attr('disabled', 'disabled');
                     } else {
                     $('#soldgoods${lmevgoods.id}').removeAttr('disabled');
                     }
                     });
                </c:forEach>
                });
             </script>

    </c:param>
</c:import>