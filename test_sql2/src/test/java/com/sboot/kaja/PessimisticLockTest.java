package com.sboot.kaja;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@SpringBootTest
public class PessimisticLockTest {

    @Autowired
    private CreditService creditService;

    @Test
    public void testPessimisticLock() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            creditService.useCredits("C001", 100);
        });

        Thread thread2 = new Thread(() -> {
            creditService.cancelTransaction("C001", 100, Timestamp.from(Instant.now()));
        });

        thread1.start();
        Thread.sleep(500); // thread1이 잠금을 잡고 있는 동안 지연
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
