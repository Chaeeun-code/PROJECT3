<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*, java.net.*, org.json.*" %>
<%@ page import="com.itcom.test1.RegionCoordinates" %>
<%@ page import="java.util.Map, java.util.HashMap, java.util.List, java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Eco Shop</title>
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style1.css">
   <link href="https://fonts.googleapis.com/css2?family=Schoolbell&display=swap" rel="stylesheet">
</head>
<body>
	
	<div id="logoutModal" class="modal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); justify-content: center; align-items: center;">
	    <div class="modal-content" style="background-color: white; padding: 20px; border-radius: 5px; text-align: center;">
	        <p>로그아웃 하시겠습니까?</p>
	        <button id="confirmLogout" class="modal-btn">확인</button>
	        <button id="cancelLogout" class="modal-btn">취소</button>
	    </div>
	</div>
	

    <%-- RegionCoordinates에서 좌표 데이터를 가져오기 --%>
    <%
        Map<String, double[]> coordinatesMap = RegionCoordinates.getRegionCoordinates();
        JSONObject jsonCoordinates = new JSONObject(coordinatesMap);
    %>

   <div class="navbar">
           <div class="logo">
            <a href="${pageContext.request.contextPath}/index">
               <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854684/logo_odbfxc.png" alt="logo" id="logo-image">
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
               <button class="login-btn" onclick="location.href='https://localhost:8443/login'">log in</button>
            </a>
           </div>
       </div>
       
           <div id="logoutModal" class="modal">
    	<div class="modal-content">
        <p>로그아웃 하시겠습니까?</p>
        <button id="cancelLogout" class="modal-btn">취소</button>
        <button id="confirmLogout" class="modal-btn">확인</button>
    	</div>
		</div>
           

   <!-- 배너 섹션 -->
   <div class="banner-section">
        <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730707867/%EB%B0%B0%EB%84%88_lkd2ud.png" alt="배너 이미지" >
        <div class="banner-text">지속 가능한 미래, 함께하는 탄소 제로!</div>
       <div class="recycle-container1">    
           <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854685/recycle_ndlty4.png" alt="왼쪽 풍선" class="recycling-cloud"> 
      </div>
      <div class="recycle-container2">    
           <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854685/noplastic_d8g19n.png" alt="오른쪽 풍선" class="recycling-cloud2">
      </div>
      <div class="recycle-container3">     
            <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854686/water_ehqvcb.png" alt="water" class="recycling-water">
        </div>
        <div class="recycle-container4">     
            <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854686/zero2_y2ovqg.png" alt="zero" class="zero-font">
        </div>
   </div>
   
   <div class="section-gap"></div>

   <div class="eco-section">
      <div class="eco-images-container">
           <div class="eco-image-left">
               <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854684/left_rk3xpj.jpg">
           </div>
           <div class="eco-image-right">
                 <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854686/right_yhjofx.jpg">
           </div>
       </div>
       
       <div class="eco-text">
           <h2>Eco Wave와 함께하는<br>친환경 프로젝트</h2>
           <p>
               지구를 위한 작은 작은 변화, 우리의 손에서 시작됩니다.<br>
               환경 보호는 모두의 참여로 이루어집니다!
           </p>
           <button class="join-btn">참여하기</button>
      </div>
   </div>
   
   <div class="eco-heading-section fade-in-up">
       <div class="report-content">
         <div class="report-text">
            <h2>Eco 보고서<br>친환경 프로젝트</h2>
            <p>지구를 위한 작은 작은 변화, 우리의 손에서 시작됩니다.<br>환경 보호는 모두의 참여로 이루어집니다!</p>
            <a href="${pageContext.request.contextPath}/manage">
            <button class="join-btn">참여하기</button>
            </a>
         </div>
           <div class="eco-icon-box">
              <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854683/eco-icon_ogx2tp.png" alt="eco 아이콘" class="eco_icon">
           </div>
           <div class="heading-box fade-in-up">
              <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1730854683/arrow_duqsvo.png" alt="Eco 아이콘" class="heading-icon">
               <h2>탄소제로란?</h2>
               <p>지구의 숨을 가볍게!<br>우리가 만든 탄소를 없애거나 줄여서 지구에 남는 발자국을 0으로 만드는 일.<br>작은 실천이 모여 더 푸른 내일을 만듭니다.</p>
           </div>
       </div>
   </div>
   
   <div class="report-tags fade-in-up">
      <div class="tag-tag1 fade-in-up">
         <div class="tag1-label"># 심은 나무 수 </div>
           <div class="tag-value"> 381 그루 +</div>
      </div>
      <div class="tag-tag2 fade-in-up">
         <div class="tag2-label"># 줄어든 탄소량</div>
             <div class="tag-value">3,812kg +</div>
      </div>
   </div>

   <div class="eco-effects-section fade-in-up">
       <div class="report-effects fade-in-up">
           <h2>친환경 활동의 효과</h2>
           <p>나무 한 그루를 심으면 연간 약 22kg의 탄소를 흡수할 수 있습니다.<br>
              이런 작은 실천들이 모이면 대기 중 탄소량을 효과적으로 줄이고 지구를 더욱 건강하게 만드는 데 큰 기여를 합니다.<br>
              함께하는 노력이 깨끗한 환경을 만들어갑니다!</p>  
           <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1731292668/free-icon-forest-5052680_zwysow.png" alt="나무이미지" class="tree-image"> 
       </div>
   </div>

   <div class="eco-footer-section fade-in-up">
       <div class="eco-footer-box1 fade-in-up">
           <img src="https://res.cloudinary.com/dhuybxduy/image/upload/v1731294562/group-young-volunteers-park-they-are-planting-tree-seedling_sw1tsg.jpg" alt="나무 심는 사진" class="tree">
           <div class="report-footer fade-in-up">
               <p class="text">친환경 활동 참여자 수</p>
               <h2 class="count">1,964명 +</h2>
           </div>
       </div>
       <div class="eco-footer-box2 fade-in-up">
           <div class="report-footer">
               <h2 class="text2">Let's<br>Campaign!</h2>
           </div>
       </div>
   </div>

   <div class="map-section">
        <h2>전국별 일산화탄소 농도</h2>
        <div id="map" style="width:1000px; height:900px; margin: 0 auto;"></div>
        <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=471d79826dc265d84f4964cd36744859"></script>
   </div>
    
    <%
       String filePath = application.getRealPath("/WEB-INF/data/SIDO_MAP_2022.json");
       StringBuilder jsonData = new StringBuilder();
          try {
              BufferedReader reader = new BufferedReader(new FileReader(filePath));
              String line;
              while ((line = reader.readLine()) != null) {
                  jsonData.append(line);
              }
                 reader.close();
          } catch (IOException e) {
              out.println("파일 읽기 오류: " + e.getMessage());
          }
          
          JSONObject jsonObject = null;
          try {
              jsonObject = new JSONObject(jsonData.toString());
          } catch (JSONException e) {
              out.println("JSON 파싱 오류: " + e.getMessage());
          }

          Map<String, String> nameMapping = new HashMap<>();
          nameMapping.put("서울특별시", "서울");
          nameMapping.put("부산광역시", "부산");
          nameMapping.put("대구광역시", "대구");
          nameMapping.put("인천광역시", "인천");
          nameMapping.put("광주광역시", "광주");
          nameMapping.put("대전광역시", "대전");
          nameMapping.put("울산광역시", "울산");
          nameMapping.put("세종특별자치시", "세종");
          nameMapping.put("경기도", "경기");
          nameMapping.put("강원도", "강원");
          nameMapping.put("충청북도", "충북");
          nameMapping.put("충청남도", "충남");
          nameMapping.put("전라북도", "전북");
          nameMapping.put("전라남도", "전남");
          nameMapping.put("경상북도", "경북");
          nameMapping.put("경상남도", "경남");
          nameMapping.put("제주특별자치도", "제주");

          String apiUrl = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
          String serviceKey = "A7zVq3%2B%2FM1QrIqir8zMHq1FDsGJNghLX9HwEhetgf8XYCmsOubpJNqbM5fkZDCkdpU%2BeT1%2BnKiAi%2BPu8olEkFg%3D%3D";
          String returnType = "json";

          Map<String, Double> cityCoMap = new HashMap<>();

          for (String sidoName : nameMapping.values()) {
              try {
                  String requestUrl = apiUrl + "?serviceKey=" + serviceKey + "&sidoName=" + URLEncoder.encode(sidoName, "UTF-8") + "&returnType=" + returnType;
                  URL url = new URL(requestUrl);
                  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                  conn.setRequestMethod("GET");

                  BufferedReader rd;
                  if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  } else {
                      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                  }
                  StringBuilder sb = new StringBuilder();
                  String line;
                  while ((line = rd.readLine()) != null) {
                      sb.append(line);
                  }
                  rd.close();
                  conn.disconnect();

                  JSONObject jsonResponse = new JSONObject(sb.toString());
                  JSONArray items = jsonResponse.getJSONObject("response").getJSONObject("body").getJSONArray("items");

                  double totalCoValue = 0.0;
                  int count = 0;

                  for (int i = 0; i < items.length(); i++) {
                      JSONObject item = items.getJSONObject(i);
                      double coValue = item.optDouble("coValue", 0.0);
                      totalCoValue += coValue;
                      count++;
                  }

                  if (count > 0) {
                      cityCoMap.put(sidoName, totalCoValue / count);
                  } else {
                      cityCoMap.put(sidoName, 0.0);
                  }
              } catch (Exception e) {
                  out.println("API 요청 오류: " + e.getMessage());
              }
          }
    %>
    
       <script>
        var sidoMapData = <%= jsonObject.toString() %>;
        var cityCoData = <%= new JSONObject(cityCoMap).toString() %>;
        var nameMapping = <%= new JSONObject(nameMapping).toString() %>;
        var regionCoordinates = <%= jsonCoordinates.toString() %>;

        var container = document.getElementById('map');
        var options = {
            center: new kakao.maps.LatLng(36.5, 127.8),
            level: 12
        };
        
        var map = new kakao.maps.Map(container, options);
        var currentOverlay;
        
        sidoMapData.features.forEach(function(feature) {
            var geometry = feature.geometry;
            var coordinates = geometry.coordinates;
            var paths = [];
            var cityName = feature.properties.CTP_KOR_NM;
            var apiName = nameMapping[cityName];
            var coValue = cityCoData[apiName];

            if (geometry.type === "Polygon") {
                coordinates[0].forEach(function(coord) {
                    paths.push(new kakao.maps.LatLng(coord[1], coord[0]));
                });
            } else if (geometry.type === "MultiPolygon") {
                coordinates.forEach(function(polygon) {
                    var path = [];
                    polygon[0].forEach(function(coord) {
                        path.push(new kakao.maps.LatLng(coord[1], coord[0]));
                    });
                    paths.push(path);
                });
            }

            var polygon = new kakao.maps.Polygon({
                map: map,
                path: paths,
                strokeWeight: 2,
                strokeColor: '#CCE6CC',
                strokeOpacity: 0.9,
                fillColor: '#66B366',
                fillOpacity: 0.15
            });

        var regionNotes = {
            "서울": "서울의 메모입니다.",
            "부산": "부산의 메모입니다.",
            "대구": "대구의 메모입니다.",
            "인천": "인천의 메모입니다.",
            "광주": "광주의 메모입니다.",
            "대전": "대전의 메모입니다.",
            "울산": "울산의 메모입니다.",
            "세종": "세종의 메모입니다.",
            "경기": "경기의 메모입니다.",
            "강원": "강원의 메모입니다.",
            "충북": "충북의 메모입니다.",
            "충남": "충남의 메모입니다.",
            "전북": "전북의 메모입니다.",
            "전남": "전남의 메모입니다.",
            "경북": "경북의 메모입니다.",
            "경남": "경남의 메모입니다.",
            "제주": "제주의 메모입니다."
        };

        var defaultStrokeColor = '#CCE6CC';
        var defaultFillColor = '#66B366';
        var hoverStrokeColor = '#145214';
        var hoverFillColor = '#339933';

        polygon.setOptions({
            strokeColor: defaultStrokeColor,
            fillColor: defaultFillColor
        });

        kakao.maps.event.addListener(polygon, 'mouseover', function() {
            polygon.setOptions({
                strokeColor: hoverStrokeColor,
                fillColor: hoverFillColor,
                fillOpacity: 0.25
            });
        });

        kakao.maps.event.addListener(polygon, 'mouseout', function() {
            polygon.setOptions({
                strokeColor: defaultStrokeColor,
                fillColor: defaultFillColor,
                fillOpacity: 0.15
            });
        });

        kakao.maps.event.addListener(polygon, 'click', function() {
            var regionName = feature.properties.CTP_KOR_NM;
            var note = regionNotes[regionName] || "메모가 없습니다.";
            var displayCoValue = coValue !== undefined ? coValue.toFixed(2) : "정보 없음";

            var latSum = 0, lngSum = 0, count = 0;
            if (geometry.type === "Polygon") {
                coordinates[0].forEach(function(coord) {
                    latSum += coord[1];
                    lngSum += coord[0];
                    count++;
                });
            } else if (geometry.type === "MultiPolygon") {
                coordinates.forEach(function(polygon) {
                    polygon[0].forEach(function(coord) {
                        latSum += coord[1];
                        lngSum += coord[0];
                        count++;
                    });
                });
            }
            var centerLatLng = new kakao.maps.LatLng(latSum / count, lngSum / count);

            var overlayContent = 
                '<div class="note-overlay">' +
                    '<strong>' + regionName + '</strong><br>' +
                    '메모: ' + note + '<br>' +
                    'CO 농도: ' + displayCoValue + 'ppm' +
                '</div>';

            var overlay = new kakao.maps.CustomOverlay({
                position: centerLatLng,
                content: overlayContent,
                yAnchor: 0.5,
                xAnchor: 0.5
            });

            if (currentOverlay) {
                currentOverlay.setMap(null);
            }
            currentOverlay = overlay;
            currentOverlay.setMap(map);
        });

        var fillColor, innerColor, circleSize;
        if (coValue > 0.4) {
            fillColor = '#004578';
            innerColor = '#004578';
            circleSize = 35;
        } else if (coValue > 0.26) {
            fillColor = '#106ebe';
            innerColor = '#106ebe';
            circleSize = 28;
        } else {
            fillColor = '#71afe5';
            innerColor = '#71afe5';
            circleSize = 23;
        }

        var circleContent = 
            '<div class="outer-circle" style="border-color:' + fillColor + '; width: ' + (circleSize * 1.5) + 'px; height: ' + (circleSize * 1.5) + 'px;">' +
            '<div class="inner-circle" style="background-color:' + innerColor + '; width: ' + circleSize + 'px; height: ' + circleSize + 'px;">' +
            coValue.toFixed(1) +
            '</div></div>';
        var centerLatLng = new kakao.maps.LatLng(regionCoordinates[apiName][0], regionCoordinates[apiName][1]);
        var customOverlay = new kakao.maps.CustomOverlay({
            position: centerLatLng,
            content: circleContent,
            yAnchor: 0.5,
            xAnchor: 0.5
        });
        customOverlay.setMap(map);
    });
</script>

   <!-- 기존에 있던 다른 script 및 이벤트 코드들 유지 -->
   <script src="script.js"></script>
    

    
   <script>
   window.addEventListener("scroll", function() {
        const navbar = document.querySelector('.navbar');
        const navLinks = document.querySelectorAll('.nav-links a');
        const authButtons = document.querySelectorAll('.auth-buttons button');
        const logoImage = document.getElementById("logo-image");
        const cartImage = document.getElementById("cart-image");

        if (window.scrollY === 0) {
            navbar.style.backgroundColor = "transparent"; 
            navLinks.forEach(link => link.style.color = "#ffffff"); 
            authButtons.forEach(button => button.style.color = "#ffffff"); 
            logoImage.src = "https://res.cloudinary.com/dhuybxduy/image/upload/v1730854685/logo2_n7qyfi.png";

        } else {
            navbar.style.backgroundColor = "#ffffff"; 
            navLinks.forEach(link => link.style.color = "#000000"); 
            authButtons.forEach(button => button.style.color = "#000000"); 
            logoImage.src = "https://res.cloudinary.com/dhuybxduy/image/upload/v1730854684/logo_odbfxc.png";

        }
    });
   
   </script>

   
   <script>
       document.addEventListener("DOMContentLoaded", function() {
           const authButtons = document.querySelector('.auth-buttons');
           const accessToken = localStorage.getItem("accessToken") || sessionStorage.getItem("accessToken");
           const savedUsername = localStorage.getItem("savedUsername") || sessionStorage.getItem("username");
           console.log("저장된 사용자 이름:", savedUsername);

           if (savedUsername) {
               console.log("로그인 상태입니다.");
               authButtons.innerHTML =
                   '<a href="${pageContext.request.contextPath}/shop_cart">' +
                       '<button class="cart-btn">장바구니</button>' +
                   '</a>' +
                   '<a href="${pageContext.request.contextPath}/mypage">' +
                       '<button class="cart-btn">' + savedUsername + '님</button>' +
                   '</a>' +
                   '<button id="logoutButton" class="cart-btn">로그아웃</button>';

               // 로그아웃 버튼 클릭 시 모달 열기
               document.getElementById("logoutButton").addEventListener("click", function() {
                   document.getElementById("logoutModal").style.display = "flex";
               });
           }

           // 모달 창에서 '취소' 버튼 클릭 시 모달 닫기
           document.getElementById("cancelLogout").addEventListener("click", function() {
               document.getElementById("logoutModal").style.display = "none";
           });

           // 모달 창에서 '확인' 버튼 클릭 시 로그아웃 요청
           document.getElementById("confirmLogout").addEventListener("click", function() {
               performLogout();
           });
       });

       // 로그아웃 요청 함수
       function performLogout() {
           fetch("https://localhost:8030/jwt/logout", {
               method: "POST",
               credentials: "include"  // 쿠키 포함하여 요청
           })
           .then(response => {
               if (response.status === 200) {
                   // 로그아웃 성공 시 세션 스토리지와 쿠키 정리
                   clearSessionAndCookie();
                   alert("로그아웃되었습니다.");
                   window.location.href = "/login";  // 로그아웃 후 로그인 페이지로 이동
               } else {
                   alert("로그아웃에 실패했습니다.");
               }
           })
           .catch(error => {
               console.error("로그아웃 오류:", error);
               alert("로그아웃 요청 중 오류가 발생했습니다.");
           });
       }

       // 세션 스토리지와 쿠키 정리 함수
       function clearSessionAndCookie() {
           // 세션 스토리지 비우기
           sessionStorage.clear();

           // 리프레시 토큰 쿠키 삭제
           document.cookie = "refreshToken=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 UTC; Secure; HttpOnly; SameSite=Lax";
       }
   </script>

         <script>
			
        document.addEventListener("DOMContentLoaded", function() {
            const authButtons = document.querySelector('.auth-buttons');
            const accessToken = localStorage.getItem("accessToken") || sessionStorage.getItem("accessToken");
            const savedUsername = localStorage.getItem("savedUsername") || sessionStorage.getItem("username");
            console.log("저장된 사용자 이름:", savedUsername);

            if (savedUsername) {
                console.log("로그인 상태입니다.");
                authButtons.innerHTML =
                    '<a href="${pageContext.request.contextPath}/shop_cart">' +
                        '<button class="cart-btn">장바구니</button>' +
                    '</a>' +
                    '<a href="${pageContext.request.contextPath}/mypage">' +
                        '<button class="cart-btn">' + savedUsername + '님</button>' +
                    '</a>' +
                    '<button id="logoutButton" class="cart-btn">로그아웃</button>';

                // 로그아웃 버튼 클릭 시 모달 열기
                document.getElementById("logoutButton").addEventListener("click", function() {
                    document.getElementById("logoutModal").style.display = "flex";

					
					
                });
            }
            document.getElementById("cancelLogout").addEventListener("click", function() {
                document.getElementById("logoutModal").style.display = "none";
            });
        });
		
		
    </script>
    
	
	

<!--  에코섹션 애니메이션 효과주려고 script 작성 -->
<script>
	document.addEventListener("DOMContentLoaded", function() {
	    const ecoSection = document.querySelector('.eco-section');
	    let lastScrollY = window.scrollY; // 이전 스크롤 위치 저장
	    let animationPlayed = false;
	
	    const observer = new IntersectionObserver(entries => {
	        entries.forEach(entry => {
	            const currentScrollY = window.scrollY;
	
	            if (entry.isIntersecting && currentScrollY > lastScrollY && !animationPlayed) {
	                ecoSection.classList.add('show');
	                animationPlayed = true; // 애니메이션이 실행되었음을 표시
	            } else if (!entry.isIntersecting && currentScrollY < lastScrollY && animationPlayed) {
	                ecoSection.classList.remove('show');
	                animationPlayed = false; // 다시 위에서 내려올 때만 애니메이션이 작동하도록 초기화
	            }
	            lastScrollY = currentScrollY;
	        });
	    }, { threshold: 0.5 }); // 에코 섹션이 50% 보이면 실행
	
	    observer.observe(ecoSection);
	
	    window.addEventListener("scroll", function() {
	        // 에코 섹션 밑으로 스크롤을 완전히 내려가면 애니메이션 유지
	        if (window.scrollY >= ecoSection.offsetTop + ecoSection.offsetHeight) {
	            ecoSection.classList.add('show');
	        }
	    });
	});
</script>

<script>
	document.addEventListener("DOMContentLoaded", function() {
	    const fadeElements = document.querySelectorAll('.fade-in-up');
	    let lastScrollY = window.scrollY;

	    fadeElements.forEach((element) => {
	        let animationPlayed = false;

	        const observer = new IntersectionObserver(entries => {
	            entries.forEach(entry => {
	                const currentScrollY = window.scrollY;

	                if (entry.isIntersecting && currentScrollY > lastScrollY && !animationPlayed) {
	                    entry.target.classList.add('show');
	                    animationPlayed = true; // 애니메이션이 실행되었음을 표시
	                } else if (!entry.isIntersecting && currentScrollY < lastScrollY) {
	                    entry.target.classList.remove('show');
	                    animationPlayed = false; // 다시 위에서 내려올 때만 애니메이션이 작동하도록 초기화
	                }

	                lastScrollY = currentScrollY;
	            });
	        }, { threshold: 0.5 });

	        observer.observe(element);
	    });

	    window.addEventListener("scroll", function() {
	        fadeElements.forEach((element) => {
	            if (window.scrollY >= element.offsetTop + element.offsetHeight) {
	                element.classList.add('show'); // 요소가 화면에 완전히 진입했을 때 'show' 유지
	            }
	        });
	    });
	});
</script>
</body>
</html>