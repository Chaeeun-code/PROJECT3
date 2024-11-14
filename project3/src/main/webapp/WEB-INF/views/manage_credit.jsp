<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>구매 관리</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style0_credit.css">
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
            <a href="#">일반회원<br>관리</a>
            <a href="${pageContext.request.contextPath}/shop_main">기업회원<br>관리</a>
            <a href="#">활동인증관리</a>
        </div>
        <div class="auth-buttons">
            <a href="${pageContext.request.contextPath}/manage_product">
                <button class="signup-btn">상품 등록</button>
            </a>
            <button class="login-btn">log in</button>
        </div>
    </div>
    
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/manage">관리자 홈</a>
        <a href="${pageContext.request.contextPath}/manage_credit" class="active">구매 관리</a>
        <a href="${pageContext.request.contextPath}/company">상품 등록</a>
        <a href="${pageContext.request.contextPath}/manage_certification">활동 인증 관리</a>
        <a href="#">회원 관리</a>
    </div>
    
    
    <div class="title">
    

    <h1>구매내역</h1>
    <br>
    </div>
    
   
    <!-- 검색 필터 -->
    <div class="search-filter">
        <input type="date" id="startDate">
        <span>to </span>
        <input type="date" id="endDate">
        <input type="text" id="userId" placeholder="아이디">
        <button class="search-btn" onclick="searchTransactions()">검색</button>
    </div>

    <!-- 크레딧 거래내역 테이블 -->
    <table class="credit-table">
        <thead>
            <tr>
                <th>이름</th>
                <th>아이디</th>
                <th>구매 내역</th>
                <th>사용 크레딧</th>
                <th>취소</th>
            </tr>
        </thead>
        <tbody id="transactionTableBody">
            <!-- 거래 내역이 동적으로 추가될 부분 -->
        </tbody>
    </table>

    <!-- 페이지네이션 버튼 -->
    <div class="pagination" id="pagination"></div>

	<div class="title2">
    <hr>
    <h1>취소내역</h1>
    <br>
    </div>
    <!-- 검색 필터 -->
    <div class="search-filter1">
        <input type="date" id="startDate2">
        <span>to </span>
        <input type="date" id="endDate2">
        <input type="text" id="userId2" placeholder="아이디">
        <button class="search-btn" onclick="searchCancelTransactions()">검색</button>
    </div>

    <!-- 취소내역 테이블 -->
    <table class="cancel-table">
        <thead>
            <tr class="table-header">
                <th>이름</th>
                <th>아이디</th>
                <th>구매 내역</th>
                <th>사용 크레딧</th>
                <th>취소</th>
                
                
                
                
                
            </tr>
        </thead>
        <tbody id="cancelTableBody">
            <!-- 취소 내역이 동적으로 추가될 부분 -->
        </tbody>
    </table>

	<div id="confirmModal" class="modal">
	    <div class="modal-content">
	        <p>진짜 취소 하겠습니까?</p> 
	        <button id="cancelBtn">취소</button>
			<button id="confirmBtn">확인</button>
	    </div>
	</div>
	
    <!-- 페이지네이션 버튼 -->
    <div class="pagination" id="cancelPagination"></div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
		
		const confirmModal = document.getElementById('confirmModal');
		        let currentCustomerId, currentUsedCredits, currentTransactionDate;
				
		function showModal(customerId, usedCredits, transactionDate, isCancel) {
				currentCustomerId = customerId;
				currentUsedCredits = usedCredits;
				currentTransactionDate = transactionDate;
				confirmModal.style.display = 'block';
				
		}
				
		
        document.addEventListener("DOMContentLoaded", function() {
            const today = new Date();
            const formattedToday = today.toISOString().split('T')[0];
            document.getElementById('endDate').value = formattedToday;
            document.getElementById('endDate2').value = formattedToday;

            today.setMonth(today.getMonth() - 1);
            const formattedOneMonthAgo = today.toISOString().split('T')[0];
            document.getElementById('startDate').value = formattedOneMonthAgo;
            document.getElementById('startDate2').value = formattedOneMonthAgo;
        });

        const itemsPerPage = 5;
        let currentPage = 1;
        let cancelPage = 1;
        let transactionsData = [];
        let cancelTransactionsData = [];

        $(document).ready(function() {
            loadAllTransactions();
            loadAllCancelTransactions();
        });

        function loadAllTransactions() {
            $.ajax({
                url: "https://localhost:8587/api/transactions",
                method: "GET",
                success: function(data) {
					console.log("결제내역 전체 불러옴");
                    transactionsData = Array.isArray(data) ? data : [data];
					console.log(transactionsData);
                    renderPagination();
                    renderTransactions();
                },
                error: function(xhr, status, error) {
                    console.error("거래 내역을 불러오는 중 오류 발생:", error);
                }
            });
        }

        function loadAllCancelTransactions() {
			console.log("전체 불러올수 있냐")
            $.ajax({
                url: "https://localhost:8587/api/cancel_transactiondate",
                method: "GET",
                success: function(data) {
					console.log("취소내역 전체 불러옴");
                    cancelTransactionsData = Array.isArray(data) ? data : [data];
					console.log(cancelTransactionsData);
                    renderCancelPagination();
                    renderCancelTransactions();
                },
                error: function(xhr, status, error) {
                    console.error("취소 내역을 불러오는 중 오류 발생:", error);
                }
            });
        }

        function searchTransactions() {
            const startDate = $('#startDate').val();
            let endDate = $('#endDate').val();
            if (endDate) {
                let endDateObj = new Date(endDate);
                endDateObj.setDate(endDateObj.getDate() + 1);
                endDate = endDateObj.toISOString().split('T')[0];
            }
            const userId = $('#userId').val();

            $.ajax({
                url: "https://localhost:8587/api/transactions_search",
                method: "GET",
                data: { startDate, endDate, userId },
                success: function(data) {
                    transactionsData = Array.isArray(data) ? data : [data];
                    currentPage = 1;
                    renderPagination();
                    renderTransactions();
                },
                error: function(xhr, status, error) {
                    console.error("거래 내역을 불러오는 중 오류 발생:", error);
                }
            });
        }

        function searchCancelTransactions() {
            const startDate = $('#startDate2').val();
            let endDate = $('#endDate2').val();
            if (endDate) {
                let endDateObj = new Date(endDate);
                endDateObj.setDate(endDateObj.getDate() + 1);
                endDate = endDateObj.toISOString().split('T')[0];
            }
            const userId = $('#userId2').val();

            $.ajax({
                url: "https://localhost:8587/api/cancel_transactions_search",
                method: "GET",
                data: { startDate, endDate, userId },
                success: function(data) {
                    cancelTransactionsData = Array.isArray(data) ? data : [data];
                    cancelPage = 1;
                    renderCancelPagination();
                    renderCancelTransactions();
                },
                error: function(xhr, status, error) {
                    console.error("취소 내역을 불러오는 중 오류 발생:", error);
                }
            });
        }

        function renderPagination() {
            const totalPages = Math.ceil(transactionsData.length / itemsPerPage);
            const pagination = $("#pagination");
            pagination.empty();
            for (let i = 1; i <= totalPages; i++) {
                const pageButton = $("<button>").text(i);
                if (i === currentPage) pageButton.addClass("active");
                pageButton.on("click", function() {
                    currentPage = i;
                    renderTransactions();
                    renderPagination();
                });
                pagination.append(pageButton);
            }
        }

        function renderCancelPagination() {
            const totalPages = Math.ceil(cancelTransactionsData.length / itemsPerPage);
            const cancelPagination = $("#cancelPagination");
            cancelPagination.empty();
            for (let i = 1; i <= totalPages; i++) {
                const pageButton = $("<button>").text(i);
                if (i === cancelPage) pageButton.addClass("active");
                pageButton.on("click", function() {
                    cancelPage = i;
                    renderCancelTransactions();
                    
                    renderCancelPagination();
                });
                cancelPagination.append(pageButton);
            }
        }

        function renderTransactions() {
            const startIndex = (currentPage - 1) * itemsPerPage;
            const endIndex = startIndex + itemsPerPage;
            const transactionTableBody = $("#transactionTableBody");
            transactionTableBody.empty();
            transactionsData.slice(startIndex, endIndex).forEach(transaction => {
                const row = '<tr>' +
				        '<td>' + transaction.name + '</td>' +
				        '<td>' + transaction.customerId + '</td>' +
				        '<td>' + transaction.transactionDate + '</td>' +
				        '<td>' + transaction.usedCredits + 'c</td>' +
				        '<td><button class="cancel-btn" onclick="cancel(\'' + transaction.customerId + '\', \'' + transaction.usedCredits + '\', \'' + transaction.transactionDate + '\')">취소</button></td>' +
				    '</tr>';
                transactionTableBody.append(row);
            });
        }
		
	   function cancel(a,b,c){
		     confirmModal.style.display = 'block'
			 
			 document.getElementById('confirmBtn').onclick = function() {
			      confirmModal.style.display = 'none';
			      cancelTransaction(a, b, c);
			 };

			             // 취소 버튼 클릭 이벤트
			 document.getElementById('cancelBtn').onclick = function() {
			       confirmModal.style.display = 'none';
			 };			 
	   }

       function renderCancelTransactions() {
            const startIndex = (cancelPage - 1) * itemsPerPage;
            const endIndex = startIndex + itemsPerPage;
            const cancelTableBody = $("#cancelTableBody");
            cancelTableBody.empty();
            cancelTransactionsData.slice(startIndex, endIndex).forEach(transaction => {
                const row = 
					'<tr>' +
				        '<td>' + transaction.name + '</td>' +
				        '<td>' + transaction.customerId + '</td>' +
				        '<td>' + transaction.transactionDate + '</td>' +
				        '<td>' + transaction.usedCredits + 'c</td>' +
				        '<td><button class="cancel-btn" onclick="cancelcancel(\'' + transaction.customerId + '\', \'' + transaction.usedCredits + '\', \'' + transaction.transactionDate + '\')">취소</button></td>' +
				    '</tr>'				;
                cancelTableBody.append(row);
            });
        }
		
		function cancelcancel(a,b,c){
				     confirmModal.style.display = 'block'
					 
					 document.getElementById('confirmBtn').onclick = function() {
					      confirmModal.style.display = 'none';
					      Transactioncancel(a, b, c);
					 };

					             // 취소 버튼 클릭 이벤트
					 document.getElementById('cancelBtn').onclick = function() {
					       confirmModal.style.display = 'none';
					 };			 
			   }

        function cancelTransaction(customerId, usedCredits, transactionDate) {
            let isoDate = new Date(transactionDate).toISOString();
			console.log("취소 하러 왔다")
            $.ajax({
                url: "https://localhost:8587/api/cancel_transaction",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({ customerId, usedCredits, transactionDate: isoDate }),
                success: function(response) {
                    if (response.success) {
                        alert("거래가 취소되었습니다.");
						loadAllCancelTransactions()
                        loadAllTransactions();
                    } else {
                        alert("거래 취소에 실패했습니다: " + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    console.error("거래 취소 중 오류 발생:", error);
                }
            });
        }
		
		function Transactioncancel(customerId, usedCredits, transactionDate) {
		            let isoDate = new Date(transactionDate).toISOString();
					console.log("취소 하러 왔다222")
		            $.ajax({
		                url: "https://localhost:8587/api/Transactioncancel",
		                method: "POST",
		                contentType: "application/json",
		                data: JSON.stringify({ customerId, usedCredits, transactionDate: isoDate }),
		                success: function(response) {
		                    if (response.success) {
		                        alert("거래가 취소되었습니다.");
		                        loadAllTransactions();
								loadAllCancelTransactions();
		                    } else {
		                        alert("거래 취소에 실패했습니다: " + response.message);
		                    }
		                },
		                error: function(xhr, status, error) {
		                    console.error("거래 취소 중 오류 발생:", error);
		                }
		            });
		        }
    </script>
</body>
</html>
