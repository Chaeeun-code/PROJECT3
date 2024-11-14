package com.sboot.kaja;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import com.sboot.kaja.dao.BuyDAO;
import com.sboot.kaja.dao.SawonDAO;
import com.sboot.kaja.vo.BuyVO;
import com.sboot.kaja.vo.CreditTransactionVO;
import com.sboot.kaja.vo.SawonVO;

@RestController
@CrossOrigin(origins="https://localhost:8443")
public class kajaController {
    
    @Autowired
    SawonDAO dao;
    
    @Autowired
    private CreditService creditService;
    
    @Autowired
    private BuyDAO buyDao;
    
    @GetMapping("/api/order_history")
    public List<BuyVO> getOrderHistory(@RequestParam String customerId) {
        return buyDao.findByCustomerId(customerId); // MyBatis 매퍼를 통해 구매 이력 조회
    }
    
   
    //크레딧 조회용 api
    
    @GetMapping("/api/shop_buy")
    public ResponseEntity<?> getShopBuy() {
        String customerId = "C001";
        Integer credit = dao.getCreditById(customerId);
        System.out.println("credit"+ credit);

        // 크레딧 값이 null일 경우 기본 값 설정
        if (credit == null) {
            credit = 0;  // 기본 값으로 0 설정
        }

        return new ResponseEntity<>(credit, HttpStatus.OK);  // JSON 응답 반환
    }
    
    @CrossOrigin(origins="https://localhost:8443")
    @GetMapping("/api/mypg")
    public ResponseEntity<?> getID(@RequestParam(required = false) String customerId) {
        // customerId가 null 또는 빈 문자열일 경우 기본값 "C001"을 사용
        if (customerId == null || customerId.isEmpty()) {
            customerId = "C001";
        }

        // DB에서 customerId로 조회
        String id = dao.getId(customerId);
        System.out.println("id: " + id);

        if (id == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    
    @CrossOrigin(origins="https://localhost:8443")
    @GetMapping("/api/customer_info")
    public ResponseEntity<SawonVO> getInfo(@RequestParam(required = false) String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            customerId = "C001";
        }

        SawonVO customerInfo = dao.getInfo(customerId);
        if (customerInfo == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return new ResponseEntity<>(customerInfo, HttpStatus.OK);
    }


    
    // 로직 빼서 CreditService.java 트랜잭션 내로 옮김.
    @CrossOrigin(origins = "https://localhost:8443")
    @PostMapping("/api/use_credits")
    public ResponseEntity<Map<String, Object>> useCredits(@RequestBody Map<String, Object> request) {
        String customerId = "C001"; 
        Integer usedCredits = (Integer) request.get("usedCredits");

        return creditService.useCredits(customerId, usedCredits);
    }
    
   

        
        
        @CrossOrigin(origins = "https://localhost:8443")
        @GetMapping("/api/transactions")
        public ResponseEntity<List<CreditTransactionVO>> getTransactions() {
            // 모든 거래 내역을 반환
            List<CreditTransactionVO> transactions = creditService.getAllTransactions();
            return ResponseEntity.ok(transactions);
        }
        
        @CrossOrigin(origins = "https://localhost:8443")
        @GetMapping("/api/cancel_transactiondate")
        public ResponseEntity<List<CreditTransactionVO>> getcencelTransactions() {
            List<CreditTransactionVO> transactions = dao.getAllcancelTransactions();
            System.out.println(dao.getAllcancelTransactions());
            return ResponseEntity.ok(transactions);
        }
        
 
        
        @CrossOrigin(origins = "https://localhost:8443") 
        @GetMapping("/api/transactions_search")
        public ResponseEntity<List<CreditTransactionVO>> getTransactions(
                
                @RequestParam(required = false) String startDate,
                @RequestParam(required = false) String endDate,
                @RequestParam(required = false) String userId
        ) {

            // 검색 조건을 Map 형태로 전달하여 쿼리에서 사용할 수 있도록 함
            Map<String, Object> filters = new HashMap<>();
            filters.put("startDate", startDate);
            filters.put("endDate", endDate);
            filters.put("userId", userId);
            
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("User ID: " + userId);
            System.out.println("Filters: " + filters);
            
            if (startDate == null) { 
                System.out.println("startDate is null"); 
            }

            // 직접 DAO를 통해 검색 조건을 기반으로 데이터를 조회
            List<CreditTransactionVO> transactions = dao.getTransactionsWithFilters(filters);

            // 결과 출력
            if (transactions.isEmpty()) {
                System.out.println("No transactions found for the given filters.");
            } else {
                System.out.println(transactions); // 실제 트랜잭션이 있는 경우 출력
            }

            return ResponseEntity.ok(transactions);
        }
        
        
        @CrossOrigin(origins = "https://localhost:8443") 
        @GetMapping("/api/cancel_transactions_search")
        public ResponseEntity<List<CreditTransactionVO>> getcancelTransactions(
                
                @RequestParam(required = false) String startDate,
                @RequestParam(required = false) String endDate,
                @RequestParam(required = false) String userId
        ) {

            // 검색 조건을 Map 형태로 전달하여 쿼리에서 사용할 수 있도록 함
            Map<String, Object> filters1 = new HashMap<>();
            filters1.put("startDate", startDate);
            filters1.put("endDate", endDate);
            filters1.put("userId", userId);
            
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("User ID: " + userId);
            System.out.println("Filters: " + filters1);
            
            if (startDate == null) { 
                System.out.println("startDate is null"); 
            }

            // 직접 DAO를 통해 검색 조건을 기반으로 데이터를 조회
            List<CreditTransactionVO> transactions = dao.getTransactionscancel(filters1);
            System.out.println("여기까지는 잘 나오네");
            System.out.println(transactions);
            // 결과 출력
            if (transactions.isEmpty()) {
                System.out.println("No transactions found for the given filters.");
            } else {
                System.out.println(transactions); // 실제 트랜잭션이 있는 경우 출력
            }

            return ResponseEntity.ok(transactions);
        }
        
        // 여기의 로직도 CreditService.java의 트랜잭션내로 옮김.
        @CrossOrigin(origins = "https://localhost:8443")
        @PostMapping("/api/cancel_transaction")
        public ResponseEntity<Map<String, Object>> cancelTransaction(@RequestBody Map<String, Object> request) {
            String customerId = (String) request.get("customerId");
            String usedCreditsStr = (String) request.get("usedCredits");
            Integer usedCredits = Integer.parseInt(usedCreditsStr);
            String transactionDateStr = (String) request.get("transactionDate");   
            System.out.println("취소하러 오긴함");
            System.out.println(transactionDateStr);
            Instant instant = Instant.parse(transactionDateStr);
            ZonedDateTime seoulZonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));
 
            Timestamp transactionDate = Timestamp.from(seoulZonedDateTime.toInstant());

            
            Map<String, Object> response = new HashMap<>();

            
            try {
                // CreditService의 cancelTransaction 메서드 호출
                creditService.cancelTransaction(customerId, usedCredits, transactionDate);
                response.put("success", true);
                response.put("message", "거래가 취소되었습니다.");
            } catch (RuntimeException e) {
                response.put("success", false);
                response.put("message", e.getMessage()); // 예외 메시지 반환
            }

            return ResponseEntity.ok(response);
            
        }
        
        @CrossOrigin(origins = "https://localhost:8443")
        @PostMapping("/api/Transactioncancel")
        public ResponseEntity<Map<String, Object>> Transactioncancael(@RequestBody Map<String, Object> request) {
            String customerId = (String) request.get("customerId");
            String usedCreditsStr = (String) request.get("usedCredits");
            Integer usedCredits = Integer.parseInt(usedCreditsStr);
            String transactionDateStr = (String) request.get("transactionDate");   
            System.out.println("취소하러 오긴함");
            System.out.println(transactionDateStr);
            
            Instant instant = Instant.parse(transactionDateStr);
            ZonedDateTime seoulZonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));
 
            Timestamp transactionDate = Timestamp.from(seoulZonedDateTime.toInstant());

            
            Map<String, Object> response = new HashMap<>();

            
                // 기존 크레딧을 DB에서 가져오기
                Integer currentCredit = dao.getCreditById(customerId);
                System.out.println(currentCredit);
                // 크레딧 반환 로직
                Integer updatedCredit = currentCredit + usedCredits;
                dao.updateCredit(customerId, updatedCredit);
                // DB에서 해당 거래 내역 삭제 (필요 시)
                
                dao.insertCredit(customerId,transactionDate,usedCredits);
                int a = dao.deletecredit(customerId, transactionDate);

                System.out.println("a");
                
              System.out.println(a);
               if (a==1) {
                response.put("success", true);
                response.put("message", "거래가 취소되었습니다.");
               }
                return ResponseEntity.ok(response);
            
        }
        
        @CrossOrigin(origins = "https://localhost:8443")
        @PostMapping("/api/add_credits")
        public ResponseEntity<Map<String, Object>> addCredits(@RequestBody Map<String, Object> request) {
            String customerId = (String) request.get("customerId"); // 고객 ID를 요청 본문에서 가져옴
            int creditsToAdd = 100; // 추가할 크레딧 수

            Map<String, Object> response = new HashMap<>();

            // 현재 크레딧을 DB에서 가져오기
            Integer currentCredit = dao.getCreditById(customerId);
            if (currentCredit == null) {
                currentCredit = 0; // 기본값으로 0 설정
            }

            // 크레딧 업데이트
            Integer updatedCredit = currentCredit + creditsToAdd;
            dao.updateCredit(customerId, updatedCredit); // DB 업데이트

            response.put("success", true);
            return ResponseEntity.ok(response);
        }
        
        

}
