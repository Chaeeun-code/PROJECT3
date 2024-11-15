<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>제품 상세 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style2_detail.css"> <!-- 스타일시트 링크 -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index">
                <img src="${pageContext.request.contextPath}/grim/logo.png" alt="logo">
            </a>
        </div>
        <div class="nav-links">
            <a href="#">참여활동</a>
            <a href="#">회사소개</a>
            <a href="${pageContext.request.contextPath}/shop_main">eco 쇼핑</a>
            <a href="#">참여방법</a>
        </div>
        <div class="auth-buttons">
            <a href="#"><button class="signup-btn">sign up</button></a>
            <a href="#"><button class="login-btn">log in</button></a>
        </div>
    </div>
	
    <!-- 상품 상세 정보 영역 -->
	<div class="container">
		<img id="product-image" alt="상품 이미지" width="400" height="400">

    <div class="product-info">
        <h1 id="product-name">제품 이름</h1>     
        <p class="price" id="product-price"></p>
		<hr class="line">
        <p class="info" id="product-info">상품 설명</p>

        <!-- 수량 선택 -->
        <div class="quantity-count">
            <label for="quantity" id="count">수량</label>
            <input type="number" id="quantity" name="quantity" min="1" value="1">
        </div>

        <div class="total-price">
            <p>총 상품금액: <span id="total">원</span></p>
        </div>

        <div class="button-group">
            <button class="btn-cart">장바구니</button>
            <button class="btn-buy" id="buyButton" onclick="location.href='${pageContext.request.contextPath}/shop_buy?thing_id=${thing_id}'">구매하기</button>

        </div>

    </div>
	</div>

    <div class="footer">
        <button class="footer-view">상세정보</button>
        <button class="footer-re">Review</button>
        <button class="footer-qna">QnA</button> 
        <hr class="line">
    </div>
	
	<!-- 모달 창 HTML -->
	<div class="modal" style="display: none;">
		<div class="modal_popup">
			<h3>장바구니에 담겼습니다</h3>
			<div class="button-group">
				<button type="button" class="gocart" onclick="location.href='shop_cart'">장바구니로 가기</button>
				<button type="button" class="close_btn">쇼핑 계속하기</button>
			</div>
		</div>
	</div>	
	
    <script>
        // 페이지가 로드되면 상품 정보를 불러옵니다.
        document.addEventListener("DOMContentLoaded", function() {
            loadProductDetail();
        });

        // URL에서 imageid 파라미터를 추출하는 함수
        function getParameterByName(name) {
            const urlParams = new URLSearchParams(window.location.search);
            return urlParams.get(name);
        }
		
		const thing_id = getParameterByName("thing_id");
		
		document.getElementById("buyButton").addEventListener("click", function() {
			const quantity = document.getElementById("quantity").value;
		    if (thing_id) {
		        window.location.href = '/shop_buy?thing_id='+thing_id+'&quantity=' + quantity;;
		    } else {
		        alert("상품 정보가 없습니다.");
		    }
		});

        // 상품 상세 정보를 불러오는 함수
        function loadProductDetail() {
            const thing_id = getParameterByName("thing_id");
			
            // imageid가 없으면 경고를 띄우고 종료
            if (!thing_id) {
                alert("상품 정보가 없습니다.");
                return;
            }

            // 상품 정보를 서버에서 불러옴
            $.ajax({
                url: 'https://localhost:8588/api/products/detail?thing_id=' + thing_id,
                method: 'GET',
                contentType: 'application/json',
                success: function(product) {
                    // 상품 정보를 화면에 표시
                    document.getElementById('product-image').src = product.image_path;
                    document.getElementById('product-name').textContent = product.name;
                    document.getElementById('product-price').textContent = '가격: ' + product.price + '원';
                    document.getElementById('product-info').textContent = product.explain;
                    document.getElementById('total').textContent = product.price + '원';

                    // 수량에 따른 총 금액 계산
                    const quantityInput = document.getElementById('quantity');
                    const totalPrice = document.getElementById('total');
                    const pricePerItem = parseFloat(product.price); // 개당 가격

                    quantityInput.addEventListener('input', function() {
                        const quantity = parseInt(this.value);
                        totalPrice.textContent = (quantity * pricePerItem).toLocaleString() + '원';
                    });
                },
                error: function(xhr) {
                    alert("상품 정보를 불러올 수 없습니다: " + xhr.responseText);
                }
            });
        }

		// 모달 열기
		document.querySelector(".btn-cart").addEventListener("click", function() {
			
			const productId = getParameterByName("thing_id"); // 상품 ID 가져오기
			const priceText = document.getElementById('total').textContent;
			const price = parseInt(priceText.replace(/[^0-9]/g, ''));			
			const count = document.getElementById('quantity').value;
			const name = document.getElementById('product-name').textContent; // 상품 이름 가져오기
			const image_path = document.getElementById("product-image").src

			
			console.log(count);
			
			$.ajax({
				url: 'https://localhost:8588/api/products/cart',
				method: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({customer_id:'C001' ,thing_id: productId, price:price, num:count, name:name,
			image_path: image_path}),
				success: function() {
					document.querySelector(".modal").style.display = "block";
				},
				error: function(xhr, status, error) {
					console.error("Error details:", xhr.responseText); // 에러 상세 로그
					alert("장바구니에 담는데 실패 했습니다"); // 에러 메시지
				}
			});
		});

		// 모달 닫기
		document.querySelector(".close_btn").addEventListener("click", function() {
			document.querySelector(".modal").style.display = "none";
		});
		
		

    </script>
</body>
</html>
