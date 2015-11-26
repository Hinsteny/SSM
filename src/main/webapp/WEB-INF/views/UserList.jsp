<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="header">
	<h1>浅雪夕阳, Hinsteny!</h1>
</div>
<div class="regist-container">
	<h3 style="text-algin: left;">所有用户列表</h3>
	<ul>
		<c:forEach var="user" items="${users}" varStatus="flag">
			<li>${user.username}</li>
		</c:forEach>
	</ul>
</div>
</body>
</html>