<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>쿠폰 발급하기</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script type="text/javascript">
<!--
	function givecoupon() {
		document.coupon.target="rightFrame";
		document.coupon.submit();
		self.close();
	}
	-->
</script>
</head>
<%
	System.out.println("userId = " + request.getParameter("userId"));
	System.out.println("Name = " + request.getParameter("userName"));
	System.out.println("CurrentPage = " + request.getParameter("currentPage"));
	System.out.println("coupon1 = " + request.getParameter("couponNo1"));
	System.out.println("coupon2 = " + request.getParameter("couponNo2"));
	System.out.println("coupon3 = " + request.getParameter("couponNo3"));
%>
<body>
	<table width="99%" height="37" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td align="center"><font class="ct_name01" align="center" disabled="${param.couponNo1==10? false:true}">'${param.userName}'회원님의
					쿠폰발급하기</font></td>
		</tr>
	</table>
	<form name="coupon" action="/givecoupon.do" method="post">
		<table width="99%" border="0" cellspacing="0" cellpadding="0"
			style="margin-top: 10px;">
			<tr>
				<td class="ct_list_b">발급선택</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">쿠폰명</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">쿠폰안내</td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="808285" height="1"></td>
			</tr>
			<tr class="ct_list_pop">
				<td align="center">
					<input type="checkbox" value="10" name="coupon" ${param.couponNo1==10 ? "disabled" : ""}>
				</td>
				<td></td>
				<td align="center">10%할인쿠폰</td>
				<td></td>
				<td align="center">전체 가격의 10%를 할인해 줍니다</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="D6D7D6" height="1"></td>
			</tr>
			<tr class="ct_list_pop">
				<td align="center"><input type="checkbox" value="15"
					name="coupon" ${param.couponNo2==15 ? "disabled" : ""}></td>
				<td></td>
				<td align="center">15%할인쿠폰</td>
				<td></td>
				<td align="center">전체 가격의 15%를 할인해 줍니다</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="D6D7D6" height="1"></td>
			</tr>
			<tr class="ct_list_pop">
				<td align="center"><input type="checkbox" value="99"
					name="coupon" ${param.couponNo3==99 ? "disabled" : ""}></td>
				<td></td>
				<td align="center">배송비 무료쿠폰</td>
				<td></td>
				<td align="center">구매가격에 상관없이 배송비가 무료!</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="D6D7D6" height="1"></td>
			</tr>
		</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="10"
		style="margin-top: 20px;">
		<tr>
			<td align="center">
				<input type="button" value="발급하기" onclick="javascript:givecoupon();"> 
				<input type="hidden" name="userId" value="${param.userId}" />
				<input type="hidden" name="userName" value="${param.userName}" />
				<input type="hidden" name="currentPage" value="${param.currentPage}" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>