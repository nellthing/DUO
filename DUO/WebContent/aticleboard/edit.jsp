<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정&삭제</title>
	<script src="../script/jquery-3.4.1.min.js"></script>
	<link href="../css/style.css" rel="stylesheet" />
	<script>
		$(function(){
			console.log('Jquery 완료');
			
			$('#btnUpdate').click(function(){
				console.log('수정버튼 완료');
				if(confirm("수정하시겠습니까?")){
					document.frm.action="${path}/board_servlet/update.do";
					document.frm.submit();					
				}
			});
			$('#btnDelete').click(function(){
				console.log('삭제버튼 완료');
				
				if(confirm("삭제하시겠습니까?")){
					document.frm.action="${path}/board_servlet/delete.do";
					document.frm.submit();
				}
			});
			$('#btnList').click(function(){
				console.log('취소버튼 완료');
				
				document.frm.action="${path}/board_servlet/list.do";
				document.frm.submit();
			});
			
		});
	</script>
	
</head>
<body>
<h2>게시물 수정&삭제</h2> <hr />
<form action="" name="frm" method="post">
	<table>
		<tr>
			<td>이름</td>
			<td><input type="text" name="writer" id="writer" value="${dto.writer}" /></td>
		</tr>
		<tr>
			<td>제목</td>
			<td><input type="text" name="subject" id="subject" value="${dto.subject}" /></td>
		</tr>
		<tr>
			<td>본문</td>
			<td><textarea name="content" id="content" cols="70" rows="10">${dto.content}</textarea></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>
				<input type="password" name="passwd" id="passwd" />
				<c:if test="${param.pass_error == 'y'}">
					<span style="color:red;">비밀번호가 일치하지 않습니다.</style>		
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" id="btnUpdate" value="수정" />
				<input type="button" id="btnDelete" value="삭제" />
				<input type="button" id="btnList" value="취소" />
				<input type="hidden" name="num" value="${dto.num}" />				
			</td>
		</tr>
	</table>
</form>
</body>
</html>