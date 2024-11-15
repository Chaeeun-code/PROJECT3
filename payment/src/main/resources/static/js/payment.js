var IMP = window.IMP;
IMP.init("imp73285400");

function requestPay(paymentData) {
	
	
	let name = paymentData.name;
	let address = paymentData.address;
	let tel = paymentData.tel;
	let email = paymentData.email;
	let finprice = paymentData.finprice;
	let productQuantities = paymentData.productQuantities;
	let credits = paymentData.credits;
	let from = paymentData.from;
	let customer_id = paymentData.customer_id;
	   
	console.log(name);
	
	IMP.request_pay({
		pg: 'kakaopay.TC0ONETIME',
		pay_method: 'card',
		merchant_uid: customer_id + new Date().getTime(), // URL에서 가져온 상품 이름 사용	
		name: customer_id, // 아이디를 가지고 오자
		amount: finprice, // URL에서 가져온 상품 가격 사용
		buyer_name: name

	}, function(rsp) { // callback
		//rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
		if (rsp.success) {
			// POST 방식으로 성공 페이지로 리다이렉트
			alert("결제에 성공하였습니다");
<<<<<<< HEAD
			console.log(tel);
			$.ajax({
			    url: "https://localhost:8380/payment/success",  // 전체 URL 확인
=======
			$.ajax({
			    url: "http://localhost:8380/payment/success",  // 전체 URL 확인
>>>>>>> origin/main
			    type: "POST",
			    data: {
			        transaction_id: rsp.merchant_uid,
			        buyer_name: paymentData.name,
			        buyer_tel: paymentData.tel,
			        buyer_email: paymentData.email,
			        product_quantities: JSON.stringify(paymentData.productQuantities),
			        amount: paymentData.finprice,
					credits:paymentData.credits,
					from:paymentData.from,
					customer_id:paymentData.customer_id
			    },
			    success: function(response) {
			        console.log("결제 성공:", response);
<<<<<<< HEAD
			        window.location.href = "https://localhost:8443/index";  // 성공 시 메인 페이지로 이동
			    },
				error: function(xhr, status, error) {
				    console.error("결제 요청 실패:", {
				        status: xhr.status,
				        statusText: xhr.statusText,
				        responseText: xhr.responseText,
				        error: error
				    });
				}

=======
			        window.location.href = "http://localhost:8080/index";  // 성공 시 메인 페이지로 이동
			    },
			    error: function(xhr, status, error) {
			        console.error("결제 요청 실패:", xhr.responseText);
			    }
>>>>>>> origin/main
			});
			
		
			
		} else {
			// 결제에 실패한 경우
			alert("결제에 실패했습니다."+ rsp.error_msg);
		}
	});
}