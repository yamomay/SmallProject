<%@ page contentType="text/html; charset=euc-kr" %>

<%@ page import="com.model2.mvc.service.domain.User" %>

<%
	User vo=(User)session.getAttribute("user");
	String role="";
	
	if(vo != null) {
		role=vo.getRole();
	}
%>

<html>
<head>
<title>Model2 MVC Shop</title>

<link href="/css/left.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function history(){
	popWin = window.open("/history.jsp","popWin","left=300, top=200, width=300, height=200, marginwidth=0, scrollbars=yes, scrolling=auto, marginheight=0, menubar=no, resizable=no");
}
</script>

</head>

<body background="/images/left/imgLeftBg.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

<table width="159" border="0" cellspacing="0" cellpadding="0">

<!--menu 01 line-->
<tr>
<td valign="middle"> 
	<table  border="0" cellspacing="0" cellpadding="0" width="159" >	
		<% 	if(vo != null){ %>
		<tr>
			<td class="Depth03" align="center">
				<a href="/getUser.do?userId=<%=vo.getUserId() %>" target="rightFrame">����������ȸ</a>
			</td>
		</tr>
		<%	}  %>
		<% if(role.equals("admin")){%>
		<tr>
			<td class="Depth03" align="center">
				<a href="/listUser.do" target="rightFrame">ȸ��������ȸ</a>
			</td>
		</tr>
		<% } %>
<%	if(role.equals("admin")){ %>
<!--menu 02 line-->
			<tr>
				<td class="Depth03" align="center">
					<a href="../product/addProductView.jsp;" target="rightFrame">�ǸŻ�ǰ���</a>
				</td>
			</tr>
			<tr>
				<td class="Depth03" align="center">
					<a href="/listProduct.do?menu=manage"  target="rightFrame">�ǸŻ�ǰ����</a>
				</td>
			</tr>
<% } %>
<!--menu 03 line-->
			<tr>
				<td class="Depth03" align="center">
					<a href="/listProduct.do?menu=search" target="rightFrame">�� ǰ �� ��</a>
				</td>
			</tr>
				<td class="Depth03" align="center">
					<a href="/listProduct.do?menu=search" target="rightFrame">�� �� �� ��</a>
				</td>
			<%	if(vo != null && role.equals("user")){%>
			<tr>
				<td class="Depth03" align="center">
					<a href="/listPurchase.do"  target="rightFrame">�����̷���ȸ</a>
				</td>
			</tr>
			<%  }%>
			<tr>
				<td class="Depth03" align="center">
					�ֱ� �� ��ǰ
				</td>
			</tr>
		</table>
	</td>
</tr>

</table>

</body>
</html>
