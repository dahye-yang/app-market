<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/view/component/header.jspf"%>
	<div class="container mt-2">
		<div class="p-4 p-md-5 mb-4 rounded text-body-emphasis bg-body-secondary">
		    <div class="col-lg-6 px-0">
		      <h1 class="display-4 fst-italic">찜하기</h1>
		      <p class="lead my-3"></p>
		    </div>
	    </div>
    </div>
    <div class="container">
		<div class="row mb-2">
		<c:forEach var="one" items="${picklist }">
			<div class="col-md-6" onclick="location.href='${contextPath }/product/${one.productId }'" style="cursor: pointer">
				<div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
					<div class="col-auto d-none d-lg-block">
						<img src="${contextPath }${one.url}" alt="사진"
						    class="bd-placeholder-img" width="150" height="150"
							xmlns="http://www.w3.org/2000/svg" role="img"
							aria-label="Placeholder: Thumbnail"
							preserveAspectRatio="xMidYMid slice" focusable="false"/>		
					</div>
					<div class="col p-4 d-flex flex-column position-static">
						<div class="mb-3">
							<h6>${one.title }</h6>
							<c:choose>
								<c:when test="${one.type eq 'sell' }">
									<div><b><fmt:formatNumber pattern="#,###" value="${one.price }"/></b>원 </div>
								</c:when>
								<c:otherwise>
									<span class="badge bg-success" style="width: 50px">나눔</span>
								</c:otherwise>
							</c:choose>
						</div>
						<div>
							<div> 
							<small><i class="bi bi-geo-alt"></i>${one.address }</small></div>
							<div><i class="bi bi-suit-heart-fill"></i>  ${one.count }</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
		
		</div>
	</div>

<%@ include file="/WEB-INF/view/component/footer.jspf"%>
