<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf"%>
<div class="container mt-2">
    	<h4>내 물건팔기</h4>
    	<div class="border-top">
	    	<form action="${contextPath }/product/register" method="post" enctype="multipart/form-data" 
																		onsubmit="syncFileState();">	
	    		<div class="my-3">
	    			<input type="file" id="image" style="display: none" multiple="multiple" name="images">
	    			<div class="d-flex">
		    			<button type="button" class="btn btn-dark" onclick="document.querySelector('#image').click();"
		    					style="--bs-btn-padding-y: 1.75rem; --bs-btn-padding-x: 2.2rem; --bs-btn-font-size: 1.5rem;">
		    				<i class="bi bi-camera"></i>
		    			</button>
		    			<div class="d-flex" style="overflow-x: auto;" id="imageView">
			    			<!-- <div class="mx-1 rounded-4"
			    				style="overflow: hidden; min-width: 100px">
			    				<img src="/market/resource/icon/kakao_login.png" class="object-fit-cover"
			    					style="width: 100px; height: 100px">
			    			</div> -->
	    				</div>
	    			</div>
	    		</div>
	    		<div class="my-3">
		    		  <h6 style="font-weight: bold">제목</h6>
					  <input type="text" class="form-control" id="title" name="title" >
				</div>
				<div class="my-3">
	    		  <h6 style="font-weight: bold">거래방식</h6>
	    		  	<div>
					    <input type="radio" class="btn-check" name="type" id="option1" value="sell" autocomplete="off" checked>
						<label class="btn btn-outline-dark" for="option1">판매하기</label>
						
						<input type="radio" class="btn-check" name="type" id="option2" autocomplete="off" value="free">
						<label class="btn btn-outline-dark" for="option2">나눔하기</label>
					</div>
				</div>
				
				<div class="input-group my-2">
				  <span class="input-group-text">₩</span>
				  <input id="price" name="price" type="number" class="form-control"  step="1000" placeholder="가격을 입력해주세요 (천원단위)" min="1000"/>
				</div>
				
				<div class="my-3">
		    		<h6 style="fot-weight: bold">자세한 설명</h6>
					<textarea name="description" class="form-control" id="exampleFormControlTextarea1" rows="6" style="resize: none" placeholder="신뢰할 수 있는 거래를 위해 자세히 적어주세요."></textarea>
					<button class="btn btn-sm btn-secondary mt-2" type="button">자주쓰는문구</button>
				</div>
				<div>
					 <button class="form-control btn btn-dark" type="submit">작성 완료</button>	
				</div>
	    	</form>
    	</div>
    </div>
    <script>
    	
    	const fileState = [];
    	
    	// 사진 미리보기
	    document.querySelector("#image").onchange = function(e) {
			const files = [...document.querySelector("#image").files]
			
			for(let f of files){
				fileState.push(f);
			}
			
			console.log(fileState);	   
			files.forEach(function(file) {
				const fileReader = new FileReader();
				fileReader.readAsDataURL(file);
				fileReader.onload = function(e) {
					const div = document.createElement("div");
					div.className = "mx-1 rounded-4";
					div.style.overflow = "hidden";
					div.style.minWidth = "128px";
					
					const img = document.createElement("img");
					img.src = e.target.result;
					img.width = 118;
					img.height = 128;
					img.className = "object-fit-cover";
					div.appendChild(img);
					
					document.querySelector("#imageView").appendChild(div);
					
					div.ondblclick = function(e) {
					document.querySelector("#imageView").removeChild(this);
					
					//const button = document.creatElement("button");
					//button.type ="button";
					//button.className = "btn-close";
					//button.aria-label = "Close";
					//div.appendChild(img);
					
					//document.querySelector("#imageView").appendChild(div);
					
					}
				}
				
			});																																								
 				
	    }
	    document.querySelector("#option2").onchange = function(e){
	    	//console.log(e.target.value);
	    	if(e.target.value == 'free' ){
	    		document.querySelector("#price").disabled = true;
	    		document.querySelector("#price").placeholder = '0';
	    	}
	    	
	    };
	    document.querySelector("#option1").onchange = function(e){
	    	//console.log(e.target.value);
	    	if(e.target.value == 'sell' ){
	    		document.querySelector("#price").disabled = false;
	    		document.querySelector("#price").placeholder = '가격을 입력해주세요 (천원단위)';
	    		console.log(document.querySelector("#price"));
	    		
	    	}
	    	
	    };
	    	    
		function syncFileState(){
			fileState = document.querySelector("#image").files;
			//document.querySelector('#image').files = fileState;
		}
	    

    </script>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>
 