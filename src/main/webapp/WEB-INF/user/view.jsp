<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>사용자 정보 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet href="<c:url value='/css/common.css' />" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

</head>
<body>
<%@include file="/WEB-INF/navbar.jsp" %>

<div class="container">  
	<br>
	<h4>사용자 정보 조회</h4>
	<br>
	<table class="table table-sm table-striped">
    	<tbody> 
	  	  <tr>
			<th>사용자 ID</th>
			<td>${user.userId}</td>
		  </tr>
	  	  <tr>
			<th>닉네임</th>
			<td>${user.nickname}</td>
		  </tr>
		  <tr>
			<th>이메일 주소</th>
			<td>${user.email}</td>
		  </tr>	
	  	  <tr>
			<th>생일</th>
			<td>${user.birth}</td>
		  </tr>
	  	  <tr>
			<th>성별</th>
			<td>
				<c:choose>
				<c:when test="${user.gender eq 0}">남자</c:when>
				<c:otherwise>여자</c:otherwise>
				</c:choose>
			</td>
		  </tr>
		  <tr>
		  	<th>주량(알콜량, 단위 : g)</th>
		  	<td>${user.drinkingCapacity }</td>
		  </tr>
		</tbody>
	</table>
	<br> 		     
    <a class="btn btn-outline-success" 
    	href="<c:url value='/user/update' >
     		     <c:param name='userId' value='${user.userId}'/>
		 	  </c:url>">수정</a>
     <br>
	    
	<!-- 수정 또는 삭제가  실패한 경우 exception 객체에 저장된 오류 메시지를 출력 -->
	<c:if test="${updateFailed}">
		<h6 class="text-danger"><c:out value="${exception.getMessage()}"/></h6>
    </c:if>  
</div>  
</body>
</html>