<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전체 보기</h1>
	<table border="2">
		<tr><th>이름</th><th>전화번호</th></tr>
		<c:forEach var="imsi" items="${getallinfo}">
			<tr>
				<th><a href="getinfo?idhi=${imsi.idhi}">${imsi.idhi}</a></th>
				<th>${imsi.hihi}</th>
			</tr>
		</c:forEach>
	</table> <br />
	
	<a href="insertinfo">[추가]</a>
	<a href="insertForm">[삭제]</a>
	<a href="insertForm">[수정]</a>
</body>
</html>