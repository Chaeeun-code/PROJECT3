<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style2_buy.css"> <!-- 스타일시트 연결 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
	<script src="http://localhost:8380/js/payment.js"></script>
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
    

	<div class="top">
		<div class="top-content">
		    <h1>결제</h1>			
		</div>
		<hr>
	</div>
	
	
    <div class="container"> 
        <p>주문내역</p>		
		<div class="thing-grid">
		</div>
		

        <hr>
        <div class="order-buy">
            <p class="total"> 총금액 </p>
        </div>
        <hr>

        <div class="shipping-section">
            <h2>배송 정보</h2>
            <form>
				<label for="recipient-name">받으시는 분</label>
				<input type="text" id="recipient-name" name="recipient-name" required>

				<label for="address">주소</label>
				<input type="text" id="address" name="address" required>

				<label for="phone">휴대폰 번호</label>
				<input type="text" id="phone" name="phone" required>

                <label for="email">이메일</label>
                <input type="email" id="email" name="email">

                <label for="delivery-memo">배송 메시지</label>
                <textarea id="delivery-memo" name="delivery-memo"></textarea>
            </form>
        </div>

		
		<div class="discount-section">
		<h2>할인</h2>
		<form class="credit-buy">
		<label for="credit">사용할 크레딧</label>
		<input type="number" id="usedCredits" name="credit" value='0'>
		<p>보유 크레딧: <span id="creditDisplay"></span></p>
		<button type="button" class="useAllCredits">전액 사용</button>
		</form>
		</div>

        <div class="payment-section">
            <h2>결제 예정 금액</h2>
            <table>
                <thead>
                    <tr>
                        <th>총 주문 금액</th>
						<th>-</th>
                        <th>할인 금액</th>
						<th>=</th>
                        <th>총 결제 금액</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="tot">₩ 총 주문 금액</td>
						<th>-</th>
                        <td class="sal">0</td>
						<th>=</th>
                        <td class="fin">₩ 총 결제 금액</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="button-section">
			<button id="paymentbtn" type="submit"
			    onclick="pay(this)">
			    결제하기
			</button>
        </div>
    </div>

    <!-- 할인 정보와 상품 정보를 AJAX로 로드 -->
	<div class="product-grid">
	<script>
		
		let totalCredits;
		let product;  
		let response;
		
		// URL에서 imageid 파라미터를 추출
		function getParameterByName(name) {
		    const urlParams = new URLSearchParams(window.location.search);
		    return urlParams.get(name);
		}


		document.addEventListener("DOMContentLoaded", function() {
			const from = getParameterByName("from");
			loadProducts();
			if(from ==="product"){      
				cartthings();
			}else{
				loadthings();
			}
			const creditInput = document.getElementById('usedCredits');
			        creditInput.addEventListener('input', function() {
			            updateFinalPrice();
			        });
					
					document.querySelector('.close').addEventListener('click', function() {
					    const modal = document.getElementById('creditLimitModal');
					    modal.style.display = 'none';
					});

					window.addEventListener('click', function(event) {
					    const modal = document.getElementById('creditLimitModal');
					    if (event.target === modal) {
					        modal.style.display = 'none';
					    }
					});	   


		   });

		 // 크레딧이 10%사용되고 있는지 확인하는 로직
		 function updateFinalPrice() {
		   		const creditInput = document.getElementById('usedCredits');
		   		const totalOrderElement = document.querySelector('.tot');
		   		let totalOrderPrice = parseInt(totalOrderElement.textContent.replace(/[^0-9]/g, '')) || 0;
		   		const creditInput2 = parseInt(creditInput.value) ||0;
		   		const discountElement = document.querySelector('.sal');
		   		const finbuy = document.querySelector('.fin');
		   		
		   		if(totalOrderPrice*0.1<creditInput2){
					const modal = document.getElementById('creditLimitModal');
					modal.style.display = 'block';
					discountElement.textContent = '0'; 
					creditInput.value = '0';
		   		}else{
		   			discountElement.textContent = creditInput2.toLocaleString(); 
		   			finbuy.textContent = (totalOrderPrice-creditInput2).toLocaleString() + '원';
		   		}
		   		
		   };
		   
		// 크레딧 가지고 오는 로직
		function loadProducts() {
		    $.ajax({
		        url: 'https://localhost:8587/api/shop_buy',
		        method: 'GET',
		        contentType: 'application/json',
		        success: function(response) {
		            const creditInput = document.getElementById('usedCredits');
		            const creditDisplays = document.getElementById('creditDisplay');
					
		            document.getElementById('creditDisplay').textContent = response;

		            document.querySelector('.useAllCredits').addEventListener('click', function() {
						updateFinalPrice()
						creditInput.value = response;
						creditDisplays.textContent = response;
						updateFinalPrice()
		                
		            });
					
		        },
		        error: function(xhr) {
		            alert("크레딧을 가지고 올 수 없습니다: " + xhr.responseText);
		        }
		    });
		}
		
		//cart에서 결제로 넘어갈때 로직
		function cartthings() {
		    const thing_id = getParameterByName("thing_id");

		    console.log(thing_id);
		    if (!thing_id) {
		        alert("상품 정보가 없습니다.");
		        return;
		    }

		    $.ajax({
		        url: 'https://localhost:8588/api/products/shop_buy',
		        method: 'GET',
		        contentType: 'application/json',
		        data: {
		            thing_id: thing_id,
		            customer_id: 'C001'
		        },
		        success: function(products) {
		            const productGrid = document.querySelector('.thing-grid');
		            productGrid.innerHTML = '';
					
					let totprice = 0;

		            (Array.isArray(products) ? products : [products]).forEach(function(product, index) {
		                const pricePerItem = parseInt(product.price / product.num);  // 개당 가격을 계산
		                const productElement = 
							'<hr class="line">'+
		                    '<div class="order-section">' +
		                        '<div class="product-image">' +
		                            '<img src="' + product.image_path + '" alt="상품이미지" class="product-placeholder">' +
		                            '<p id="product-name-' + index + '">상품 이름: ' + product.name + '</p>' +
		                            '<p>상품 옵션: (ex. 색상)</p>' +
		                            '<p class="product-price" id="product-price-' + index + '">상품 가격: ' + product.price + '원</p>' +
		                        '</div>' +
		                        '<div class="quantity-count">' +
		                            '상품수량 <input type="number" class="quantity-input" id="' + product.thing_id + '" name="quantity" min="1" value="' + product.num + '">' +
		                        '</div>' +
		                    '</div>' ;

		                productGrid.insertAdjacentHTML('beforeend', productElement);
						totprice += parseInt(product.price);
						updateTotalPrice(totprice);

		                
		                const quantityInput = document.getElementById(product.thing_id);
		                const productPriceElement = document.getElementById('product-price-' + index);
		                quantityInput.addEventListener('input', function() {
		                    const updatedQuantity = parseInt(this.value) || 1;
		                    const updatedPrice = pricePerItem * updatedQuantity;
		                    productPriceElement.textContent = '상품 가격: ' + updatedPrice.toLocaleString() + '원';
							
							
							const oldPrice = pricePerItem * product.num;
							totprice = totprice - oldPrice + updatedPrice;
							product.num = updatedQuantity;
							updateTotalPrice(totprice);							
							
		                });
		            });
					
		        },
		        error: function(xhr) {
		            alert("상품 정보를 불러올 수 없습니다: " + xhr.responseText);
		        }
		    });
		}

		//detail페이지에서 결제로 넘어갈때
		function loadthings() {
		    const thing_id = getParameterByName("thing_id");
		    const quantity = parseInt(getParameterByName("quantity"));

		    console.log(thing_id);

		    // 상품 정보가 없으면 경고를 띄우고 종료
		    if (!thing_id) {
		        alert("상품 정보가 없습니다.");
		        return;
		    }

		    $.ajax({
		        url: 'https://localhost:8588/api/products/cartbuy',
		        method: 'GET',
		        contentType: 'application/json',
		        data: { thing_id: thing_id },
		        success: function(product) {
		            console.log(product);
		            const productGrid = document.querySelector('.thing-grid');
		            productGrid.innerHTML = '';

		            const pricePerItem = parseInt(product.price);
		            let totprice = pricePerItem * quantity; 

		            const productElement = 
		                '<div class="order-section">' +
		                    '<div class="product-image">' +
		                        '<img src="' + product.image_path + '" alt="상품이미지" class="product-placeholder">' +
		                        '<p id="product-name">상품 이름: ' + product.name + '</p>' +
		                        '<p>상품 옵션: (ex. 색상)</p>' +
		                        '<p id="product-price">상품 가격: ' + (pricePerItem * quantity).toLocaleString() + '원</p>' +
		                    '</div>' +
		                    '<div class="quantity-count">' +
		                        '상품수량 <input type="number" class="quantity-input" id="' + product.thing_id + '" name="quantity" min="1" value="' + quantity + '">' +
		                    '</div>' +
		                '</div>';

		            productGrid.insertAdjacentHTML('beforeend', productElement);
		            updateTotalPrice(totprice);

		            
		            const quantityInput = document.getElementById(product.thing_id);
		            const productPriceElement = document.getElementById('product-price');

		            quantityInput.addEventListener('input', function() {
		                const updatedQuantity = parseInt(this.value) || 1; 
		                const updatedPrice = pricePerItem * updatedQuantity;

		                productPriceElement.textContent = '상품 가격: ' + updatedPrice.toLocaleString() + '원';
              
		                totprice = updatedPrice;
		                updateTotalPrice(totprice);
		            });
		        },
		        error: function(xhr) {
		            alert("상품 정보를 불러올 수 없습니다: " + xhr.responseText);
		        }
		    });
		}		
	
	// 총금액 계산 로직	
	function updateTotalPrice(totprice) {
	    const totalPriceElement = document.querySelector('.total');
		const totalOrderElement = document.querySelector('.tot');
		const creditInput = document.getElementById('usedCredits');
		let creditfin = parseInt(creditInput.textContent.replace(/[^0-9]/g, '')) || 0;
		const finbuy = document.querySelector('.fin');
		
	   	totalPriceElement.textContent = '총금액: ' + totprice.toLocaleString() + '원';
		totalOrderElement.textContent = totprice.toLocaleString() + '원';
		finbuy.textContent = (totprice-creditfin).toLocaleString() + '원';
		
	}
	
	//결제 요청 팝업
	function pay() {
	    const name = document.getElementById('recipient-name').value;
	    const address = document.getElementById('address').value;
	    const tel = document.getElementById('phone').value;
	    const email = document.getElementById('email').value;

		if (!name || !address || !tel || !email) {
		        alert("배송 정보를 모두 입력해 주세요.");
		        return; 
		    }
		
	    const finElement = document.querySelector('.fin');
	    const finprice = parseInt(finElement.textContent.replace(/[^0-9]/g, ''));
	    const thing_id = getParameterByName("thing_id"); // 예: "TH123,TH124,TH125"
	    const thingIdArray = thing_id ? thing_id.split(",") : [];
	    const productQuantities = [];
		const credits = parseInt(document.getElementById('usedCredits').value) || 0;


	    for (let i = 0; i < thingIdArray.length; i++) {
	        let thingId = thingIdArray[i];
	        let quantityInput = document.getElementById(thingId);
	            let quantity = parseInt(quantityInput.value) || 0;
	            productQuantities.push({thingId: thingId, quantity: quantity });	
	    }
		const from = getParameterByName("from");

		 requestPay({
		     name: name,
			 address:address,
		     tel: tel,
			 email:email,	   
		     finprice: finprice,
			 productQuantities: productQuantities,
			 credits:credits,
			 from: from,
			 customer_id:"C001"
		 });
		
		
	}


			   
			
	    </script>	
		</div>
		
	<!--모달 팝업-->
		<div id="creditLimitModal" class="modal">
		    <div class="modal-content">
		        <span class="close">&times;</span>
		        <p>결제 금액의 10%까지 크레딧 사용이 가능합니다.</p>
		    </div>
		</div>
	
</body>
</html>
