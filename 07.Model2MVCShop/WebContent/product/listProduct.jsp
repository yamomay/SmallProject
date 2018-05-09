<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<title>상품 목록 조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<c:if test="${param.likefail==1}">
<script>
alert("이미 좋아요를 누르신 상품입니다!");
</script>
</c:if>
<script type="text/javascript">
	// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
	   	document.detailForm.submit();	
	}
	
	function likeProduct() {
		alert("비회원은 좋아요가 불가능합니다!\n로그인해주세요");
		document.location.href="user/loginView.jsp"

	}
	
	
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" method="post" action="/listProduct.do">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					<c:if test="${ menu == 'manage' }">
					    판매상품관리
					  <input type="hidden" name ="menu" value="manage"/>
					</c:if>
					<c:if test="${ menu == 'search' }">
					    상품검색
					  <input type="hidden" name ="menu" value="search"/>
					</c:if>
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${ search.searchCondition == "0" ? "selected" : "" }>상품번호</option>
				<option value="1" ${ search.searchCondition == "1" ? "selected" : "" }>상품명</option>
				<option value="2" ${ search.searchCondition == "2" ? "selected" : "" }>상품가격</option>
			</select>
			<input type="text" name="searchKeyword" value="${search.searchKeyword}"  class="ct_input_g" 
							style="width:200px; height:20px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="5">
			전체  ${ resultPage.totalCount } 건수,	현재 ${ resultPage.currentPage } 페이지 
		</td>
		<td colspan="6" class="ct_list_b01">
			<c:if test="${search.order!='reg_date'}">
				<a href="/listProduct.do?order=reg_date&menu=${menu}">신상품순</a>
			</c:if>
			<c:if test="${search.order=='reg_date'}">
				<input type="hidden" name="order" value="reg_date"/>
				<a href="/listProduct.do?menu=${menu}"><b>신상품순</b></a>
			</c:if>
			
			 / 
			 
			<c:if test="${search.order!='price'}">
				<a href="/listProduct.do?order=price&menu=${menu}">가격순</a>
			</c:if>
			<c:if test="${search.order=='price'}">
				<input type="hidden" name="order" value="price"/>
				<a href="/listProduct.do?menu=${menu}"><b>가격순</b></a>
			</c:if>
			
			 / 
			 
			<c:if test="${search.order!='prod_name'}">
				<a href="/listProduct.do?order=prod_name&menu=${menu}">상품명순</a>
			</c:if>
			<c:if test="${search.order=='prod_name'}">
				<input type="hidden" name="order" value="prod_name"/>
				<a href="/listProduct.do?menu=${menu}"><b>상품명순</b></a>
			</c:if>
			
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">상품번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>		
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">LIKES</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:set var="i" value="0"/>
	<c:forEach var="product" items="${list}">
	<tr class="ct_list_pop">
		<td align="center">
		${product.prodNo }
		</td>
		<td></td>
		<td align="left">
			<c:if test="${ menu == 'manage' && empty product.proTranCode.trim()}">
				<a href="/getProduct.do?prodNo=${product.prodNo}&menu=manage">${product.prodName}</a>
			</c:if>
			<c:if test="${ menu == 'search' || product.proTranCode.trim() > 0}">
				<a href="/getProduct.do?prodNo=${product.prodNo}&menu=search">${product.prodName}</a>
			</c:if>
		</td>
		<td></td>
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.regDate}</td>
		<td></td>
		<td align="left">
			<c:if test="${ product.proTranCode.trim() == 0 || empty product.proTranCode.trim()}">
				판매중
				</td>
			</c:if>
			<c:if test="${ product.proTranCode.trim() > 0 }">
				<c:choose>
					<c:when test="${product.proTranCode.trim() == 3 && user.role == 'admin'}">
						배송완료
						</td>				
					</c:when>	
					<c:when test="${ product.proTranCode.trim() == 1 && user.role == 'admin'}">
						구매완료 <a href="/updateTranCodeByProd.do?currentPage=${resultPage.currentPage}&tranCode=2&prodNo=${product.prodNo}">배송하기</a>
						</td>
					</c:when>
					<c:when test="${ product.proTranCode.trim() == 2 && user.role == 'admin'}">
						배송중
						</td>
					</c:when>
					<c:otherwise>
						판매완료
						</td>
					</c:otherwise>
				</c:choose>
			</c:if>
		<td></td>
		<td align="center">
		<c:if test="${empty user.userId}">
			<input type="button" style="height:15pt; text-align:center;" onclick="javascript:likeProduct();" value="♥" class="ct_list_like"/>
		</c:if>
		<c:if test="${!empty user.userId}">
			<input type="button" style="height:15pt; text-align:center;" onclick="location.href='/likeProduct.do?prod_no=${product.prodNo}&menu=${menu}&currentPage=${resultPage.currentPage}'" value="♥" class="ct_list_like"/>
		</c:if>
		<input type="hidden" name="user_id" value="${user.userId}"/>
		${product.likes}
		</td>
		<td></td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>

<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center" class="ct_paging">
		    <input type="hidden" id="currentPage" name="currentPage" value="">
			<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!-- PageNavigation End... -->
</form>
</div>
</body>
</html>