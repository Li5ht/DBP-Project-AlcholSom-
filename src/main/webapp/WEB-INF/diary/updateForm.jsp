<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String year = request.getParameter("tmpYear");
	String month = request.getParameter("tmpMonth");
	String date = request.getParameter("tmpDate");

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
var count = 0;

function diaryUpdate(id) {
	// 폼 내용 확인
	var form = document.getElementById("diaryUpdateForm");
	var hiddenField = document.createElement('input');
	hiddenField.setAttribute("name", "diaryId");
	hiddenField.setAttribute("type", "hidden");
	hiddenField.setAttribute("value", id);
	form.appendChild(hiddenField);
	
	var hiddenField2 = document.createElement('input');
	hiddenField2.setAttribute("name", "count1");
	hiddenField2.setAttribute("type", "hidden");
	hiddenField2.setAttribute("value", count);
	form.appendChild(hiddenField2);
	
	var presentDrinkingList = document.getElementById("presentDrinkingList");
	
	
	
	form.submit();
	alert('음주 기록이 수정되었습니다.');
}

function diaryDelete() {
	// 삭제 알림
	alert("삭제되었습니다.");
}

	
function changeConditionValue(n){
	var v = n;
	document.getElementById("condition2").value = n;
}	
	
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
	let type = $("#sel1_3").val();
	let name = $("#sel2_3").val();
	let amount = $("#amount2").val();
	let amountInt = parseInt(amount);
	
	if (type != null && name != null && amountInt > 0 && type != '0' && name != '0') {
		count++;
		
		var form = document.getElementById("diaryUpdateForm");
		
		var str = "drink." + count.toString();
		var drinkStr = type + "/" + name + "/" + amount;
		
		var hiddenField = document.createElement('input');
		hiddenField.setAttribute("name", str);
		hiddenField.setAttribute("type", "hidden");
		hiddenField.setAttribute("value", drinkStr);
		form.appendChild(hiddenField);
		
		$("#sel1_3").val("0").prop("selected", true);
		$("#sel2_3").val("0").prop("selected", true);
		$("#amount2").val("");
		
		var target = document.getElementById("todayAlcoholList2");
		
		var textBox = type + " " + name + " " + amount + "ml";
		var newDiv = document.createElement('div');
		var newText = document.createTextNode(textBox);
		var newSpan = document.createElement('span');
		var deleteText = document.createTextNode('');
		newDiv.className = "drinkingList";
		newDiv.id = "drinkingList" + count;
		newSpan.id = "deleteBtn" + count;
		newSpan.onclick = function(){ newDiv.remove(); } 
		newDiv.appendChild(newText);
		newSpan.appendChild(deleteText);
		newDiv.appendChild(newSpan);
		
		target.appendChild(newDiv);
	} else {
		alert('다시 입력해주세요.');
	}
}

</script>

<!-- 회원가입이 실패한 경우 exception 객체에 저장된 오류 메시지를 출력 -->
<div class="row col-lg-12">
	<c:if test="${creationFailed}">
		<h6 class="text-danger">
			<c:out value="${exception.getMessage()}" />
		</h6>
	</c:if>
</div>

<!-- registration form  -->
<form name="form" id="diaryUpdateForm" method="POST" action="<c:url value='/diary/update' />">
	<div style="width: 100px; margin: auto">
		<strong>음주 기록</strong>
	</div>
	<div class="parent green">
		<label for="date" class="diaryLabel"><strong>날짜</strong></label>
		<div class="diaryInput">
			<input class="diaryAmount" type="date" name="drinkingDate" id="drinkingDate" value="${drinkingDate}" style="margin:5px;">
		</div>
	</div>
	<div class="green">
		<label for="alcohol"><strong>오늘의 술</strong></label>
		<div class="parent">
			<div class="diaryLabel">
				<select id="sel1_3" onchange="categorychange(this, 3)">
					<option value="0">주종 선택</option>
					<option value="소주">소주</option>
					<option value="맥주">맥주</option>
					<option value="전통주">전통주</option>
					<option value="와인">와인</option>
					<option value="양주">양주</option>
				</select>
				
			</div>&nbsp;
			<div class="diaryLabel">
				<select id="sel2_3">
					<option value="0">술 선택</option>
				</select>
			</div>&nbsp;
			<div class="diaryInput">
				<input class="diaryAmount" type="text" id="amount2"> 
			</div>&nbsp;
			<div class="diaryInput">
				<a href="#" onclick="plus()"> + </a>
			</div>
		</div>
	</div>
	<div id="todayAlcoholList2">
	<c:forEach var="drink" items="${diary.drinkingList}">
		<div class="drinkingList" id="${drink.alcohol.type}${drink.alcohol.name}${drink.amount}">
			${drink.alcohol.type} ${drink.alcohol.name} ${drink.amount}ml
		</div>
	</c:forEach>	
	</div>
	
	<div class="parent green">
		<label for="condition" class="diaryLabel"><strong>상태</strong></label>
		<div class="diaryLabel" onclick="changeConditionValue(1)">
			🤢
		</div>
		<div class="diaryLabel" onclick="changeConditionValue(2)">
			😣
		</div>
		<div class="diaryLabel" onclick="changeConditionValue(3)">
			🙂
		</div>
		<div class="diaryLabel" onclick="changeConditionValue(4)">
			😊
		</div>
		<div class="diaryLabel" onclick="changeConditionValue(5)">
			😆
		</div>
		<input type="number" name="condition2" id="condition2" class="diaryCondition" value="${diary.condition}" readonly>
	</div>
	<div class="green">
		<label for="content"><strong>오늘의 일기</strong></label>
		<div class="col-lg-10">
			<textarea name="content">${diary.content}</textarea>
		</div>
	</div>
	<br>
	<div class="form-group">
		<input type="button" class="btn btn-success" value="수정" onClick="diaryUpdate(${diary.diaryId})">
		<a class="btn btn-outline-success"  onClick="diaryDelete()"
	    	href="<c:url value='/diary/delete'> 
	    			<c:param name='diaryId' value='${diary.diaryId}' />
	    		</c:url>">삭제하기</a> 
			
	</div>
</form>
