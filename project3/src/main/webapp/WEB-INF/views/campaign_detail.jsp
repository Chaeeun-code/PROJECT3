<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style4_detail.css">
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
              
              <div class="form-container">
                     <h2>캠페인 참여</h2>
                     <!-- 상품 등록 폼 -->
                     <form id="productForm">
                         <div>
                             <label for="name">이름</label>
                             <input type="text" id="name" name="name" required>
                         </div>
                         <div>
                             <label for="phone">전화번호</label>
                             <input type="text" id="phone" name="phone" required>
                         </div>
                         <div>
                             <label for="email">이메일 주소</label>
                             <input type="text" id="email" name="email" required>
                         </div>
                        
                         <button type="button" id="registerButton">신청하기</button>
                     </form>
                 </div>
				 
				 
				 <div class="form-container2">
				        <h2>캠페인 인증</h2>
				        <!-- 상품 등록 폼 -->
				        <form id="productForm">
							<div>
							                <label>인증 사진 첨부</label>
							               <td> <input type="file" name="uploadFile"> </td>
							          </div>

				          
				           
				            <button type="button" id="verifyButton">인증</button>
				        </form>
				    </div>
				<script>
				   // 세션 스토리지에서 role 값 가져오기
	               const username = sessionStorage.getItem("username");
	               const role = sessionStorage.getItem("role");

	               if (role === "customer") {
	                   // role이 'customer'인 경우 필요한 값들을 세션 스토리지에서 가져옴
	                   const name = sessionStorage.getItem("name");
	                   const sex = sessionStorage.getItem("sex");
	                   const tel = sessionStorage.getItem("tel");
	                   const birth = sessionStorage.getItem("birth");
	                   const email = sessionStorage.getItem("email");
	                   const address = sessionStorage.getItem("address");

	                   console.log("Customer 정보:");
	                   console.log("이름:", name);
	                   console.log("성별:", sex);
	                   console.log("전화번호:", tel);
	                   console.log("생년월일:", birth);
	                   console.log("이메일:", email);
	                   console.log("주소:", address);

	               } else if (role === "company") {
	                   // role이 'company'인 경우 필요한 값들을 세션 스토리지에서 가져옴
	                   const name = sessionStorage.getItem("name");
	                   const brn = sessionStorage.getItem("brn");  // 사업자 등록 번호
	                   const tel = sessionStorage.getItem("tel");
	                   const email = sessionStorage.getItem("email");
	                   const address = sessionStorage.getItem("address");

	                   console.log("Company 정보:");
	                   console.log("회사명:", name);
	                   console.log("사업자 등록 번호:", brn);
	                   console.log("전화번호:", tel);
	                   console.log("이메일:", email);
	                   console.log("주소:", address);

	               } else {
	                   console.log("role이 'customer' 또는 'company'가 아닙니다.");
	               }
				
				
				</script>
                 
                 <script>
                    // 페이지 로드 시 고객 정보 로드
                    $(document).ready(function() {
                        loadCustomers();
                    });

                    function loadCustomers() {
                         // 고객 정보 가져오기
                         $.ajax({
                             url: 'https://localhost:8888/api/customers/list', 
                             type: 'GET',
                             data: { name: username },
                             contentType: 'application/json',
                             success: function(customer) {
                                 document.getElementById('name').value  = customer.name;
                                 document.getElementById('phone').value = customer.tel;
                                 document.getElementById('email').value = customer.email;
                             }, 
                             error: function(xhr, status, error) {
                                 alert("고객 정보를 가져오는 데 실패했습니다: " + error); 
                             }
                         });
                    }
					
					
					$(document).ready(function() {
					    $('#verifyButton').on('click', function() {
					        console.log("AJAX 요청 시작"); // 요청 시작 확인
					        
					        const formData = new FormData();
					        formData.append('customer_id', username); 
					        
					        const fileInput = $('input[name="uploadFile"]')[0];
					        if (fileInput.files.length > 0) {
					            formData.append('uploadFile', fileInput.files[0]);
					            console.log("파일 추가됨"); // 파일 추가 확인
					        } else {
					            alert('이미지를 선택해 주세요.');
					            return;
					        }

					        $.ajax({
					            url: 'https://localhost:8388/api/campaign/accept',
					            method: 'POST',
					            processData: false,
					            contentType: false,
					            data: formData,
					            success: function(response) {
					                console.log("AJAX 요청 성공:", response); // 응답 성공 로그
					                if (response.success) {
					                    alert('캠페인 참여가 성공적으로 등록되었습니다: ' + response.message);
					                } else {
					                    alert('캠페인 참여 등록에 실패했습니다: ' + response.message);
					                }
					            },
					            error: function(xhr) {
					                console.error("AJAX 요청 실패:", xhr.responseText); // 오류 로그
					                alert('오류 발생: ' + xhr.responseText);
					            }
					        });
					    });
					});


					

                 </script>
</body>
</html>
