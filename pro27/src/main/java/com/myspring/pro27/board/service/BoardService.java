package com.myspring.pro27.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro27.board.dao.BoardDAO;
import com.myspring.pro27.board.vo.ArticleVO;
import com.myspring.pro27.member.vo.MemberVO;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;
 
 public BoardService(){
	 
	 boardDAO = new BoardDAO();
 }
	public List<ArticleVO> listArticles() {
		List<ArticleVO> articlesList = boardDAO.selectAllArticlesList();
		return articlesList;
		
	}
	public MemberVO articleForm(String id) {
		MemberVO articleId = boardDAO.articleForm(id);
		return articleId;
	}


	public int addArticle(Map<String, Object> articleMap) {
		boardDAO.insertNewArticle(articleMap);
		return 0;
	}

}
