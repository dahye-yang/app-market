<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
	<div class="container mt-3 d-flex flex-column"
				style="position: relative ; height: 85vh">
    	<div style="position: absolute; top: 0; width: 96.5%; z-index:10; cursor: pointer;" class="bg-dark text-white d-flex p-2
    						align-items-center gap-3" onclick = "location.href='${contextPath }/product/${product.id }'">
    		<img alt="" src="${contextPath }${product.productImages[0].url }" width="64" height ="64"/>
    		<span>${product.title }</span>
    		<small><fmt:formatNumber pattern="#,###" value="${product.price}"/>원 </small>
    	</div>
    	
    	<div class="flex-grow-1 mb-4" style="overflow: auto;z-index:1; margin-top: 80px;  margin-bottom:80px; width: 96.5%" id="chatView">
    		<c:forEach var="one" items="${chatMessages }" varStatus="status">
    			<c:choose>
    				<c:when test="${state.first }">
    					<fmt:formatDate value="${one.sentAt }" pattern="yyyy년 MM월 dd일 E요일" var="now"/>
    					<div class="d-flex justify-content-center my-1 align-items-end gap-q">
    						<div class="card px-2 py-q bg-dark text-white">
    							<small>${now }</small>
    						</div>
    					</div>
    				</c:when>
    				<c:otherwise>
    					<fmt:formatDate value="${one.sentAt }" pattern="yyyy년 MM월 dd일 E요일" var="next"/>
    					<c:if test="${now != next }">
    						<hr />
	    					<div class="d-flex justify-content-center my-1 align-items-end gap-q">
	    						<div class="card px-2 py-q bg-dark text-white">
	    							<small>${next }</small>
	    						</div>
	    					</div>
    					</c:if>
    					<c:set var="now" value="${next }"/>
    				</c:otherwise>
    			</c:choose>
    			
    			<!-- 읽음 안읽음을 뺐음????? -->
    			<c:choose>
				<c:when test="${one.talkerId eq sessionScope.logonAccount.id }">
					<div id="${one.id }"
						class="d-flex justify-content-end my-1 align-items-end gap-1 ">
						<div style="font-size: xx-small;">
							<fmt:formatDate pattern="a hh:mm" value="${one.sentAt }" />
						</div>
						<div class="card px-2 py-1 " style="background-color: peachpuff">${one.content }</div>
					</div>
				</c:when>
				<c:otherwise>
					<div id="${one.id }"
						class="d-flex justify-content-start  my-1 align-items-end gap-1">
						<div class="card px-2 py-1" style="background-color: aliceblue">${one.content }</div>
						<div style="font-size: xx-small;">
							<fmt:formatDate pattern="a hh:mm" value="${one.sentAt }" />
						</div>
					</div>
				</c:otherwise>
			</c:choose>
    			 
    		</c:forEach>
    	</div>
    	<div style="position: absolute; bottom: 0;z-index:10; width: 95%;" class="input-group">
    		<input type="text" class="form-control form-control-sm" id="msg">
  			<button class="btn btn-outline-secondary" type="button" id="sendBt">
  				<i class="bi bi-send"></i>
  			</button>
    	</div>
    </div>
    
   <script>
   		
   		function sendChatMessage(){
   			const value = document.querySelector('#msg').value;
   			if(value){
   				const xhr = new XMLHttpRequest();
   				xhr.open("POST","${contextPath}/chat/room/${chatRoom.id}/message",true);
   				
   				xhr.setRequestHeader("content-type","application/x-www-form-urlencoded");
   				xhr.send("content="+value);
   				
   				
   				xhr.onreadystatechange=function(){
   					if(xhr.readyState==4){
   						var response = JSON.parse(xhr.responseText);
   						//window.alert(response.result);
   						document.querySelector('#msg').value="";
   						getLatestMessage();
   					}
   				}
   			}
   			
   		}
  	
   		document.querySelector('#sendBt').onclick = sendChatMessage;
   		
   		document.querySelector('#msg').onkeyup = function(e){
  
   			if(e.keyCode==13){
   				sendChatMessage();
   			}
   		};
   		
   	</script>
   	
   	<script>
	var logonAccountId = '${sessionScope.logonAccount.id}';
	// 추가된 최신 메시지 얻어오는 AJAX 함수
	
	function getLatestMessage() {
		const id = document.querySelector("#chatView").lastElementChild.id;
		
		
		const xhr = new XMLHttpRequest();
		xhr.open("GET", "${contextPath}/chat/room/${chatRoom.id}/latest?lastMessageId="+id);
		xhr.onreadystatechange=function() {
			if(xhr.readyState==4) {
				var response = JSON.parse(xhr.responseText);
				console.log(response);
				if(response.result == 0 )
					return;
				// response 안에 messages 라는 객체배열이 있을꺼임
				for(var msg of response.messages) {
					if(logonAccountId == msg.talkerId ) {
						const div = document.createElement("div");
						div.id = msg.id;
						div.className = "d-flex justify-content-end my-1 align-items-end gap-1 ";
							const inDiv1 = document.createElement("div");
							inDiv1.style.fontSize="xx-small";
							inDiv1.textContent = msg.strSentAt;
						div.appendChild(inDiv1);
							const inDiv2 = document.createElement("div");
							inDiv2.className="card px-2 py-1 ";
							inDiv2.style.backgroundColor = "peachpuff";
							inDiv2.textContent = msg.content;
						div.appendChild(inDiv2);
						document.querySelector("#chatView").appendChild(div);
					}else {
						const div = document.createElement("div");
						div.id = msg.id;
						div.className = "d-flex justify-content-start  my-1 align-items-end gap-1";
							const inDiv1 = document.createElement("div");
							inDiv1.className="card px-2 py-1 ";
							inDiv1.style.backgroundColor = "aliceblue";
							inDiv1.textContent = msg.content;
						div.appendChild(inDiv1);
							const inDiv2 = document.createElement("div");
							inDiv2.style.fontSize="xx-small";
							inDiv2.textContent = msg.strSentAt;
							div.appendChild(inDiv2);
						document.querySelector("#chatView").appendChild(div);
					}
				} // end for
				
				document.querySelector("#chatView").scrollTop = document.querySelector("#chatView").scrollHeight;
				
			}	
		}
		xhr.send();
	}
	
	setInterval(getLatestMessage, 1000);
	
</script>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>
 