<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="../script/jquery-3.4.1.min.js"></script>
	<link href="../css/style.css" rel="stylesheet" />
	<script>
		$(function(){
			console.log('Jquery 정상작동');
			
			$('#btnSave').click(function(){
				console.log('확인버튼 성공');
				
				var writer = $('#writer').val();
				var subject = $('#subject').val();
				var content = $('#content').val();
				var passwd = $('#passwd').val();
				
				if(writer==""){
					alert("이름을 입력하세요.");
					$('#writer').focus();
					return;
				}
				if(subject==""){
					alert("제목을 입력하세요.");
					$('#subject').focus();
					return;
				}
				if(content==""){
					alert("내용을 입력하세요.");
					$('#content').focus();
					return;
				}
				if(passwd==""){
					alert("비밀번호를 입력하세요.");
					$('#passwd').focus();
					return;
				}
				document.frm.action="${path}/board_servlet/insertReply.do";
				document.frm.submit();
			});
			$('#btnList').click(function(){
				console.log('취소버튼 성공');
				
				location.href="${path}/board_servlet/list.do";
			});
			
		});
	</script>
	
</head>
<body>
<h2>답글 쓰기</h2> <hr />
<form action="" name="frm" method="post">
	<table>
		<tr>
			<td>이름</td>
			<td><input type="text" name="writer" id="writer" /></td>
		</tr>
		<tr>
			<td>제목</td>
			<td><input type="text" name="subject" id="subject" value="RE:${dto.subject}" /></td>
		</tr>
		<tr>
			<td>본문</td>
			<td><textarea name="content" id="content" cols="70" rows="10">${dto.content}</textarea></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="passwd" id="passwd" /></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" id="btnSave" value="확인" />
				<input type="button" id="btnList" value="취소" />
				<input type="hidden" name="num" value="${dto.num}" />
			</td>
		</tr>
	</table>
</form>

</body>
</html>