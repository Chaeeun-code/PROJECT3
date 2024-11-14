<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*, java.net.*, org.json.*" %>
<%@ page import="java.util.Map, java.util.HashMap, java.util.List, java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eco Shop</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style5.css">
    <link href="https://fonts.googleapis.com/css2?family=Schoolbell&display=swap" rel="stylesheet">
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
   
    <div class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index">
                <img src="${pageContext.request.contextPath}/grim/logo.png" alt="logo" id="logo-image">
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
    
     <div class="sidebar">
        <a href="${pageContext.request.contextPath}/manage" >관리자 홈</a>
        <a href="${pageContext.request.contextPath}/manage_credit">구매 관리</a>
        <a href="${pageContext.request.contextPath}/company" class="active">상품 등록</a>
        <a href="${pageContext.request.contextPath}/manage_certification">활동 인증 관리</a>
        <a href="#">회원 관리</a>
    </div>

    <!-- 상품 등록 폼 시작 -->
   <div class="form-container">
       <h2>상품 등록</h2>
       <!-- 상품 등록 폼 -->
       <form id="productForm">
           <div>
               <label for="company_id">회사 ID</label>
               <input type="text" id="company_id" name="company_id" required>
           </div>
           <div>
               <label for="name">상품명</label>
               <input type="text" id="name" name="name" required>
           </div>
           <div>
               <label for="price">가격 (원)</label>
               <input type="number" id="price" name="price" required>
           </div>
           <div>
               <label for="num">입고 수량</label>
               <input type="number" id="num" name="num" required>
           </div>
           <div>
               <label for="explain">상품 설명</label>
               <textarea id="explain" name="explain" rows="4" required></textarea>
           </div>
           <div>
                 <labe >상품 이미지</label>
                <td> <input type="file" name="uploadFile"> </td>
           </div>
           <button type="button" id="registerButton">상품 등록</button>
       </form>
   </div>

   <script>
       $(document).ready(function() {
    	   $('.sidebar a').each(function () {
	            if (this.href === window.location.href) {
	                $(this).addClass('active');
	            }
	        });

	        $('.sidebar a').click(function () {
	            $('.sidebar a').removeClass('active');
	            $(this).addClass('active');
	        });
	        
           $('#registerButton').on('click', function() {
              
              console.log("AJAX 요청 시작"); // 요청 시작 확인용 콘솔 출력
              
               // 폼 데이터 수집
               const formData = new FormData();
               formData.append('company_id', $('#company_id').val());
               formData.append('name', $('#name').val());
               formData.append('price', $('#price').val());
               formData.append('num', $('#num').val());
               formData.append('explain', $('#explain').val());
            // 파일 입력 필드에서 파일 가져오기
               const fileInput = $('input[name="uploadFile"]')[0];
               if (fileInput.files.length > 0) {
                   formData.append('uploadFile', fileInput.files[0]); // 파일 추가
               } else {
                   alert('이미지를 선택해 주세요.');
                   return;
               }

               // Ajax 요청을 통해 서버로 데이터 전송
               $.ajax({
                   url: 'https://localhost:8588/api/things/register',  // 상품 등록 API URL
                   method: 'POST',
                   processData: false,
                   contentType: false,
                   data: formData,
                   success: function(response) {
                      
                      console.log(response);  // 응답 데이터 확인용 콘솔 출력
                      
                       if (response.success) {
                           alert('상품 등록 완료: ' + response.message);
                       } else {
                           alert('상품 등록 실패: ' + response.message);
                       }
                   },
                   error: function(xhr) {
                       alert('오류 발생: ' + xhr.responseText);
                   }
               });
           });
       });
   </script>

    <!-- 상품 등록 폼 끝 -->


</body>
</html>