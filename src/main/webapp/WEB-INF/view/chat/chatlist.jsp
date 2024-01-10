<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
	<div class="container mt-2">
    	<div class="my-3 p-3 bg-body rounded shadow-sm">
		    <h6 class="border-bottom pb-2 mb-0">최근 메세지</h6>
		    
		   <c:forEach var="one" items="${complex }">
			    <div class="d-flex text-body-secondary pt-3" onclick="location.href='${contextPath}/chat/room/${one.chatRoom.id }'" style="cursor: pointer;">
			      <img src = "${contextPath }${one.product.productImages[0].url}" 
			      		class="bd-placeholder-img flex-shrink-0 me-2 rounded" width="32" height="32" 
			      		xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" 
			      		preserveAspectRatio="xMidYMid slice" focusable="false"/>
				      
			      <p class="pb-3 mb-0 small lh-sm border-bottom">
			        <strong class="d-block text-gray-dark">${one.product.title}</strong>
			        <span class="text-secondary" style="font-size: xx-small">${one.recentMessage }</span>
			      </p>
			    </div>	
		   </c:forEach>

		  </div>
    </div>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>