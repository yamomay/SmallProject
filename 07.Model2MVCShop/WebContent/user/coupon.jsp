<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>���� �߱��ϱ�</title>
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
			<td align="center"><font class="ct_name01" align="center" disabled="${param.couponNo1==10? false:true}">'${param.userName}'ȸ������
					�����߱��ϱ�</font></td>
		</tr>
	</table>
	<form name="coupon" action="/givecoupon.do" method="post">
		<table width="99%" border="0" cellspacing="0" cellpadding="0"
			style="margin-top: 10px;">
			<tr>
				<td class="ct_list_b">�߱޼���</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">������</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">�����ȳ�</td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="808285" height="1"></td>
			</tr>
			<tr class="ct_list_pop">
				<td align="center">
					<input type="checkbox" value="10" name="coupon" ${param.couponNo1==10 ? "disabled" : ""}>
				</td>
				<td></td>
				<td align="center">10%��������</td>
				<td></td>
				<td align="center">��ü ������ 10%�� ������ �ݴϴ�</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="D6D7D6" height="1"></td>
			</tr>
			<tr class="ct_list_pop">
				<td align="center"><input type="checkbox" value="15"
					name="coupon" ${param.couponNo2==15 ? "disabled" : ""}></td>
				<td></td>
				<td align="center">15%��������</td>
				<td></td>
				<td align="center">��ü ������ 15%�� ������ �ݴϴ�</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" bgcolor="D6D7D6" height="1"></td>
			</tr>
			<tr class="ct_list_pop">
				<td align="center"><input type="checkbox" value="99"
					name="coupon" ${param.couponNo3==99 ? "disabled" : ""}></td>
				<td></td>
				<td align="center">��ۺ� ��������</td>
				<td></td>
				<td align="center">���Ű��ݿ� ������� ��ۺ� ����!</td>
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
				<input type="button" value="�߱��ϱ�" onclick="javascript:givecoupon();"> 
				<input type="hidden" name="userId" value="${param.userId}" />
				<input type="hidden" name="userName" value="${param.userName}" />
				<input type="hidden" name="currentPage" value="${param.currentPage}" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>