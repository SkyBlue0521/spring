package com.myspring.pro27.board.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro27.board.vo.ArticleVO;
import com.myspring.pro27.member.vo.MemberVO;

@Repository("boardDAO")
public class BoardDAO {
	@Autowired
	private SqlSession sqlSession;
	
	public List<ArticleVO> selectAllArticlesList() throws DataAccessException {
		List<ArticleVO> membersList = null;
		membersList = sqlSession.selectList("mapper.article.selectAllArticlesList");
		return membersList;
	}

	public MemberVO articleForm(String id) {
		MemberVO articleId = sqlSession.selectOne("mapper.member.selectMemberById",id);
		return articleId;
	}

	public int insertNewArticle(Map<String, Object> articleMap) {

		sqlSession.selectList("mapper.article.insertNewArticle",articleMap);
		return 0;
	}
}
