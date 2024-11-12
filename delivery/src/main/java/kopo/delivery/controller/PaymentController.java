package kopo.delivery.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kopo.delivery.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${payment.imp_code}")
    private String impCode;

    @ResponseBody
    @GetMapping("/api/imp-code")
    public Map<String, String> getImpUid() {
        Map<String, String> response = new HashMap<>();
        response.put("imp_Code", impCode);
        return response;
    }

    @ResponseBody
    @PostMapping("/payment/complete")
    public Map<String, String> paymentComplete(HttpSession session, @RequestBody Map<String, Object> paymentData) throws IOException {
        String impUid = (String) paymentData.get("imp_uid");
        int amount = (int) paymentData.get("paid_amount");

        Map<String, String> response = new HashMap<>();

        String address = (String) session.getAttribute("address");
        if (address == null) {
            response.put("error", "주소를 선택해주세요");
            return response;
        }
        try {
            boolean isPaymentValid = paymentService.verifyPayment(impUid, amount);

            if (isPaymentValid) {
                System.out.println("검증완료 > redirect");
                response.put("redirectUrl", "/payment/orderComplete");
            } else {
                throw new IllegalStateException("결제 검증에 실패했습니다.");
            }
        } catch (Exception e) {
            System.err.println("결제 정보 검증 중 오류 발생: " + e.getMessage());
            response.put("redirectUrl", "/error"); // 오류 발생 시 오류 페이지로 리다이렉트
        }
        return response;
    }


    @GetMapping("/payment/orderComplete")
    public String orderComplete() {
        return "order/orderComplete";
    }

    @GetMapping("/check-address")
    @ResponseBody
    public Map<String, Object> checkAddress(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String address = (String) session.getAttribute("address");

        if (address != null) {
            response.put("haveAddress", true);
        } else {
            response.put("haveAddress", false);
            response.put("message", "주소가 선택되지 않았습니다. 결제를 진행하려면 주소를 선택하세요.");
        }

        return response;
    }
}
