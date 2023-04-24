<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원검색</title>
</head>
<body>
	<h1>회원검색</h1>
	<form type="hidden" name="action" value="searchMember" />
	이름:<input type="text" name="name" /><br>
	이메일:<input type="text" name="email" /><br>
	<input type="submit" value="검색"	/>
	</form>
</body>
</html>