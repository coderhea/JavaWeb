<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		Cookie[] cookies = request.getCookies();
		System.out.println("cookies " + cookies);
		
		if(cookies !=null){ //jsp tag if cookies exist 아래 ㄹorm 없이 loginOk바
			for(Cookie c: cookies){
				if(c.getName().equals("memberId")){
					response.sendRedirect("loginOk.jsp");
				}
			}
		}
	%>
	<form action="loginCon" method="post">
		ID : <input type="text" name="mID"><br>
		PW : <input type="password" name="mPW"><br>
		<input type = "submit" value="login">
	</form>
</body>
</html>
