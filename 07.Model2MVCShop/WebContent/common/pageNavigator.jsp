<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

	<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
	</c:if>
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetList('${ resultPage.currentPage-1}')" class="ct_paging">¢¸ </a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<c:choose>
		<c:when test="${resultPage.currentPage == i}">
			<b>${i}</b>
		</c:when>
		<c:when test="${resultPage.currentPage != i}">
			<a href="javascript:fncGetList('${i}');">${i}</a>
		</c:when>
		</c:choose>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
	</c:if>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetList('${resultPage.endUnitPage+1}')" class="ct_paging"> ¢º</a>
	</c:if>