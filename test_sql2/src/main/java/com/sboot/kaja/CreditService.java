package com.sboot.kaja;

import com.sboot.kaja.dao.SawonDAO;
import com.sboot.kaja.vo.CreditSearchVO;
import com.sboot.kaja.vo.CreditTransactionVO;
import com.sboot.kaja.vo.SawonVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class CreditService {

    @Autowired
    private SawonDAO dao; // DAO 주입

    // 모든 거래 내역을 조회하는 메서드
    public List<CreditTransactionVO> getAllTransactions() {
        return dao.getAllTransactions();
    }

	/*
	 * public List<CreditTransactionVO> getTransactionsWithFilters(Map<String,
	 * Object> filters) { // TODO Auto-generated method stub return
	 * dao.getTransactionsWithFilters(filters); }
	 */
	
    public List<CreditTransactionVO> getTransactionsWithFilters(Map<String, Object> filters) {
        // DAO 호출하여 거래 내역을 가져옴
        List<CreditTransactionVO> transactions = dao.getTransactionsWithFilters(filters);

        // 필터 값 로그 출력
        System.out.println("Filters: " + filters);

        // 반환된 데이터를 로그로 출력
        for (CreditTransactionVO transaction : transactions) {
            System.out.println(transaction); // toString() 메서드를 호출하여 객체의 정보를 출력
        }
        
        return transactions;
    }
    
    @Transactional(isolation = Isolation.SERIALIZABLE)  // 트랜잭션 관리
    public void cancelTransaction(String customerId, Integer usedCredits, Timestamp transactionDate) {
    try {	
    	System.out.println("cancelTransaction 트랜잭션 시작 - 고객 ID: " + customerId + ", 취소 크레딧: " + usedCredits);
    	
    	 // 트랜잭션 활성화 여부 확인
        boolean isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println("Cancel Transaction - 트랜잭션 활성 상태: " + isTransactionActive);
    	
        // 비관적 잠금을 통해 고객의 크레딧 정보를 가져옵니다.
        SawonVO sawon = dao.getCreditForUpdate(customerId);
        System.out.println("1");
        
        // 현재 크레딧 가져오기
        Integer currentCredit = sawon.getCredit(); 
        System.out.println("2");
        
        // 크레딧 업데이트 계산
        Integer updatedCredit = currentCredit + usedCredits; 
        System.out.println("3");
        
        // 크레딧 업데이트
        dao.updateCredit(customerId, updatedCredit); // 기존 메서드를 호출
        System.out.println("4");
        
        System.out.println(customerId);
        // 거래 취소 내역에 삽입
        dao.cancelinput(customerId, transactionDate, usedCredits);
        System.out.println("5");

        // 원래 구매 내역에서 삭제
        dao.deletecancel(customerId, transactionDate);
        System.out.println("6");
        
        System.out.println("cancelTransaction 트랜잭션 종료 - 고객 ID: " + customerId);
    }catch (Exception e) {
    	System.err.println("cancelTransaction 예외 발생: " + e.getMessage());
        e.printStackTrace();
    }
        
    }
    
    
    
    @Transactional(isolation = Isolation.SERIALIZABLE)  // 트랜잭션 관리
    public ResponseEntity<Map<String, Object>> useCredits(String customerId, Integer usedCredits) {
        Map<String, Object> response = new HashMap<>();
        
    try {   
        System.out.println("useCredits 트랜잭션 시작 - 고객 ID: " + customerId + ", 사용 크레딧: " + usedCredits);
        
        // 트랜잭션 활성화 여부 확인
        boolean isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println("Use Credits - 트랜잭션 활성 상태: " + isTransactionActive);

        
        // 비관적 잠금을 통해 고객의 크레딧 정보를 가져옵니다.
        SawonVO sawon = dao.getCreditForUpdate(customerId);

        
        // 현재 크레딧 가져오기
        Integer currentCredit = sawon.getCredit();

        // 사용자의 크레딧이 충분한지 확인
        if (currentCredit < usedCredits) {
            response.put("success", false);
            response.put("message", "사용 가능한 크레딧을 초과했습니다.");
            return ResponseEntity.ok(response); // 실패 시 메시지 반환
        }

        // 크레딧 업데이트 계산 (차감)
        Integer updatedCredit = currentCredit - usedCredits;

        // 크레딧 업데이트
        dao.updateCredit(customerId, updatedCredit);

        // VO 객체 생성 후 사용
        CreditSearchVO transaction = new CreditSearchVO(
            customerId,
            usedCredits,
            "결제",
            "상품 구매",
            Timestamp.from(Instant.now())
        );

        // VO를 통해 거래 내역 삽입
        dao.insertCreditTransaction(transaction);

        response.put("success", true);
        response.put("message", "결제가 완료되었습니다.");
        
        System.out.println("useCredits 트랜잭션 종료 - 고객 ID: " + customerId);
    } catch (Exception e) {
    	System.err.println("useCredits 예외 발생: " + e.getMessage());
    }
        
        return ResponseEntity.ok(response); // 성공 시 응답 반환
        
    }



}
