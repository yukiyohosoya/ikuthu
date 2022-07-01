<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actLmev" value="${ForwardConst.ACT_LMEVEGOODS.getValue()}" />
<c:set var="actGds" value="${ForwardConst.ACT_GOODS.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

 <%--    ${goods.goodsday} のgoodsは下で定義してるvar。 goodsdayはEventviewの変数名。間違えないように！--%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

    <h2 class="title2">グッズ追加</h2>
<c:choose>
    <c:when test="${goodss_count == 0}">
        <div class="in_main">
            <h3>登録できるグッズがありません</h3>
            <div class="exp">
                <p>グッズを登録してみてください</p>
                <div class="button002">
                    <a class="marginauto" href="<c:url value='?action=${actGds}&command=${commNew}' />">グッズ登録画面</a>
                </div>
            </div>
        </div>

    </c:when>
    <c:otherwise>

         <form method="POST" action="<c:url value='?action=${actLmev}&command=${commCrt}' />">
            <c:if test="${errors != null}">
            <div id="flush_error">
                入力内容にエラーがあります。<br />
            <c:forEach var="error" items="${errors}">
                ・<c:out value="${error}" /><br />
            </c:forEach>

            </div>
            </c:if>

            <div class="divTable redTable">
                <div class="divTableHeading">
                        <div class="divTableRow">
                        <div class="divTableHead">追加</div>
                        <div class="divTableHead">グッズ</div>
                        <div class="divTableHead">販売価格</div>
                        <div class="divTableHead">販売数</div>
                    </div>
                    </div>
                    <div class="divTableBody">
                        <c:forEach var="goods" items="${goodss}" varStatus="status">
                        <fmt:parseDate value="" pattern="yyyy-MM-dd" var="eventDay" type="date" />
                            <div class="divTableRow">
                                <div class="divTableCell"><input type="checkbox" name="${AttributeConst.GS_ID.getValue()}" value="${goods.id}"  id="checkbox${goods.id}"/> </div>
                                <div class="divTableCell">
                                    <div class="divcon">${goods.name}</div>
                                    <div class="divcon"><img class="mini" src="https://ikuthu.s3.ap-northeast-1.amazonaws.com/uploaded/${goods.picture}" ></div>
                                </div>
                                <div class="divTableCell"><input class="number1" type="number" name="${AttributeConst.LMEVGS_SELLINGPRICE.getValue()}" value="${goods.sellingprice}" id="price${goods.id}" /></div>
                                <div class="divTableCell"><input class="number1" type="number" name="${AttributeConst.LMEVGS_SOLDGOODS.getValue()}" value="0" id="soldgoods${goods.id}" /></div>
                            </div>
                        </c:forEach>
                </div>
            </div>
            <div class="redTable outerTableFooter">
                <div class="tableFootStyle">
                    <div class="links">
                       （全 ${goodss_count} 件）　

                    </div>
                </div>
            </div>
            <input type="hidden" name="${AttributeConst.EV_ID.getValue()}" value="${ev_id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        <button type="submit">投稿</button>
        </form>

            <script>
            $(function() {
                <c:forEach var="goods" items="${goodss}" varStatus="status">
                $('#price${goods.id}').attr('disabled', 'disabled');
                $('#checkbox${goods.id}').click(function() {
                     if ($(this).prop('checked') == false) {
                     $('#price${goods.id}').attr('disabled', 'disabled');
                     } else {
                     $('#price${goods.id}').removeAttr('disabled');
                     }
                     });
                $('#soldgoods${goods.id}').attr('disabled', 'disabled');
                $('#checkbox${goods.id}').click(function() {
                     if ($(this).prop('checked') == false) {
                     $('#soldgoods${goods.id}').attr('disabled', 'disabled');
                     } else {
                     $('#soldgoods${goods.id}').removeAttr('disabled');
                     }
                     });
                </c:forEach>
                });
             </script>



    </c:otherwise>
</c:choose>


    </c:param>
</c:import>