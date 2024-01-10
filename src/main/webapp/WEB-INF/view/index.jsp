<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/view/component/header.jspf"%>
	<div class="container mt-2">
		<div class="py-5 text-center">
			<div class="row py-lg-5">
				<div class="col-lg-6 col-md-8 mx-auto">
					<h1 class="fw-light"> <i class="bi bi-cart4"></i> OPEN MARKET </h1>
					
				</div>
			</div>
		</div>
		<form action="${contextPath }/index">
			<div class="input-group mb-3">
					<input type="text" name="search" class="form-control form-control-sm" value="${param.search }">
					<button class="btn btn-outline-secondary" type="button" id="button-addon2">검색 <i class="bi bi-search"></i> </button>
			</div>
		</form>
	</div>
	<div class="album py-3 bg-body-tertiary">
		<div class="container">
		<c:choose>
			<c:when test="${empty list }">
				<h4>"${param.search }" 에 일치하는 상품을 찾지 못했습니다.</h4>
			</c:when>
			<c:otherwise>
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
				<c:forEach var="one" items="${list }">
				<div class="col" onclick="location.href='${contextPath }/product/${one.id }'" style="cursor: pointer;">
					<div class="card shadow-sm">
						<img src="${contextPath }${one.productImages[0].url}" alt="사진"
							class="bd-placeholder-img card-img-top" width="100%"
							height="225" xmlns="http://www.w3.org/2000/svg" role="img"
							aria-label="Placeholder: Thumbnail"
							preserveAspectRatio="xMidYMid slice" focusable="false"/>
							<title>${one.title }</title>
							<rect width="100%" height="100%" fill="#55595c"></rect>
						<div class="card-body">
							<p class="card-text text-truncate">${one.title }</p>
							<div class="d-flex justify-content-between align-items-center">
								<div>
									<c:choose>
										<c:when test="${one.type eq 'sell' }">
											<span class="badge bg-dark">판매</span>
											<small class="text-body-secondary">
												<fmt:formatNumber pattern="#,###" value="${one.price }"/>원</small>
										</c:when>
										<c:otherwise>
											<span class="badge bg-success">나눔</span>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="btn-group">
									<small>
											<img src="${fn:startsWith(one.account.profileImageUrl, '/upload') ? contextPath:'' }${one.account.profileImageUrl }"
											width="42" height="42" class="rounded-circle " />
											${one.account.nickname }
									</small>
								</div>
							</div>
						</div>
					</div>
				</div>
					</c:forEach>
			</div>
			</c:otherwise>
		</c:choose>
		
			<div class="mt-2">
				<ul class="pagination justify-content-center">
					<c:if test="${currentPage ne 1 }">
					    <li class="page-item">
					    <a class="page-link" href="?p=${currentPage-1 }&search=${param.search}">&lt;</a></li>
						
					</c:if>
				    <c:forEach var="p" begin="1" end="${end }">
				    	 <li class="page-item">
				    	 <a class="page-link ${p eq currentPage ? 'active' :'' }" href="?p=${p }&search=${param.search}">${p }</a>
				    	 </li>
				    </c:forEach>
				    <c:if test="${currentPage ne end }">
					    <li class="page-item">
					  	 <a class="page-link" href="?p=${correntPage+1 }&search=${param.search}">&gt;</a></li>		
					</c:if>
			  </ul>
			</div>
		</div>
	</div>


<%@ include file="/WEB-INF/view/component/footer.jspf"%>
