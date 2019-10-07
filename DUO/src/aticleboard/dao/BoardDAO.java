package aticleboard.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import aticleboard.dto.BoardCommentDTO;
import aticleboard.dto.BoardDTO;
import sqlMap.MyBatisManager;

public class BoardDAO {
// 게시글 목록
	public List<BoardDTO> list(int start, int end) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		List<BoardDTO> list = session.selectList("board.list",map);	
		session.close();
		
		return list;
	}
	
// view 페이지
	public BoardDTO view(int num) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		
		BoardDTO dto = session.selectOne("board.view", num);
		session.close();
		
		return dto;
	}

// 게시물 번호에 따른 답글달기
	public void reply(BoardDTO dto) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		
		session.insert("board.reply",dto);
		
		session.commit();
		session.close();
	}

// 게시물과 답글 출력 순서 조정
	public void updateStep(int ref, int re_step) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ref", ref);
		map.put("re_step", re_step);
		
		session.update("board.updateStep", map);
		session.commit();
		session.close();
	
	}
	
// 게시물 번호에 따른 수정
	public void update(BoardDTO dto) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		session.update("board.update", dto);
		session.commit();
		session.close();
	}
	
// 게시물 번호에 따른 삭제
	public void delete(int num) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		session.delete("board.delete", num);
		session.commit();
		session.close();
	}
	
// 수정, 삭제 시 비밀번호 체크
	public String pwdCheck(int num, String passwd) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		map.put("passwd", passwd);
		
		String result = session.selectOne("board.pwdCheck", map);
		session.close();
		
		return result;
	}

// 게시물 추가
	public void insert(BoardDTO dto) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		session.insert("board.insert", dto);
		session.commit();
		session.close();
	}
	
// 조건에 따른 게시물 검색
	public List<BoardDTO> search(String option, String keyword){
		SqlSession session = MyBatisManager.getInstance().openSession();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("option", option);
		map.put("keyword", "%"+keyword+"%");
		
		List<BoardDTO> list = session.selectList("board.search", map);
		session.commit();
		session.close();
		
		return list;
	}
	
// 조회수 증가	
	public void readcount(int num) {
		SqlSession session = MyBatisManager.getInstance().openSession();

		session.update("board.readcount", num);
		session.commit();
		session.close();
	}
	
// 테이블에 있는 총 레코드 수 계산
	public int recodeCount() {
		SqlSession session = MyBatisManager.getInstance().openSession();
		int recodeCount = session.selectOne("board.recodeCount");
		session.close();
		return recodeCount;
	}
	
// 게시글 번호에 따른 댓글 추가
	public void commentAdd(BoardCommentDTO dto) {
		SqlSession session = MyBatisManager.getInstance().openSession();
		session.insert("commentAdd", dto);
		session.commit();
		session.close();
		
		}
	
// 댓글 조회처리
	public List<BoardCommentDTO> commentList(int board_num){
		SqlSession session = MyBatisManager.getInstance().openSession();
		List<BoardCommentDTO> list = session.selectList("board.commentList", board_num);
		session.close();
		return list;
	
	}
}
