package aticleboard.mapper;

import java.io.IOException;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aticleboard.dao.BoardDAO;
import aticleboard.dto.BoardCommentDTO;
import aticleboard.dto.BoardDTO;


@WebServlet("/board_servlet/*")
public class boardController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Controller.....OK");
		
		String url = request.getRequestURL().toString();
		BoardDAO dao = new BoardDAO();
		
		//System.out.println(url);
		
// list		
		if(url.contains("list.do")) {
			System.out.println("list.do... OK");
		
			int curPage = 1;
			if(request.getParameter("curPage")!=null) {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}
			// 페이지 설정 시작
			int count = dao.recodeCount();
			int page_scale = 10; // 한 페이지에 출력되는 레코드 수
			int totPage =(int)Math.ceil(count*1.0/page_scale); // 총 페이지 수
			System.out.println("Total Page : "+totPage);
			
			int start = (curPage-1)*page_scale+1; // 페이지 시작 레코드 번호
			int end = start+page_scale-1;	// 페이지 끝 레코드 번호
			//페이지 설정 끝
			
			//블럭 설정 시작
			int block_scale = 10; // 한 블럭에 들어갈 페이지 수
			int totBlock = (int)Math.ceil(totPage*1.0/block_scale); // 페이지 수에 따라 필요한 블럭 수
			int curBlock = (int)Math.ceil(curPage*1.0/block_scale); // 선택한 페이지가 속한 블럭
			
			int startBlock = (curBlock-1)*block_scale+1;
			int endBlock = startBlock+block_scale-1;
			
			if(endBlock>totPage) endBlock=totPage;
			
			int prev = curBlock == 1 ? 1:(curBlock-1)*block_scale;
			int next = curBlock > totBlock ? (curBlock*block_scale) : (curBlock*block_scale)+1;
			
			if(next>=totPage) next=totPage;
			
			List<BoardDTO> list = dao.list(start, end);
			
			request.setAttribute("list", list);
			
			request.setAttribute("curBlock", curBlock);
			request.setAttribute("startBlock", startBlock);
			request.setAttribute("endBlock", endBlock);
			request.setAttribute("totBlock", totBlock);
			request.setAttribute("prev", prev);
			request.setAttribute("next", next);
			
			String page = "/aticleboard/list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
// view			
		}else if(url.contains("view.do")) {	
			System.out.println("view.do OK...");
			
			int num = Integer.parseInt(request.getParameter("num"));
			System.out.println("view.do: "+num);
			
			dao.readcount(num);
			
			BoardDTO dto = dao.view(num);

			request.setAttribute("dto", dto);
			
			String page = "/aticleboard/view.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
// reply			
		}else if(url.contains("reply.do")) {
			System.out.println("reply.do OK...");
			
			int num = Integer.parseInt(request.getParameter("num"));
			System.out.println("reply.do num : "+num);
			
			BoardDTO dto = dao.view(num);
			dto.setContent("=========게시물 내용=========\n"+dto.getContent());
			
			request.setAttribute("dto", dto);
			
			String page = "/aticleboard/reply.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
// insertReply			
		}else if(url.contains("insertReply.do")) {
			System.out.println("insertReply.do OK...");
			
			request.setCharacterEncoding("utf-8");
			
			int num = Integer.parseInt(request.getParameter("num"));
			
			BoardDTO dto = dao.view(num);
			
			int ref = dto.getRef();
			int re_step = dto.getRe_step()+1;
			int re_level = dto.getRe_level()+1;
			
			System.out.println("re_step : "+re_step+"\nre_level : "+re_level);
			
			String writer = request.getParameter("writer");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String passwd = request.getParameter("passwd");
					
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPasswd(passwd);
			dto.setRef(ref);
			dto.setRe_step(re_step);
			dto.setRe_level(re_level);
			
			
			dao.updateStep(ref,re_step);
			dao.reply(dto);
			
			String page = request.getContextPath()+"/board_servlet/list.do";
			response.sendRedirect(page);
// pwdCheck		
		}else if(url.contains("pwdCheck.do")) {
			System.out.println("pwdCheck.do");
			
			int num = Integer.parseInt(request.getParameter("num"));
			String passwd = request.getParameter("passwd");
			
			String result = dao.pwdCheck(num, passwd);
			
			String page = "";
			
			if(result != null) {
				
				request.setAttribute("dto", dao.view(num));
				page = "/aticleboard/edit.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);
			
			}else {
				page = request.getContextPath()+"/board_servlet/view.jsp";
				response.sendRedirect(page);
			}
			
		}else if(url.contains("update.do")) {
			System.out.println("update.do");
			
			BoardDTO dto = new BoardDTO();
			
			String writer = request.getParameter("writer");
			String subject = request.getParameter("subject");
			String passwd = request.getParameter("passwd");
			String content = request.getParameter("content");
			
			String ip = request.getRemoteAddr();
			int num = Integer.parseInt(request.getParameter("num"));
			
			dto.setNum(num);
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setPasswd(passwd);
			dto.setContent(content);
			dto.setIp(ip);
			
			//비밀번호 체크
			String result = dao.pwdCheck(num, passwd);
			
			if(result!=null) {
				
				dao.update(dto);
				
				String page = "${path}/board_servlet/list.do";
				response.sendRedirect(page);
				
			}else {
				
				request.setAttribute("dto", dto);
				
				String page = "/aticleboard/edit.jsp?pass_error=y";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);
			}
// delete		
		}else if(url.contains("delete.do")) {
			System.out.println("delete.do");
			
			BoardDTO dto = new BoardDTO();
			
			int num = Integer.parseInt(request.getParameter("num"));
			String passwd = request.getParameter("passwd");
			
			
			String result = dao.pwdCheck(num, passwd);
			
			if(result!=null) {
				dao.delete(num);
				
				String page = "${path}/board_servlet/list.do";
				response.sendRedirect(page);
				
			}else {
				request.setAttribute("dto",dto);
				
				String page = "/aticleboard/edit.jsp?pass_error=y";
				RequestDispatcher rd = request.getRequestDispatcher(page);
				rd.forward(request, response);
				
			}
// insert
		}else if(url.contains("insert.do")) {
			System.out.println("insert.do");
			
			BoardDTO dto = new BoardDTO();
			
			String writer = request.getParameter("writer");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String passwd = request.getParameter("passwd");
			String ip = request.getRemoteAddr();
			
			dto.setWriter(writer);
			dto.setSubject(subject);
			dto.setContent(content);
			dto.setPasswd(passwd);
			dto.setIp(ip);
			
			dao.insert(dto);
			
			String page = "${path}/board_servlet/list.do";
			response.sendRedirect(page);	
			
// writer			
		}else if (url.contains("write.do")) {
			System.out.println("write.do...확인");
			
			String page = request.getContextPath()+"/aticleboard/write.jsp";
			response.sendRedirect(page);
// search
		}else if (url.contains("search.do")) {
			System.out.println("search.do");
			
			String option = request.getParameter("option");
			String keyword = request.getParameter("keyword");
			
			List<BoardDTO> list = dao.search(option, keyword);
			
			request.setAttribute("list", list);
			
			String page = "/aticleboard/list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

// comment_add
		}else if (url.contains("comment_add.do")) {
			System.out.println("comment_add.do");
			
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			
			BoardCommentDTO dto = new BoardCommentDTO();
			dto.setWriter(writer);
			dto.setContent(content);
			dto.setBoard_num(board_num);
			
			dao.commentAdd(dto);

// comment_reply		
		}else if (url.contains("comment_rep.do")) {
			System.out.println("comment_rep.do");
			
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			List<BoardCommentDTO> list = dao.commentList(board_num);
			request.setAttribute("list", list);
			
			String page = "/aticleboard/comment_list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		}
			
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
