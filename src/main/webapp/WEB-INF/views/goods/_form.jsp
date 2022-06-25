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

      <div class="goodinput_box">
        <div class="goodinput_box_Left">
          <div class="goodinput_box_light_content">
            <label class="input_title" for="${AttributeConst.GS_NAME.getValue()}" >商品名</label><br />
            <input type="text" class="text1" name="${AttributeConst.GS_NAME.getValue()}" value="${goods.name}" />
          </div>
          <div class="goodinput_box_light_content">
                  <c:if test="${goods.picture != null}">
                    <img src="<c:url value='/uploaded/'/>${goods.picture}" >
                  </c:if>
            <label class="input_title" for="${AttributeConst.GS_PICTURE.getValue()}" >商品画像ファイル</label><br />
            <input type="file" accept=".jpg,.jpeg,.png" name="${AttributeConst.GS_PICTURE.getValue()}" value="${goods.picture}" />

          </div>
        </div>
        <div class="goodinput_box_light">
          <div class="goodinput_box_light_content">
            <label for="${AttributeConst.GS_CREATEDAY.getValue()}" >グッズ作成日</label><br />
            <input type="date" class="number1" name="${AttributeConst.GS_CREATEDAY.getValue()}" value= "${goods.create_day}" />
          </div>
          <div class="goodinput_box_light_content">
            <label class="input_title" for="${AttributeConst.GS_SELLINGPRICE.getValue()}" >デフォルト販売価格</label><br />
            <input type="number" min="0" class="number1" name="${AttributeConst.GS_SELLINGPRICE.getValue()}" value="${goods.sellingprice}" />
          </div>
          <div class="goodinput_box_light_content">
            <label class="input_title" for="${AttributeConst.GS_STOCK.getValue()}" >在庫数（初期仕入れ数）</label><br />
            <input type="number" min="0" class="number1" name="${AttributeConst.GS_STOCK.getValue()}" value="${goods.stock}" />
          </div>
          <div class="goodinput_box_light_content">
            <label class="input_title" for="${AttributeConst.GS_PURCHASEPRICE.getValue()}" >仕入れ価格</label><br />
            <input type="number" min="0" class="number1" name="${AttributeConst.GS_PURCHASEPRICE.getValue()}" value="${goods.purchaseprice}" />
          </div>
        </div>
      </div>

      <input type="hidden" name="${AttributeConst.GS_ID.getValue()}" value="${goods.id}" />
      <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
      <button type="submit">投稿</button>