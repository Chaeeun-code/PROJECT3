<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>활동 인증 관리</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style0_certification.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Schoolbell&display=swap" rel="stylesheet">
</head>

<body>
    <div class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index">
                <img src="${pageContext.request.contextPath}/grim/logo.png" alt="logo">
            </a>
        </div>
        <div class="nav-links">
            <a href="#">관리자홈</a>
            <a href="#">일반회원 관리</a>
            <a href="#">기업회원 관리</a>
            <a href="#">활동 인증 관리</a>
        </div>
        <div class="auth-buttons">
            <a href="${pageContext.request.contextPath}/manage_product">
                <button class="signup-btn">상품 등록</button>
            </a>
            <button class="login-btn">로그인</button>
        </div>
    </div>

    <div class="content">
        <h2>활동 인증 관리</h2>
        <table class="certification-table">
            <thead>
                <tr>
                    <th>인증 이미지</th>
                    <th>고객 ID</th>
                    <th>인증 날짜</th>
                    <th>작업</th>
                </tr>
            </thead>
            <tbody>
                <!-- 동적으로 생성될 행은 여기에 추가됩니다. -->
            </tbody>
        </table>
    </div>

    <div id="confirmationModal" class="modal" style="display: none;">
        <div class="modal-content">
            <h3>승인 완료</h3>
            <p>크레딧이 100만큼 추가되었습니다.</p>
            <button id="confirmButton">확인</button>
        </div>
    </div>
    
       <div class="sidebar">
        <a href="${pageContext.request.contextPath}/manage" >관리자 홈</a>
        <a href="${pageContext.request.contextPath}/manage_credit">구매 관리</a>
        <a href="${pageContext.request.contextPath}/company">상품 등록</a>
        <a href="${pageContext.request.contextPath}/manage_certification" class="active">활동 인증 관리</a>
        <a href="#">회원 관리</a>
    </div>

	<script>
	    let currentCertificationDate; // 현재 승인할 인증 날짜를 저장할 변수
	    let currentRow; // 현재 승인할 행을 저장할 변수

	    document.addEventListener('DOMContentLoaded', function() {
	        loadCampaignAccepts();

	        // 모달 확인 버튼 클릭 이벤트
	        document.getElementById("confirmButton").onclick = function() {
	            approveCertification(currentCertificationDate, currentRow); // 승인 처리
	            document.getElementById("confirmationModal").style.display = "none"; // 모달 닫기
	        };
	    });

	    function loadCampaignAccepts() {
	    	
	    	$('.sidebar a').each(function () {
	            if (this.href === window.location.href) {
	                $(this).addClass('active');
	            }
	        });

	        $('.sidebar a').click(function () {
	            $('.sidebar a').removeClass('active');
	            $(this).addClass('active');
	        });
	        
	        $.ajax({
	            url: 'http://localhost:8388/api/campaign/certification',
	            type: 'GET',
	            data: { customer_id: 'C001' },
	            contentType: 'application/json',
	            success: function(data) {
	                console.log("AJAX 호출 성공:", data); // 성공 로그
	                const tbody = document.querySelector('.certification-table tbody');
	                tbody.innerHTML = ''; // 기존 데이터를 비움
	                
	                data.forEach(function(item) {
	                    // 클라이언트에서 날짜 변환
	                    const certificationDateUTC = new Date(item.certificationDate); // UTC
	                    const options = {
	                        timeZone: 'Asia/Seoul', 
	                        year: 'numeric', 
	                        month: '2-digit', 
	                        day: '2-digit', 
	                        hour: '2-digit', 
	                        minute: '2-digit', 
	                        second: '2-digit'
	                    };
	                    const seoulDateString = certificationDateUTC.toLocaleString('ko-KR', options); // 서울 시간대로 변환
	                    
	                    const row = document.createElement('tr');
	                    
	                    // 인증 이미지 셀
	                    const imgCell = document.createElement('td');
	                    const img = document.createElement('img');
	                    img.src = item.campaignImage; // 이미지 경로 설정
	                    img.alt = "인증 이미지";
	                    img.width = 300; // 너비 설정
	                    img.height = 500; // 높이 설정
	                    imgCell.appendChild(img);
	                    
	                    // 고객 ID 셀
	                    const customerIdCell = document.createElement('td');
	                    customerIdCell.textContent = item.customerId; // 고객 ID 설정
	                    
	                    // 인증 날짜 셀 (변환된 날짜로 수정)
	                    const certificationDateCell = document.createElement('td');
	                    certificationDateCell.textContent = seoulDateString; // 변환된 인증 날짜 설정
	                    
	                    // 작업 셀
	                    const actionCell = document.createElement('td');
	                    const approveButton = document.createElement('button'); // approveButton 생성
	                    approveButton.className = "approve-btn"; // 클래스 추가
	                    approveButton.textContent = "승인"; // 버튼 텍스트 설정

	                    approveButton.onclick = function() {
	                        currentCertificationDate = item.certificationDate; // 인증 날짜 저장
	                        currentRow = row; // 행 저장
	                        showConfirmationModal(); // 모달 표시
	                    };

	                    const rejectButton = document.createElement('button');
	                    rejectButton.className = "reject-btn";
	                    rejectButton.textContent = "반려";
	                    actionCell.appendChild(approveButton); // 작업 셀에 approveButton 추가
	                    actionCell.appendChild(rejectButton); // 작업 셀에 rejectButton 추가
	                    
	                    // 행에 셀 추가
	                    row.appendChild(imgCell);
	                    row.appendChild(customerIdCell);
	                    row.appendChild(certificationDateCell);
	                    row.appendChild(actionCell);
	                    
	                    // tbody에 새로운 행 추가
	                    tbody.appendChild(row);
	                });
	            },
	            error: function(xhr, status, error) {
	                console.error("AJAX 호출 실패:", error); // 실패 로그
	                alert("데이터를 가져오는 데 실패했습니다: " + error);
	            }
	        });
	    }

	    function showConfirmationModal() {
	        document.getElementById("confirmationModal").style.display = "block"; // 모달 표시
	    }

	    function approveCertification(certificationDate, row) {
	        const customerId = row.querySelector('td:nth-child(2)').textContent; // 고객 ID 추출

	        $.ajax({
	            url: 'http://localhost:8388/api/campaign/certification',
	            type: 'POST',
	            contentType: 'application/json',
	            data: JSON.stringify({
	                customerId: customerId,
	                certificationDate: certificationDate // 서버에 인증 날짜 전달
	            }),
	            success: function(response) {
	                console.log("인증 승인 성공:", response);
	                if (response.success) {
	                    alert(response.message); // 승인 성공 메시지 표시
	                    row.remove(); // 승인된 행을 테이블에서 제거
	                }
	            },
	            error: function(xhr, status, error) {
	                console.error("인증 승인 실패:", error);
	                alert("인증 승인에 실패했습니다.");
	            }
	        });
	    }
	</script>

</body>
</html>
