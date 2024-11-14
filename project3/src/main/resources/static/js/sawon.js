// 백엔드에서 제공하는 API 호출 (test_sql2 API)
function fetchSawonInfo(id) {
    fetch(`http://localhost:8080/api/sawon/info/${id}`)  // 백엔드 API 호출
        .then(response => response.json())
        .then(data => {
            console.log('Sawon 정보:', data);
            document.getElementById('sawonInfo').innerText = JSON.stringify(data);
        })
        .catch(error => console.error('API 호출 중 오류 발생:', error));
}

// 예시로 ID가 'S001'인 Sawon 정보를 가져오는 API 호출
fetchSawonInfo('sjm3802');
