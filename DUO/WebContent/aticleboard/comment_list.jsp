<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 목록</title>
</head>
<body>
	<table>
		<c:forEach var="row" items="${list}">
		<tr>
			<td>${row.writer} (<fmt:formatDate value="${row.reg_date}" pattern="yyyy-MM-dd hh:mm:ss"/>) <br />
			${row.content}
			</td>
		</tr>	
		</c:forEach>
	</table>
</body>
</html>