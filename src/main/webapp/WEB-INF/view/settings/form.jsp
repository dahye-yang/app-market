<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf"%>
<div class="container mt-3">
	<h4>
		내 정보
	</h4>
	<div class="row gx-3">
		<div class="col-md-6 p-2">
			<h6>
				<i class="bi bi-camera-fill"></i> 프로필 이미지
			</h6>
			<div>
				<img alt="" src="${sessionScope.logonAccount.profileImageUrl }"
					width="200" height="200" class="rounded-circle" style="cursor: pointer;"
					onclick="document.querySelector('#profileImage').click();" id="profileImageView">
			</div>
			<div style="display:none">
				<input type="file" class="form-control" id="profileImage" accept="image/*"/>
			</div>
				<h6>
					<i class="bi bi-person-badge"></i>닉네임
				</h6>
			<div>
				<input class="form-control" type="text"
					value="${sessionScope.logonAccount.nickname }">
			</div>
			<div>
				<button type="submit" class="form-control mt-2" style="background-color: #F1E4C3">변경</button>
			</div>
		</div>
		<div class="col-md-6 p-2">
			<h6>
				<i class="bi bi-pin-map-fill"></i> 동네 설정
			</h6>
			<div id="map" style="height: 400px; background-color: #dddd"></div>
			<div class="mt-2">
				<form action="${pageContext.servletContext.contextPath }/settings" method="post">
				<input type="hidden" name="_method" value="post"/>
					<div>
						<input type="text" class="form-control" id="address" name="address" readonly="readonly"
								value="${found.address }">
						<input type="hidden" name="latitude" id="latitude" value="${found.latitude }">
						<input type="hidden" name="longitude" id="longitude" value="${found.longitude }" >
					</div>
					<div>
						<button type="submit" class="form-control mt-2" style="background-color: #F1E4C3">설정</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6e176e54a610869f5a1784cccfbe39a6&libraries=services"></script>
<script>

	//이미지 미리보기 스크립트
	document.querySelector('#profileImage').onchange= function(e){
		if(e.target.files[0]){
			var fileReader = new FileReader();
			fileReader.readAsDataURL(e.target.files[0]);
			fileReader.onload = function(e){
				document.querySelector('#profileImageView').src =  e.target.result;
			}
		}
	};
	
	
	//지도 스크립트
	var lat = ${empty found.latitude ? 33.450701 : found.latitude};
	var lng = ${empty found.longitude ? 126.570667 : found.longitude};
	
	var container = document.querySelector("#map"); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center : new kakao.maps.LatLng(lat, lng),
		level :4
	};
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	var geocoder = new kakao.maps.services.Geocoder();

	// 마커가 표시될 위치입니다 
	var marker = new kakao.maps.Marker({
		position : map.getCenter()
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
  
	//var geocoder = new kakao.maps.services.Geocoder();
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

		// 클릭한 위도, 경도 정보를 가져옵니다 
		var latlng = mouseEvent.latLng;

		// 마커 위치를 클릭한 위치로 옮깁니다
		marker.setPosition(latlng);
		map.setLevel(4);
		map.panTo(latlng);
		
		geocoder.coord2RegionCode(latlng.getLng(), latlng.getLat(), function displayCenterInfo(result, status) {
		    if (status === kakao.maps.services.Status.OK) {

		        for(var i = 0; i < result.length; i++) {
		            // 행정동의 region_type 값은 'H' 이므로
		            if (result[i].region_type === 'H') {
		                document.querySelector("#address").value = result[i].address_name;
		                //console.log(document.querySelector("#address").value);
		                break;
		            }
		        }
		    }    
		    document.querySelector("#latitude").value = latlng.getLat();
		    document.querySelector("#longitude").value = latlng.getLng();
		});
			
		//searchAddrFromCoords(map.getCenter(), displayCenterInfo);

	});
	
	
</script>
<%@ include file="/WEB-INF/view/component/footer.jspf"%>
