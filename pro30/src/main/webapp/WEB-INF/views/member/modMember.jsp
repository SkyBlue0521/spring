<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("utf-8"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modMember.jsp</title>
<style type="text/css">
	.cls1{
		font-size:40px;
		text-align:center;
	}
	.cls2{
		text-align:center;
	}
	table tr td:nth-child(odd){
		width:200;
	}
	table tr td:nth-child(even){
		width:400;
	}
	table tr td p{
		text-align:right;
	}
	.text_center{
		text-align:center;
	}
</style>
 
<script type="text/javascript">
function fn_sub(obj){
	document.getElementById("i_id").disabled=false;
	obj.submit();
}
</script>

</head>
<body>
	<form name="frm" method="post" action="${contextPath }/member/updateMember.do">
		<h1 class="text_center">회원 정보 수정창</h1>
		<table align="center">
			<c:if test="${member != null }">
			<tr>
				<td><p>아이디</p></td>
				<td><input type="text" name="id" id="i_id" value="${member.id }" readonly></td>
			</tr>
			<tr>
				<td><p>비밀번호</p></td>
				<td><input type="password" name="pwd" value="${member.pwd }"></td>
			</tr>
			<tr>
				<td><p>이름</p></td>
				<td><input type="text" name="name" value="${member.name }"></td>
			</tr>
			<tr>
				<td><p>이메일</p></td>
				<td><input type="text" name="email" value="${member.email }"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" onClick="fn_sub(this.form)" value="수정하기"><input type="reset"></td>
			</tr>
			</c:if>
		</table>
	</form>
	
</body>
</html>