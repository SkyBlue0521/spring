package com.myspring.pro27.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro27.member.vo.MemberVO;

public interface MemberController {
	public ModelAndView listMembers(HttpServletRequest request, HttpServletRequest response) throws Exception;

	public ModelAndView addMember(@ModelAttribute("info") MemberVO memberVO, HttpServletRequest request,
			HttpServletRequest response) throws Exception;

	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletRequest response) throws Exception;

	public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr,
			HttpServletRequest request, HttpServletRequest response) throws Exception;

	public ModelAndView logout(HttpServletRequest request, HttpServletRequest response) throws Exception;

	public ModelAndView selectMemberById(String id, HttpServletRequest request, HttpServletRequest response) throws Exception;

	public ModelAndView updateMember(MemberVO member, HttpServletRequest request, HttpServletRequest response)
			throws Exception;
}
