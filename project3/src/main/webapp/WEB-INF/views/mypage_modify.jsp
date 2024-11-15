<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style3_modify.css">
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


    <div class="container">
        <div class="sidebar">
            <ul>
                <li><a href="${pageContext.request.contextPath}/mypage">주문 조회</a></li>
                <li><a href="#">크레딧 사용 현황</a></li>
                <li><a href="#">취소/교환/반품</a></li>
                <li><a href="#">1:1 문의</a></li>
                <li><a href="#">친환경 참여 활동</a></li>
                <li class="active"><a href="#">회원 정보 수정</a></li>
            </ul>
        </div>

        <div class="main-content">
        
           <!-- 프로필 섹션 -->
           <div class="profile-title-section">
             <table class="profile-title-table">
                 <tr>
                     <td class="profile-title-cell">프로필</td>
                 </tr>
             </table>
         </div>
           
           
            <div class="profile-section">
                <table class="profile-table">
                    <tr>
                        <td rowspan="2" class="profile-image-cell">
                            <img src="https://ipfs.io/ipfs/QmaQ2t3bJJKXYq8uuCy4LpyQnw2DPiX2oaDn2ZxgtdcacA" alt="Profile Image" class="profile-image">
                        </td>
                        <td class="profile-email" id="profileEmail"></td>
                    </tr>
                    <tr>
                        <td class="profile-name" id="profileName"></td>
                    </tr>
                </table>
            </div>
        
       <div class="main-section">
            <h2>회원 정보 수정</h2>
            
                <table class="info-edit-table">
               <input type="hidden" id="customer_id" value="">
                    <tr>
                        <td>이름</td>
                        <td>
                            <input type="text" id="name" class="input-name" placeholder="변경할 이름">
                            <button type="submit" class="btn1"">변경</button>
                        </td>
                    </tr>
                    <tr>
                        <td>이메일</td>
                        <td>
                            <input type="email" id="email" class="input-email" placeholder="변경할 이메일 주소">
                            <button type="submit" class="btn2">변경</button>
                        </td>
                    </tr>
                    <tr>
                        <td>휴대폰 번호</td>
                        <td>
                            <input type="text" id="phone" class="input-phone" placeholder="변경할 휴대폰 번호 (010-xxxx-xxxx)">
                            <button type="submit" class="btn3">변경</button>
                        </td>
                    </tr>
                    <tr>
                        <td>비밀번호 변경</td>
                        <td>
                            <input type="password" id="current_password" class="current_password" placeholder="현재 비밀번호"><br>
                            <input type="password" id="new_password" class="new_password" placeholder="새 비밀번호"><br>
                            <input type="password" id="confirm_password" class="confirm_password" placeholder="새 비밀번호 확인"><br>
                            <button class="btn4" type="submit">변경</button>
                        </td>
                    </tr>
                </table>
        </div>
    </div>
   <div id="passwordModal" class="modal">
           <div class="modal-content">
                <div class="modal-left">
            <img src="https://ipfs.io/ipfs/QmdygQJCXNnx5c5zMzVkegneDq9AU1KSxTAF4ybmsUFfeV" alt="Image" class="modal-image" id="modalImage">
        </div>
        <div class="modal-right">
            <h3>비밀번호 확인</h3>
            <input type="password" id="modal_password" placeholder="비밀번호를 입력하세요">
            <button id="password_confirm_btn" class="small-btn" onclick="checkPassword()">확인</button>
        </div>
    </div>
</div>
       
       
   <div class="customer-grid">
       <script>
       
       const modal = document.getElementById('passwordModal');
       
       document.addEventListener("DOMContentLoaded", function() {
          console.log("DOMContentLoaded event fired");
          // 페이지 로드 시 모달 열기
           
          if (modal){
             modal.style.display = 'block';    
             console.log("모달 오픈 성공")
          }else {
             console.log("모달 요소 찾지 모함")
          }
           

           // 비밀번호 확인 버튼 클릭 이벤트
           document.getElementById('password_confirm_btn').addEventListener('click', function() {
               const inputPassword = document.getElementById('modal_password').value;

               if (!inputPassword) {
                   alert("비밀번호를 입력하세요.");
                   return;
               }

               // 서버로 비밀번호 확인 요청
               $.ajax({
                   url: 'https://localhost:8888/api/customers/verifyPassword', 
                   type: 'POST',
                   contentType: 'application/json',
                   data: JSON.stringify({modal_password : inputPassword }),
                   success: function(response) {
                       if (response.valid) {                          
                           const imageElement = document.getElementById("modalImage");
                           imageElement.src = "https://ipfs.io/ipfs/QmYJuEurvbdsba43VbxDjbVAa3q2zTPwJeWYMU6L86SwW5"; // 새로운 이미지 경로

                           console.log("비밀번호가 올바릅니다. 모달을 닫습니다.");
                           
                           setTimeout(function() {
                               modal.style.display = 'none';
                               loadCustomers();
                           }, 500); // 1000ms = 1초
                       } else {
                           alert("비밀번호가 올바르지 않습니다.");
                       }
                   },
                   error: function(xhr, status, error) {
                       if (xhr.status === 401) {
                            const errorResponse = JSON.parse(xhr.responseText);
                            alert(errorResponse.message); 
                        } else {
                            alert("비밀번호 확인에 실패했습니다: " + error);
                        }
                        
                    }
               });
           });
       });


   function loadCustomers() {

       // 고객 정보 가져오기
       $.ajax({
       url: 'https://localhost:8888/api/customers/list', 
       type: 'GET',
<<<<<<< HEAD
       data: { name: username },
=======
       data: { name: 'C001' },
>>>>>>> origin/main
       contentType: 'application/json',
       success: function(customer) {
           document.getElementById('name').value  = customer.name;
           document.getElementById('email').value = customer.email;
           document.getElementById('phone').value = customer.tel;  
           document.getElementById('profileName').textContent  = customer.name;
           document.getElementById('profileEmail').textContent  = customer.email;
       }, 
       error: function(xhr, status, error) {
           alert("고객 정보를 가져오는 데 실패했습니다: " + error); 
       }
   });
   }

   document.querySelector('.btn1').addEventListener('click', function() {
       const newName = document.getElementById('name').value;
       const customerId = $('#customer_id').val(); 

    
       if (!newName) {
           alert("이름을 입력하세요.");
           return;
       }

       // 서버로 이름 변경 요청
       $.ajax({
           url: 'https://localhost:8888/api/customers/updateInfo', 
           type: 'PUT',
           contentType: 'application/json',
<<<<<<< HEAD
           data: JSON.stringify({customer_id: username, name: newName}),
=======
           data: JSON.stringify({customer_id: customerId, name: newName}),
>>>>>>> origin/main
           success: function(response) {
               alert("이름이 성공적으로 변경되었습니다.");
           },
           error: function(xhr, status, error) {
               alert("이름 변경에 실패했습니다: " + error); 
           }
       });
   });
   

   //이메일 변경 버튼 클릭 이벤트 update-email
   document.querySelector('.btn2').addEventListener('click', function() {
       const newEmail = document.getElementById('email').value;
       const customerId = $('#customer_id').val(); 
    
       if (!newEmail) {
           alert("이메일을 입력하세요.");
           return;
       }

       // 서버로 이메일 변경 요청
       $.ajax({
           url: 'https://localhost:8888/api/customers/updateInfo', 
           type: 'PUT',
           contentType: 'application/json',
           data: JSON.stringify({customer_id: customerId, email : newEmail}),
           success: function(response) {
               alert("이메일 정보가 성공적으로 변경되었습니다.");
           },
           error: function(xhr, status, error) {
               alert("이메일 정보 변경에 실패했습니다: " + error); 
           }
       });
   });


   //전화번호 변경 버튼 클릭 이벤트
   document.querySelector('.btn3').addEventListener('click', function() {
      const newPhone = document.getElementById('phone').value;
       const customerId = $('#customer_id').val(); 
    
       if (!newPhone) {
           alert("전화번호를 입력하세요.");
           return;
       }

       // 서버로 전화번호 변경 요청
       $.ajax({
           url: 'https://localhost:8888/api/customers/updateInfo', 
           type: 'PUT',
           contentType: 'application/json',
           data: JSON.stringify({customer_id: customerId, tel : newPhone}),
           success: function(response) {
               alert("전화번호 정보가 성공적으로 변경되었습니다.");
           },
           error: function(xhr, status, error) {
               alert("전화번호 정보 변경에 실패했습니다: " + error); 
           }
       });
   });


   document.querySelector('.btn4').addEventListener('click', function() {
      const currentPassword = document.getElementById('current_password').value;
      const newPassword = document.getElementById('new_password').value;
      const confirmPassword = document.getElementById('confirm_password').value;
      const customerId = $('#customer_id').val();
      
      if(!currentPassword || !newPassword || !confirmPassword) {
         alert("비밀번호의 모든 필드를 입력하세요");
         return;
      }
      
      if (newPassword !== confirmPassword){
         alert("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
         return;
      }
      
      $.ajax({
         url: 'https://localhost:8888/api/customers/updatePassword',
         type : 'PUT',
         contentType : 'application/json',
         data : JSON.stringify({
            customer_id : customerId,
            current_password : currentPassword,
            new_password : newPassword
         }),
         success : function(resposne){
            alert("비밀번호가 성공적으로 변경되었습니다.");
         },
         error: function(xhr, status, error){
            alert("비밀번호 변경에 실패했습니다. :" + error);
         }
      });
      
   });
   </script>
   
   
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
    
   
   
    </div>
    
</body>
</html>
