package kopo.delivery.service;

import jakarta.servlet.http.HttpSession;

public interface PaymentService {
    // 아임포트 API를 사용해 토큰 발급
    String getIamportToken();

    // 결제 정보를 조회하고 검증하는 메서드
    boolean verifyPayment(String impUid, int amount);
}
