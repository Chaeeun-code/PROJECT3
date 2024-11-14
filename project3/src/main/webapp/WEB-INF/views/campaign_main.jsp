<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Campaign</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style4_main.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <header>
            <div class="navbar">
                 <div class="logo">
                  <a href="${pageContext.request.contextPath}/index">
                   <img src="${pageContext.request.contextPath}/grim/logo.png" alt="logo">
                  </a>
            	 </div>
                       <div class="nav-links">
                           <a href="${pageContext.request.contextPath}/campaign_main">참여활동</a>
                           <a href="#">회사소개</a>
                           <a href="${pageContext.request.contextPath}/shop_main">eco 쇼핑</a>
                           <a href="#">참여방법</a>
                       </div>
	                   <div class="auth-buttons">
						 <a href="#">
			             <button class="signup-btn">sign up</button>
						 </a>
						 <a href="#">
			             <button class="login-btn">log in</button>
						 </a>
			           </div>
              </div>
          </header>
          
        <div class="banner">
           <img src = "" alt="배너 이미지">
        </div>   
          
          <!-- 메인 콘텐츠 -->
        <main class="main-content">
            <!-- 참여 방법 섹션 -->
            <section class="participation-method">
                <h2>참여방법</h2>
                <div class="steps-container">
                  <div class="step">
                     <div class="circle"></div>
                     <p class="step-title">step 1</p>
                     <span class="step-description">참여를 원하는 캠페인 선택</span>
                 </div>
                 <div class="step">
                     <div class="circle"></div>
                     <p class="step-title">step 2</p>
                     <span class="step-description">해당 캠페인 공식 웹사이트에서<br>활동 목적 및 내용을 확인</span>
                 </div>
                 <div class="step">
                     <div class="circle"></div>
                     <p class="step-title">step 3</p>
                     <span class="step-description">신청서 작성 후<br>친환경 캠페인에 참여</span>
                 </div>
             </div>
         </section>

            <!-- 캠페인 활동 포스터 섹션 -->
            <section class="campaign-poster">
            <div class = "poster-grid">
                       <script>
         
                     document.addEventListener("DOMContentLoaded", function() {
                         loadPosters();
                     });
               
                     function loadPosters() {
                         $.ajax({
                             url: 'https://localhost:8388/api/posters/list',
                             method: 'GET',
                             contentType: 'application/json',
                             success: function(posters) {
                                 const posterGrid = document.querySelector('.poster-grid');
                                  
                                 posterGrid.innerHTML = '';  // 기존 내용을 초기화
                                 
                                 posters.forEach(poster => {
                                     if (!poster || !poster.cimage) {
                                         console.warn("잘못된 데이터:", poster); // 잘못된 데이터를 콘솔에 경고 출력
                                         return; // cimage가 없는 경우 건너뜁니다
                                     }
               

                                 const posterElement = 
                                     '<div class="poster">' +
                                       '<a href="campaign_detail?cimageid=' + poster.cimage + '">'+
                                          '<img src="' + poster.cimage + '" alt="이미지" />' +                                        
                                       '</a>'+
                                     '</div>';
                                     posterGrid.insertAdjacentHTML('beforeend', posterElement); // HTML을 .poster-grid 안에 추가
                                 });
                             },
                             error: function(xhr) {                             
                                 alert("포스터를 가지고 올 수 없습니다: " + xhr.responseText);
                             }
                         });
                     }

                  </script>                                                   
                 </div>
            </section>
        </main>
    </div>
    
        <!-- nav bar 변경 -->
     <script>
	    document.addEventListener("DOMContentLoaded", function() {
	        const authButtons = document.querySelector('.auth-buttons');
	        const accessToken = localStorage.getItem("accessToken") || sessionStorage.getItem("accessToken");
	        const savedUsername = localStorage.getItem("savedUsername") || sessionStorage.getItem("username");
	        console.log("저장된 사용자 이름:", savedUsername);
	        if (savedUsername) {
	        	console.log("로그인 상태입니다.");
	            // 로그인 상태일 경우: sign up 및 log in 버튼 숨기고 장바구니와 사용자 아이디 표시
	            authButtons.innerHTML =
	                '<a href="${pageContext.request.contextPath}/shop_cart">' +
	                    '<button class="cart-btn">장바구니</button>' +
	                '</a>' +
	                '<a href="${pageContext.request.contextPath}/mypage">' +
	                '<button class="cart-btn">' + savedUsername + '님</button>' +
	            	'</a>';               
	        }else {
	            // 비로그인 상태일 경우 아무것도 하지 않음 (기본 상태 유지)
	            console.log("비로그인 상태입니다.");
	        }
	    });
	</script>
    

    <script src="script.js"></script>
          
          

</body>
</html>