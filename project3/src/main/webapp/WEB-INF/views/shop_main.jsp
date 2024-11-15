<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eco Wave</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style2_main.css">
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <!-- 상단 네비게이션 -->
	<div class="navbar">
		        <div class="logo">
					<a href="${pageContext.request.contextPath}/index">
		            <img src="${pageContext.request.contextPath}/grim/logo.png" alt="logo">
					</a>
		        </div>
		        <div class="nav-links">
		            <a href="${pageContext.request.contextPath}/campaign_main">참여활동</a>
<<<<<<< HEAD
		            <a href="#">회사소개</a>
		            <a href="${pageContext.request.contextPath}/shop_main">eco 쇼핑</a>
		            <a href="#">참여방법</a>
=======
		            <a href="#">회사소소개3</a>
		            <a href="${pageContext.request.contextPath}/shop_main">eco 쇼핑</a>
		            <a href="#">참여뱅뱅법</a>
>>>>>>> origin/main
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

   <!-- 메인 배너 영역 -->
<section class="swiper-container main-banner">
    <div class="swiper-wrapper">
        <!-- 첫 번째 배너 슬라이드 -->
        <div class="swiper-slide swiper-slide-1">
            <div class="banner-text">
                <h1>제로 웨이스트</h1>
                <p class="first-line">"Join the zero waste movement with our eco-friendly products</p>
                <p class="second-line">for a sustainable future."</p>
                <a href="#" class="btn">View Detail</a>
            </div>
        </div>
        <!-- 두 번째 배너 슬라이드 -->
        <div class="swiper-slide swiper-slide-2">
            <div class="banner-text">
            </div>
        </div>
        <!-- 세 번째 배너 슬라이드 -->
        <div class="swiper-slide swiper-slide-3">
            <div class="banner-text">
                <h1>지속 가능한 미래</h1>
                <p class="first-line">"Embrace sustainability for a brighter tomorrow."</p>
                <p class="second-line">Make eco-friendly choices every day.</p>
                <a href="#" class="btn">Learn More</a>
            </div>
        </div>
    </div>

    <!-- Swiper 네비게이션 버튼 -->
    <div class="swiper-button-next"></div>
    <div class="swiper-button-prev"></div>

    <!-- Swiper 페이지네이션 -->
    <div class="swiper-pagination"></div>
</section>



 <!-- Swiper JS -->
    <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>

    <!-- Swiper 초기화 스크립트 -->
    <script>
        var swiper = new Swiper('.swiper-container', {
            loop: true, /* 무한 루프 */
            autoplay: {
                delay: 3000, /*3초마다 슬라이드 */
                disableOnInteraction: false,
            },
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
        });
    </script>

    <!-- 제품 목록 -->
    <section class="best-product">
        <h2>BEST PRODUCT</h2>
        
		 
		<div class="product-grid">	
		
	<script>

		document.addEventListener("DOMContentLoaded", function() {
		    loadProducts();
		});

		function loadProducts() {
		    $.ajax({
		        url: 'https://localhost:8588/api/products/list',
		        method: 'GET',
		        contentType: 'application/json',
		        success: function(products) {
		            const productGrid = document.querySelector('.product-grid');
		             
		            productGrid.innerHTML = '';  // 기존 내용을 초기화
console.log(products);

		            products.forEach(product => { 
						const productElement = 
						    '<div class="product">' +
								'<a href="shop_detail?thing_id=' + product.thing_id + '">'+
									'<img src="' + product.image_path + '" alt="이미지" />' +
						        '<h3>' + product.name + '</h3>' +
								'</a>'+
						        '<h4>' + product.price + "원"+'</h4>' +
						    '</div>';
		                productGrid.insertAdjacentHTML('beforeend', productElement); // HTML을 .product-grid 안에 추가
		            });
		        },
		        error: function(xhr) {
		            alert("물건 품목을 가지고 올 수 없습니다: " + xhr.responseText);
		        }
		    });
		}
	</script>
	

        </div>
    </section>
    
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
    
    
    
</body>
</html>
