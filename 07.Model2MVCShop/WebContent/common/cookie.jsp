<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	System.out.println("cookie get in or what?");

    String history=null;
	
	Integer no = new Integer(request.getParameter("prodNo"));
	
	Cookie[] cookies = request.getCookies();
	
	if (cookies != null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
			
			}
		}
	}
	
	history+=","+no.toString();
	Cookie cookie = new Cookie("history",history);
	
	response.addCookie(cookie);
	System.out.println(cookies[0].getValue()+" : "+cookies[0].getName());
%>