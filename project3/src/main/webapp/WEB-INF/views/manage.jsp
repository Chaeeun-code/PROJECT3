<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage mode</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style0.css">
    <link href="https://fonts.googleapis.com/css2?family=Schoolbell&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.highcharts.com/highcharts.js"></script>
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
            <a href="${pageContext.request.contextPath}/manage_certification">활동인증관리</a>
        </div>
        <div class="auth-buttons">
            <a href="${pageContext.request.contextPath}/manage_credit">
                <button class="signup-btn">구매 관리</button>
            </a>
            <a href="${pageContext.request.contextPath}/company">
                <button class="signup-btn">상품 등록</button>
            </a>
            <button class="login-btn">log in</button>
        </div>
    </div>
    
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/manage" class="active">관리자 홈</a>
        <a href="${pageContext.request.contextPath}/manage_credit">구매 관리</a>
        <a href="${pageContext.request.contextPath}/company">상품 등록</a>
        <a href="${pageContext.request.contextPath}/manage_certification">활동 인증 관리</a>
        <a href="#">회원 관리</a>
    </div>


    <!-- 차트들이 포함된 직사각형 틀 -->
    <div class="chart-box">
    <!-- 차트들을 가로로 나란히 배치 -->
    <div class="chart-row">
        <div id="chart-container1" class="chart-container"></div>
        <div id="chart-container3" class="chart-container"></div>
        <div id="chart-container4" class="chart-container"></div>
    </div>
    <!-- 월별 가입자 차트 (가장 아래 위치) -->
    <div id="chart-container2" class="chart-container"></div>
	</div>

    
    <!-- 월별 가입자 차트 (가장 아래 위치) -->
    

    <!-- JavaScript for Chart -->
    <script>
    $(document).ready(function () {
    	$('.sidebar a').each(function () {
            if (this.href === window.location.href) {
                $(this).addClass('active');
            }
        });

        $('.sidebar a').click(function () {
            $('.sidebar a').removeClass('active');
            $(this).addClass('active');
        });

    	
        // 클릭 횟수가 가장 많은 상품 Top 4 데이터를 가져오기 위한 AJAX 호출
        $.ajax({
            url: 'https://localhost:8777/api/topClickCountItems',
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("Received data:", data);

                // 데이터 포인트에 thingId를 포함시킵니다.
                const dataPoints = data.map(item => ({
                    name: item.name,
                    y: item.clickCount,
                    thingId: item.thingId // thingId 정보를 추가합니다.
                }));

                Highcharts.chart('chart-container1', {
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: '클릭 횟수 TOP'
                    },
                    xAxis: {
                        categories: data.map(item => item.name),
                        title: {
                            text: '상품 이름'
                        }
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '클릭 횟수'
                        }
                    },
                    series: [{
                        name: '클릭 횟수',
                        data: dataPoints.map((point, index) => ({
                            y: point.y,
                            color: index === 0 ? '#79E0EE' : index === 1 ? '#98EECC' : '#D0F5BE', // 각 막대의 색상을 다르게 지정 (예: 파랑, 빨강, 초록)
                            thingId: point.thingId,
                            name: point.name
                        }))
                    }],
                    plotOptions: {
                        series: {
                            cursor: 'pointer',
                            point: {
                                events: {
                                    click: function () {
                                        // thingId를 직접 사용하여 demographics 데이터를 가져옵니다.
                                        getDemographicsData(this.options.thingId);
                                    }
                                }
                            }
                        }
                    }
                });
            },
            error: function (xhr, status, error) {
                console.error('차트 데이터를 가져오는 중 오류가 발생했습니다: ' + error);
            }
        });

        // 성비와 나이대 데이터를 가져오는 함수
        function getDemographicsData(thingId) {
            console.log('Demographics request URL:', 'https://localhost:8777/api/getProductDemographics?thingId=' + encodeURIComponent(thingId));
            $.ajax({
                url: 'https://localhost:8777/api/getProductDemographics?thingId=' + encodeURIComponent(thingId),
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    console.log("Received demographics data:", data);

                    const genderData = [
                        { name: '남성', y: data.MAN_COUNT },
                        { name: '여성', y: data.WOMAN_COUNT }
                    ];

                    const ageData = [
                        { name: '10대', y: data.TEENAGER_COUNT},
                        { name: '20-40대', y: data.MIDDLEAGED_COUNT},
                        { name: '기타', y: data.ELSEAGE_COUNT}
                    ];

                    // 성비 원형 차트 생성
                    Highcharts.chart('chart-container3', {
                        chart: {
                            type: 'pie'
                        },
                        title: {
                            text: '클릭 성비'
                        },
                        plotOptions: {
                            pie: {
                                size: '60%',  // 파이 차트 크기를 전체 차트의 60%로 설정
                                innerSize: '20%',  // 도넛형으로 변경하고 내부 크기 설정
                                dataLabels: {
                                    enabled: true,
                                    format: '{point.percentage:.1f} %', // 데이터 레이블 형식
                                    style: {
                                        color: '#000000',  // 텍스트 색상
                                        fontSize: '12px'
                                    }
                                }
                            }
                        },
                        series: [{
                            name: '성비',
                            colorByPoint: true,
                            data: [
                                { name: '남성', y: data.MAN_COUNT, color: '#3498db' },  // 남성 색상 지정 (예: 파란색)
                                { name: '여성', y: data.WOMAN_COUNT, color: '#e74c3c' } // 여성 색상 지정 (예: 빨간색)
                            ]
                        }]
                    });

                    // 나이대 원형 차트 생성
                    Highcharts.chart('chart-container4', {
                        chart: {
                            type: 'pie'
                        },
                        title: {
                            text: '클릭 나이대'
                        },
                        series: [{
                            name: '나이대',
                            colorByPoint: true,
                            data: [
                            	 { name: '10대', y: data.TEENAGER_COUNT, color: '#79E0EE' },  // 10대 색상 지정 (예: 청록색)
                                 { name: '20-40대', y: data.MIDDLEAGED_COUNT, color: '#98EECC' }, // 20-40대 색상 지정 (예: 보라색)
                                 { name: '기타', y: data.ELSEAGE_COUNT, color: '#D0F5BE' } 
                            	
                            ]
                        
                        }]
                    });
                },
                error: function (xhr, status, error) {
                    console.error('성비 및 나이대 데이터를 가져오는 중 오류가 발생했습니다: ' + error);
                }
            });
        }

        // 월별 가입자 수 데이터를 가져오기 위한 AJAX 호출 (가장 아래에 위치)
        $.ajax({
            url: 'https://localhost:8777/api/monthlySignupCount',
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("Received monthly signup data:", data);

                // 데이터 가공: 대문자로 된 키 이름을 그대로 사용합니다.
                const months = data.map(item => item.SIGNUP_MONTH);
                const signupCounts = data.map(item => item.SIGNUP_COUNT);

                console.log("Months:", months);
                console.log("Signup Counts:", signupCounts);

                Highcharts.chart('chart-container2', {
                    chart: {
                        type: 'line'
                    },
                    title: {
                        text: '월별 가입자 수'
                    },
                    xAxis: {
                        categories: months,
                        title: {
                            text: '월'
                        }
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '가입자 수'
                        }
                    },
                    series: [{
                        name: '가입자 수',
                        data: signupCounts
                    }]
                });
            },
            error: function (xhr, status, error) {
                console.error('월별 가입자 수 데이터를 가져오는 중 오류가 발생했습니다: ' + error);
            }
        });
    });
    </script>
</body>
</html>
