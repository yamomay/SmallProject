<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>쿠폰 사용하기</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script type="text/javascript">
	function getDiscount(){
		
	}	
</script>
</head>

<body>
	<table width="99%" height="37" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td align="center"><font class="ct_name01" align="center">쿠폰사용하기</font>
			</td>
		</tr>
	</table>
	<table width="99%" border="0" cellspacing="0" cellpadding="0"
		style="margin-top: 10px;">
		<tr>
			<td class="ct_list_b">쿠폰선택</td>
			<td class="ct_line02"></td>
			<td class="ct_list_b">쿠폰명</td>
			<td class="ct_line02"></td>
			<td class="ct_list_b">쿠폰안내</td>
		</tr>
		<tr>
			<td colspan="5" bgcolor="808285" height="1"></td>
		</tr>
		<c:set var="i" value="0" />
		<c:forEach var="user" items="${coupon}">
			<c:set var="i" value="${ i+1 }" />
			<tr class="ct_list_pop">
				<td align="center"><input type="radio" value="${couponName}"
					name="coupon"></td>
				<td></td>
				<td align="center">${couponName}</td>
				<td></td>
				<td align="center">${couponDesc}</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="D6D7D6" height="1"></td>
			</tr>
		</c:forEach>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="10"
		style="margin-top: 60px;">
		<tr>
			<td align="center"><input type="button" value="할인적용"
				onclick="javascript:getDiscount();"></td>
		</tr>
	</table>
</body>
</html>