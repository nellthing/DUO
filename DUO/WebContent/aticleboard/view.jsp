<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>View Page</title>
	<script src="../script/jquery-3.4.1.min.js"></script>
	<link href="../css/style.css" rel="stylesheet" />
	<script>
		$(function(){
			console.log("Jquery 실행 OK");
			
			$('#btnEdit').click(function(){
				console.log('수정/삭제 click 완료');
				
				var passwd = $('#passwd').val();
				
				if(passwd == ""){
					alert('비밀번호를 입력하세요.');
					$('#passwd').focus();
					return;
				}else if(passwd != ${dto.passwd}){
					alert('비밀번호가 다릅니다.');
					$('#passwd').focus();
					return;
				}else if(passwd != ""){
					
					document.frm.action="${path}/board_servlet/pwdCheck.do";
					document.frm.submit();
				}
			});
			
			$('#btnReply').click(function(){
				console.log('답변 click 완료');
				
				document.frm.action="${path}/board_servlet/reply.do";
				document.frm.submit();	
			});
			
			$('#btnList').click(function(){
				console.log('목록 click 완료');
				
				location.href="${path}/board_servlet/list.do";
			});
			
			// 댓글입력
			$('#btnSave').click(function(){
				console.log('댓글 저장 완료');
				
				var param =  "board_num=${dto.num}"+
								"&writer="+$('#writer').val()+
								"&content="+$('#content').val();
				
				$.ajax({
					type : "post",
					url : "${path}/board_servlet/comment_add.do",
					data : param,
					success : function(){
						console.log("댓글 추가 확인");
						$('#writer').val("");
						$('#content').val("");
						$('#writer').focus();
						
						comment_list();
					}
					
				});
			});
			
			// 댓글목록
			function comment_list(){
				console.log('댓글 목록 확인');
				
				var param = "board_num=${dto.num}";
				console.log(param);
				
				$.ajax({
					type : "get",
					url : "${path}/board_servlet/comment_rep.do",
					data : param,
					success : function(result){
						console.log('success');
						$('#commentList').html(result);
					}
				});
			}
		});
	</script>
</head>
<body>
<h2>게시물 내용</h2> <hr />
	<form action="" name="frm" method="post">
		<table>
			<tr>
				<td>날짜</td>
				<td>${dto.reg_date}</td>
				<td>조회수</td>
				<td>${dto.readcount}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td colspan="3">${dto.writer}</td>
			</tr>
			<tr>
				<td>제목</td>
				<td colspan="3">${dto.subject}</td>
			</tr>
			<tr>
				<td>본문</td>
				<td colspan="3">${dto.content}</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td colspan="3">
				<input type="password" id="passwd" name="passwd" />		
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="hidden" name="num" value="${dto.num}" />
					<input type="button" id="btnEdit" value="수정/삭제" />
					<input type="button" id="btnReply" value="답변" />
					<input type="button" id="btnList" value="목록" />
				</td>
			</tr>
		</table>
	</form>
<!-- 댓글 -->
<table>
	<tr>
		<td><input type="text" id="writer" placeholder="이름" /></td>
		<td rowspan="2">
			<input type="button" id="btnSave" value="확인" />
		</td>
	</tr>
	<tr>
		<td>
			<textarea id="content" cols="80" rows="5" placeholder="댓글 내용일 입력하세요."></textarea>
		</td>
	</tr>
</table> <br />
<!-- 댓글목록 -->
<div id="commentList"></div>
</body>
</html>