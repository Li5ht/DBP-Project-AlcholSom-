<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>시뮬레이터</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet href="<c:url value='/css/common.css' />" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<script>
var count = 0;

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
	<br><br>
	
	<div class="simulDiv">
	
	<form class="f1" id="f1" method="post" action="<c:url value='/simulate/result' />">
		
		<div class="simulDiv2" id="simulDiv2">
		
		
		<!-- 주량 -->
		주량 입력(ml) &nbsp;&nbsp;&nbsp;
		<c:if test="${drinkingCapacity gt 0}"> <!-- 로그인 되어 있을 경우 -->
			<select id="sel1_1" name="sel1_1" onchange="categorychange(this, 1)">
				<option value="0">주종 선택</option>
				<option value="소주" selected="selected">소주</option>
				<option value="맥주">맥주</option>
				<option value="전통주">전통주</option>
				<option value="와인">와인</option>
				<option value="양주">양주</option>
			</select>&nbsp;&nbsp;
			
			<select id="sel2_1" name="sel2_1">
				<c:forEach var="alcohol" items="${aSoju}">
					<c:if test="${alcohol eq '참이슬 오리지널'}">
						<option value="${alcohol}" selected="selected">${alcohol}</option>
					</c:if>
					<c:if test="${alcohol ne '참이슬 오리지널'}">
						<option value="${alcohol}">${alcohol}</option>
					</c:if>
				</c:forEach>
			</select>&nbsp;&nbsp;
			
			<input type="text" name="amount1" width="30" id="amount1" value="${drinkingCapacity}">
		</c:if>
		
		<c:if test="${drinkingCapacity le 0}"> <!-- 로그인 되어 있지 않을 경우 -->
			<select id="sel1_1" name="sel1_1" onchange="categorychange(this, 1)">
				<option value="0">주종 선택</option>
				<option value="소주">소주</option>
				<option value="맥주">맥주</option>
				<option value="전통주">전통주</option>
				<option value="와인">와인</option>
				<option value="양주">양주</option>
			</select>&nbsp;&nbsp;
			
			<select id="sel2_1" name="sel2_1">
				<option value="0">술 선택</option>
			</select>&nbsp;&nbsp;
			
			<input type="text" name="amount1" width="30" id="amount1">
		</c:if>
		<br><br>
		
		
		<!-- 마실 양 -->
		마실 양(ml) &nbsp;&nbsp;&nbsp;
		<select id="sel1_2" onchange="categorychange(this, 2)">
			<option value="0">주종 선택</option>
			<option value="소주">소주</option>
			<option value="맥주">맥주</option>
			<option value="전통주">전통주</option>
			<option value="와인">와인</option>
			<option value="양주">양주</option>
		</select>&nbsp;&nbsp;
		<select id="sel2_2">
			<option value="0">술 선택</option>
		</select>
		<input type="text" width="30" id="amount2"> 
		<a href="#" onclick="plus()"> + </a>
		<br><br><br><br><br><br>
		<%
			String sojuStr = "";
			String beerStr = "";
			String traditionalStr = "";
			String wineStr = "";
			String spiritsStr = "";
			
			String[] aSoju = (String[]) (request.getAttribute("aSoju"));
			String[] aBeer = (String[]) (request.getAttribute("aBeer"));
			String[] aTraditional = (String[]) (request.getAttribute("aTraditional"));
			String[] aWine = (String[]) (request.getAttribute("aWine"));
			String[] aSpirits = (String[]) (request.getAttribute("aSpirits"));
			
			for( int i = 0; i < aSoju.length; i++ ){
				if (i != (aSoju.length - 1)) {
					sojuStr = sojuStr + aSoju[i] + "/";
				} else {
					sojuStr = sojuStr + aSoju[i];
				}
			}
			
			for( int i = 0; i < aBeer.length; i++ ){
				if (i != (aBeer.length - 1)) {
					beerStr = beerStr + aBeer[i] + "/";
				} else {
					beerStr = beerStr + aBeer[i];
				}
			}
			
			for( int i = 0; i < aTraditional.length; i++ ){
				if (i != (aTraditional.length - 1)) {
					traditionalStr = traditionalStr + aTraditional[i] + "/";
				} else {
					traditionalStr = traditionalStr + aTraditional[i];
				}
			}
			
			for( int i = 0; i < aWine.length; i++ ){
				if (i != (aWine.length - 1)) {
					wineStr = wineStr + aWine[i] + "/";
				} else {
					wineStr = wineStr + aWine[i];
				}
			}
			
			for( int i = 0; i < aSpirits.length; i++ ){
				if (i != (aSpirits.length - 1)) {
					spiritsStr = spiritsStr + aSpirits[i] + "/";
				} else {
					spiritsStr = spiritsStr + aSpirits[i];
				}
			}
		%>
		
		
		<script>
    		function categorychange(e, num) {
    			var alcohol;
    			var aLength;
        		
        		
        		var i = "sel2_" + num.toString();
        		var target = document.getElementById(i);
        		
        		if (e.value == "소주") { 
        			var sojuStr = '<%=sojuStr%>';
        			alcohol = sojuStr.split("/");
        			aLength = alcohol.length;
        		}
        		else if (e.value == "맥주") {
        			var beerStr = '<%=beerStr%>';
        			alcohol = beerStr.split("/");
        			aLength = alcohol.length;
        		}
        		else if (e.value == "전통주") {
        			var traditionalStr = '<%=traditionalStr%>';
        			alcohol = traditionalStr.split("/");
        			aLength = alcohol.length;
        		}
        		else if (e.value == "와인") {
        			var wineStr = '<%=wineStr%>';
        			alcohol = wineStr.split("/");
        			aLength = alcohol.length;
        		}
        		else if (e.value == "양주") {
        			var spiritsStr = '<%=spiritsStr%>';
        			alcohol = spiritsStr.split("/");
        			aLength = alcohol.length;
        		}
        		
        		target.options.length = 0;
        		
        		for (var i = 0; i < aLength; i++) {
        		    var opt = document.createElement("option");
        			opt.value = alcohol[i];
        			opt.innerHTML = alcohol[i];
        			target.appendChild(opt);
        		}
    		}
    		
    		function plus() {
    			var type = $("#sel1_2").val();
    			var name = $("#sel2_2").val();
    			var amount = $("#amount2").val();
    			var amountInt = parseInt(amount);
    			
    			if (type != null && name != null && amountInt > 0 && type != '0' && name != '0') {
	    			count++;
	    			
	    			var form = document.getElementById("f1");
	    			
	    			
	    			
	    			var str = "drink" + count.toString();
	    			var drinkStr = type + "/" + name + "/" + amount;
	    			
	    			var hiddenField = document.createElement('input');
	    			hiddenField.setAttribute("name", str);
	    			hiddenField.setAttribute("type", "hidden");
	    			hiddenField.setAttribute("value", drinkStr);
	    			form.appendChild(hiddenField);
	    			
	    			$("#sel1_2").val("0").prop("selected", true);
	    			$("#sel2_2").val("0").prop("selected", true);
	    			$("#amount2").val("");
	    			
	    			
	    			var target = document.getElementById("simulDiv2");
	    			
	    			var c = count.toString();
	    			var textBox = c + ". " + type + " " + name + " " + amount + "ml";
	    			var newDiv = document.createElement('div');
	    			var newText = document.createTextNode(textBox);
	    			newDiv.className = "simulDiv4";
	    			newDiv.appendChild(newText);
	    			
	    			target.appendChild(newDiv);
    			} else {
    				alert('다시 입력해주세요.');
    			}
    		}
    		
    		function simulSubmit() {
    			var type = $("#sel1_1").val();
    			var name = $("#sel2_1").val();
    			var amount = $("#amount1").val();
    			var amountInt = parseInt(amount);
    			
    			if (type != null && name != null && amountInt > 0 && type != '0' && name != '0' && count > 0) {
	    			var form = document.getElementById("f1");
	    			
	    			var hiddenField = document.createElement('input');
	    			hiddenField.setAttribute("name", "count");
	    			hiddenField.setAttribute("type", "hidden");
	    			hiddenField.setAttribute("value", count);
	    			form.appendChild(hiddenField);
	    			
	    			form.submit();
    			} else {
    				alert ('주량 또는 마실 양을 다시 입력해주세요.');
    			}
    		}
    	</script>
		</div>
		
		
		
		<div class="simulDiv3">
			<br><br><br>
			<input type="button" value="시뮬레이션" onclick="simulSubmit()">
		</div>
		
		
	</form>
	
	</div>
</body>
</html>