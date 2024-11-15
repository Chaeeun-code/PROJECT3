<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 페이지</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f0f0f0;
        }
        .login-container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }
        .login-container input {
            margin: 10px 0;
            padding: 10px;
            width: calc(100% - 22px);
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .login-container button {
            padding: 10px;
            width: 100%;
            background: #98EECC;
            color: #fff;
            border: none;
            cursor: pointer;
            border-radius: 4px;
        }
        #responseMessage, #tokenDisplay {
            margin-top: 15px;
            color: #333;
        }
        .checkbox-container {
            display: flex;
            justify-content: center; /* 중앙 정렬 */
            gap: 20px;
            width: 100%;
            margin-bottom: 10px;
        }
        .checkbox-item {
            display: flex;
            align-items: center;
            gap: 5px; /* 체크박스와 텍스트 사이 간격 */
            white-space: nowrap; /* 줄바꿈 방지 */
        }
        .social-buttons {
            display: flex;
            justify-content: space-around;
            margin-top: 15px;
        }
        .social-buttons img {
            cursor: pointer;
            width: 80px;
            height: auto;
        }
        .login-links {
            margin-top: 10px;
            display: flex;
            justify-content: space-around;
            color: #333;
            text-decoration: none;
        }
        .login-links a {
            color: #333;
            text-decoration: none;
        }
        .login-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>로그인</h2>
        <form id="loginForm" onsubmit="return login(event)">
            <input type="text" id="username" placeholder="아이디" required><br>
            <input type="password" id="password" placeholder="비밀번호" required><br>
            
            <!-- 아이디 저장 및 자동 로그인 체크박스를 가로로 정렬 -->
            <div class="checkbox-container">
                <div class="checkbox-item">
                    <input type="checkbox" id="saveId">
                    <label for="saveId">아이디 저장</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="autoLogin">
                    <label for="autoLogin">자동 로그인</label>
                </div>
            </div>
            
            <button type="submit">로그인</button>

            <!-- 아이디 찾기 및 비밀번호 찾기 링크 위치 -->
            <div class="login-links">
                <a href="/findid">아이디 찾기</a>
                <a href="/findpassword">비밀번호 찾기</a>
            </div>
        </form>
        <p id="responseMessage"></p>
        <p id="tokenDisplay"></p>

        <div class="social-buttons">
            <img src="https://ipfs.io/ipfs/QmUDYqi4QDoKXNiJRccn69tiSgRoa7dZMvoYiW6RVY7Nti" alt="네이버로 시작하기" onclick="loginWithNaver()" />
            <img src="https://ipfs.io/ipfs/QmZ11YCRx3RRrS3UWryNyGg6u95ZnQhQp65n5Zjy7szyDX" alt="구글로 시작하기" onclick="onGoogleSignIn()" />
            <img src="https://ipfs.io/ipfs/QmdF7Ucj9jL8NXhTgUPPC5EDBitFKc57Qo6h7Btg7ZnMS9" alt="카카오로 시작하기" onclick="loginWithKakao()" />
        </div>

        <!-- 회원가입 링크 -->
        <a href="/signup" class="signup-link">아직 계정이 없으신가요? 회원가입</a>
    </div>

<script>

document.addEventListener("DOMContentLoaded", function() {
    const savedUsername = localStorage.getItem("savedUsername");
    const autoLogin = localStorage.getItem("autoLogin");

    if (savedUsername) {
        document.getElementById("username").value = savedUsername;
    }

    if (autoLogin && savedUsername) {
        login(); // 자동 로그인 시도 (비밀번호 저장 대신 세션 토큰 기반으로 변경 고려)
    }
});

function login(event) {
    if (event) event.preventDefault();

    // 메시지 초기화
    document.getElementById("responseMessage").innerText = "";

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const saveId = document.getElementById("saveId").checked;
    const autoLogin = document.getElementById("autoLogin").checked;
    const payload = { username, password };

    // 첫 번째 /login 요청
    fetch("https://localhost:8030/jwt/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
        credentials: "include"  // 쿠키 포함 요청
    })
    .then(response => {
        if (response.status === 200) {
            // 중복 세션이 없는 경우, 로그인 성공 처리
            return response.json().then(data => handleLoginSuccess(data, username, saveId, autoLogin));
        } else if (response.status === 409) {
            // 중복 세션이 있는 경우, 사용자에게 알림
            return response.json().then(data => {
                const userConfirmed = confirm(data.errorMessage);
                
                if (userConfirmed) {
                    sessionStorage.setItem("oneTimeToken", data.oneTimeToken);
                    // 세션 만료를 위한 /expire 요청 실행 후 재로그인 시도
                    return expireSessionAndRetryLogin(username, payload, saveId, autoLogin);
                } else {
                    throw new Error("다중 세션은 허용되지 않습니다");
                }
            });
        } else if (response.status === 401) {
            document.getElementById("responseMessage").innerText = "아이디 또는 비밀번호가 유효하지 않습니다";
            throw new Error("Unauthorized");
        } else {
            throw new Error("로그인 중 오류가 발생했습니다.");
        }
    })
    .catch(error => {
        if (error.message !== "Unauthorized") {
            console.error("Error:", error);
        }
    });
}

// /expire 요청을 통해 세션을 만료하고 재로그인 시도
function expireSessionAndRetryLogin(username, payload, saveId, autoLogin) {
    const storedToken = sessionStorage.getItem("oneTimeToken");

    return fetch("https://localhost:8030/jwt/expire", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ 
            username: username,
            oneTimeToken: storedToken
        }),
    })
    .then(expireResponse => {
        if (expireResponse.ok) {
            // 세션 만료 성공 후 재로그인 시도
            return fetch("https://localhost:8030/jwt/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
                credentials: "include"
            })
            .then(loginResponse => {
                if (loginResponse.status === 200) {
                    return loginResponse.json().then(data => handleLoginSuccess(data, username, saveId, autoLogin));
                } else {
                    throw new Error("재로그인 중 오류가 발생했습니다.");
                }
            });
        } else {
            throw new Error("세션 만료 요청이 실패했습니다.");
        }
    })
    .catch(error => {
        console.error("Error during session expire and retry login:", error);
    });
}

// 로그인 성공 시 토큰 처리 및 리다이렉트 공통 함수
function handleLoginSuccess(data, username, saveId, autoLogin) {
    console.log("Final token data:", data);
    if (data.token) {
        localStorage.setItem("accessToken", data.token);
        sessionStorage.setItem("username", username);

        if (saveId) {
            localStorage.setItem("savedUsername", username);
        } else {
            localStorage.removeItem("savedUsername");
        }

        if (autoLogin) {
            localStorage.setItem("autoLogin", "true");
        } else {
            localStorage.removeItem("autoLogin");
        }

        // 성공 후 메인 페이지로 이동
        window.location.href = "/index";
    } else {
        document.getElementById("responseMessage").innerText = "아이디 또는 비밀번호가 유효하지 않습니다.";
    }
}




</script>
</body>
</html>
