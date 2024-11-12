package kopo.delivery.dto;

import kopo.delivery.entity.PaymentComplete;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCompleteDTO {

    private Long orderId;
    private Long userId;
    private String impUid;
    private String merchantUid;
    private Long amount;
    private String status;
    private String paymentMethod;
    private LocalDateTime paidAt;
    private String buyerName;

    public PaymentComplete toEntity() {
        PaymentComplete paymentComplete = new PaymentComplete();
        paymentComplete.setOrderId(this.orderId);
        paymentComplete.setUserId(this.userId);
        paymentComplete.setImpUid(this.impUid);
        paymentComplete.setMerchantUid(this.merchantUid);
        paymentComplete.setAmount(this.amount);
        paymentComplete.setStatus(this.status);
        paymentComplete.setPaymentMethod(this.paymentMethod);
        paymentComplete.setPaidAt(this.paidAt);
        paymentComplete.setBuyerName(this.buyerName);
        return paymentComplete;
    }
}
