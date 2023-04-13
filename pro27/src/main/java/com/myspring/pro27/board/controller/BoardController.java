package com.myspring.pro27.board.controller;


import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro27.board.service.BoardService;
import com.myspring.pro27.board.vo.ArticleVO;
import com.myspring.pro27.member.vo.MemberVO;


@Controller("boardController")
public class BoardController extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO;
	@Autowired
	private MemberVO memberVO;


	@RequestMapping(value = "/board/listArticles.do", method = RequestMethod.GET)
	public ModelAndView listArticles(HttpServletRequest request, HttpServletRequest response) throws Exception {
		String viewName = getViewName(request);
		logger.info("viewName : " + viewName);
		logger.debug("viewName : " + viewName);
		List articlesList = boardService.listArticles();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articlesList", articlesList);
		return mav;
	}
	@RequestMapping(value = "/board/articleForm.do", method = RequestMethod.GET)
	public ModelAndView articleForm(@RequestParam("id") String id, HttpServletRequest request, HttpServletRequest response)
			throws Exception {
		String viewName = getViewName(request);
		logger.info("viewName : " + viewName);
		logger.debug("viewName : " + viewName);
		request.setCharacterEncoding("utf-8");
		MemberVO articleId = boardService.articleForm(id);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articleId", articleId);
		return mav;
	}
	
	@RequestMapping(value = "/board/addArticle.do", method = RequestMethod.POST)
	public ResponseEntity addArticle(MultipartHttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String)enu.nextElement();
			String value = request.getParameter(name);
			articleMap.put(name, value);
		}
		
		String imageFileName = upload(request);
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		articleMap.put("parentNO", 0);
		articleMap.put("id", id);
		articleMap.put("imageFileName", imageFileName);
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			int articleNO = boardService.addArticle(articleMap);
			if(imageFileName!=null && imageFileName.length()!=0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO+ "\\" + "temp"+ "\\" + imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
			}
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += "location.href='"+request.getContextPath()+"/board/listArticles.do';";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			message = "<script>";
			message +=" alert('오류가 발생했습니다. 다시 시도해 주세요');');";
			message +="location.href='"+request.getContextPath()+"/board/articleForm.do';";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
}


	private String getViewName(HttpServletRequest request) throws Exception{
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if(uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}
		
		int begin = 0;
		if(!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}
		int end;
		if(uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}
		
		String viewName = uri.substring(begin, end);
		if(viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0,viewName.lastIndexOf("."));
		}
		if(viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}
	
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception {
		String imageFileName = null;
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName = mFile.getOriginalFilename();
			File file = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+fileName);
			if(mFile.getSize()!=0) {//File Null Check
				if(!file.exists()) {//경로상에 파일이 존재하지 않을 경우
					file.getParentFile().mkdirs(); 
					//경로에 해당하는 디렉토리들을 생성
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\"+imageFileName));
					//임시로 저장된 multipartFile을 실제 파일로 전송
		
			}
		}
	}
		return imageFileName;
	}

}
