<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>시뮬레이션 결과</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet href="<c:url value='/css/common.css' />" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script>

</script>
</head>
<body>
	<!-- 네비게이션 바 -->
	<%@include file="/WEB-INF/navbar.jsp" %>
	
	<!-- 배너 -->
	<div class="d0">
		<div class="grad1">
			<br><br><br><br><br> S I M U L A T O R <br><br><br>
		</div>
	</div>
	<br><br><br><br>
	
	<div class="simulDiv">
		<img src="<c:url value='${con}' />"/>
		
		<br><br>
	
		<div><a href="<c:url value='/simulate' />" style="color:#28a745;">다시하기</a></div>
	</div>
</body>
</html>