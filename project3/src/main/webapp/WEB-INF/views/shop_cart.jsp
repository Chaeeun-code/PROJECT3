<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>장바구니</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style2_cart.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <!-- 헤더 영역 -->
    <header>
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
            <div class="header-right">
                <div class="user-menu">
                    <a href="${pageContext.request.contextPath}/mypage">마이페이지</a>
                    <a href="#">id명</a>
                </div>
                <div class="cart-icon">
                    <a href="#"><img src="${pageContext.request.contextPath}/images/cart_icon.png" alt="장바구니"></a>
                </div>
            </div>
        </div>
    </header>

    <!-- 장바구니 페이지 본문 -->
    
		<div class="me">
			<div class="me-content">
        		<h1>장바구니</h1>
				<input type="checkbox" id="selectAll" checked>전체 선택</button>
				<button id="delete-select">선택삭제</button>		
				<hr>
			</div>
		</div>
		<div class="cart-container">
        <div class="cart-items" id="cart-items-container"></div> 
		<button id="buy">결제 하기</button>
	</div>
	
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            loadcart();
			document.getElementById("delete-select").addEventListener("click", deleteSelects);
			
			const selectAllCheckbox = document.getElementById("selectAll");
			
			selectAllCheckbox.addEventListener("change", function() {
				const itemCheckboxes = document.querySelectorAll(".item-checkbox");
			            itemCheckboxes.forEach(checkbox => {
			                checkbox.checked = selectAllCheckbox.checked;
			            });
			        });

					document.addEventListener("change", function(event) {
					        if (event.target.classList.contains("item-checkbox")) {
					            const itemCheckboxes = document.querySelectorAll(".item-checkbox");
					            selectAllCheckbox.checked = [...itemCheckboxes].every(cb => cb.checked);
					        }
					    });
			
        });

		//cart테이블 불러오기
        function loadcart() {
            $.ajax({
                url: 'https://localhost:8588/api/products/cartlist',
                method: 'GET',
                contentType: 'application/json',
                data: { customer_id: 'C001' },
                success: function(products) {
                    const cartItemsContainer = document.getElementById('cart-items-container');
                    cartItemsContainer.innerHTML = '';
                    
					if (!products || products.length === 0) {
						alert("장바구니에 물건이 없습니다");
					    return;
					}
					
                    products.forEach((product) => {
                        const productElement =
						'<div class="cart-item" data-thing-id="' + product.thing_id + '">' +
						    '<input type="checkbox" class="item-checkbox" data-thing-id="' + product.thing_id + ' "checked>' +
						    '<div class="item-info">' +
						        '<img src="' + product.image_path + '" alt="product" class="item-image">' +
						        '<div class="item-details">' +
						            '<p class="item-name">' + product.name + '</p>' +
						            '<p class="item-price"> 가격 : ' + product.price + '원 </p>' +
						        '</div>' +
						        '<div class="item-quantity">' +
						            '<button class="decrease-btn">-</button>' +
						            '<span class="quantity">' + product.num + '</span>' +
						            '<button class="increase-btn">+</button>' +
						            '<button class="delete-btn">삭제</button>' +
						        '</div>' +
						    '</div>' +
						'</div>';
						
					
					cartItemsContainer.insertAdjacentHTML('beforeend', productElement);
						
                        
                    });
                },
                error: function(xhr) {
                    alert("장바구니에 물건이 없습니다");
                }
            });
        }

		// 수량 감소 및 증가 버튼 이벤트
		$('#cart-items-container').on('click', '.decrease-btn', function() {			
		        updateCartQuantity.call(this, 1);
		});

		$('#cart-items-container').on('click', '.increase-btn', function() {			
		        updateCartQuantity.call(this, 2);
		});

		$('#cart-items-container').on('click', '.delete-btn', function() {			
				deletecart.call(this);
		});
		

        // 수량 업데이트 함수
        function updateCartQuantity(btnid) {		
			const itemElement = $(this).closest('.cart-item');
			let currentQuantity = parseInt(itemElement.find('.quantity').text());
			let pricePerItem = parseInt(itemElement.find('.item-price').text());
			const thing_id = itemElement.data('thing-id');
			
			
            $.ajax({
                url: 'https://localhost:8588/api/products/cartnum',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ customer_id: 'C001', thing_id: thing_id, num: currentQuantity, price:pricePerItem, bid:btnid}),
                success: function(response) {
					loadcart();
                },
                error: function(xhr) {
                    alert("수량 업데이트에 실패했습니다.");
                }
            });
			
			
        }

		//cart삭제
		function deletecart() {		
			const itemElement = $(this).closest('.cart-item');
			const thing_id = itemElement.data('thing-id');
			console.log("들어오긴함");
			console.log(thing_id);
		    $.ajax({
		           url: 'https://localhost:8588/api/products/deletecart',
		           method: 'POST',
		           contentType: 'application/json',
		           data: JSON.stringify({ customer_id: 'C001', thing_id: thing_id}),
		           success: function(response) {
						loadcart();
		           },
		           error: function(xhr) {
		                alert("상품을 삭제하지 못했습니다");
		                }
		            });
					
					
		        }

				// 체크된 항목 삭제 함수
				const deleteSelects = () => {
				    const selectedItems = document.querySelectorAll('.item-checkbox:checked');
				    const thingId = Array.from(selectedItems).map(item => item.getAttribute('data-thing-id'));
				    if (thingId.length === 0) {
				        alert("삭제할 항목을 선택하세요.");
				        return;
				    }

				    $.ajax({
				        url: 'https://localhost:8588/api/products/deletecarts',
				        method: 'POST',
				        contentType: 'application/json',
				        data: JSON.stringify({ customer_id: 'C001', thing_ids: thingId}),  // thingIds 배열을 JSON으로 전송
				        success: (response) => {
				            alert("선택한 항목이 삭제되었습니다.");
								loadcart();
				        },
				        error: (xhr, status, error) => {
				            console.error("삭제 요청 실패:", error);
				            alert("삭제에 실패했습니다.");
				        }
				    });
				};

				document.getElementById("buy").addEventListener("click", function() {
				    const selectedItems = document.querySelectorAll('.item-checkbox:checked');
				    const thingIds = Array.from(selectedItems).map(item => item.getAttribute('data-thing-id').trim());

				    if (thingIds.length === 0) {
				        alert("구매할 항목을 선택하세요.");
				        return;
				    }

				    // 선택된 thing_id들을 URL 파라미터로 추가
				    const url = 'shop_buy?thing_id='+thingIds.join(',')+'&from=product';
				    window.location.href = url;
				});

			
		
    </script>
</body>
</html>
