<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>
	<script src="../script/jquery-3.4.1.min.js"></script>
	<link href="../css/style.css" rel="stylesheet" >
	<script>
		$(function(){
			console.log('Jquery 확인');
			
			$('#btnWrite').click(function(){
				console.log('btnWrite 확인');
				location.href="${path}/board_servlet/write.do";
			});
		});	
	</script>
</head>
<body>
<h2>게시물 목록</h2> <hr />
<form action="${path}/board_servlet/search.do" name="frm" id="frm" method="post">
	<select name="option" id="option">
		<option value="writer">이름</option>
		<option value="subject">제목</option>
		<option value="content">본문</option>
		<option value="all">이름+제목+본문</option>
	</select>
	<input type="text" name="keyword" />
	<input type="submit" value="검색" >
</form>
<hr />
<input type="button" id="btnWrite" value="글쓰기" />
	<table>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>이름</td>
			<td>날짜</td>
			<td>조회수</td>
		</tr>
		<c:forEach var="dto" items="${list}">
		<tr>
			<td>${dto.num}</td>
			<td>
				<c:forEach var="i" begin="1" end="${dto.re_level}">&nbsp;&nbsp;</c:forEach>
				<a href="${path}/board_servlet/view.do?num=${dto.num}">${dto.subject} [${dto.comment_count}]</a>
			</td>
			<td>${dto.writer}</td>
			<td>${dto.reg_date}</td>
			<td>${dto.readcount}</td>	
		</tr>
		</c:forEach>
		<tr>
			<td colspan="7">
				<c:if test="${curBlock>1}">
					<a href="${path}/board_servlet/list.do?curPage=${prev}">[이전]</a>
				</c:if>
				<c:forEach var="i" begin="${startBlock}" end="${endBlock}">
					&nbsp;<a href="${path}/board_servlet/list.do?curPage=${i}">${i}</a>&nbsp;
				</c:forEach>
				<c:if test="${curBlock<totBlock}">
					<a href="${path}/board_servlet/list.do?curPage=${next}">[다음]</a>
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>