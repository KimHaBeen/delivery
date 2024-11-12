package kopo.delivery.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.delivery.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${payment.api_key}")
    private String apiKey;

    @Value("${payment.secret_key}")
    private String apiSecret;

    private String accessToken;
    private LocalDateTime tokenExpiration;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 아임포트 API를 사용해 토큰 발급
    @Override
    public String getIamportToken() {
        if (accessToken != null && tokenExpiration != null && LocalDateTime.now().isBefore(tokenExpiration)) {
            System.out.println("캐시된 토큰 반환: " + accessToken);
            return accessToken; // 캐시된 토큰 반환
        }

        if (apiKey == null || apiSecret == null) {
            throw new IllegalArgumentException("API Key 또는 Secret이 설정되지 않았습니다.");
        }

        String url = "https://api.iamport.kr/users/getToken";
        Map<String, String> params = new HashMap<>();
        params.put("imp_key", apiKey);
        params.put("imp_secret", apiSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // 파라미터를 JSON으로 변환
            String json = objectMapper.writeValueAsString(params);
            System.out.println("요청 JSON: " + json);

            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            // API 요청
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            System.out.println("응답 상태 코드: " + response.getStatusCode());
            System.out.println("응답 본문: " + response.getBody());

            // 응답 데이터 처리
            Map<String, Object> responseData = (Map<String, Object>) response.getBody().get("response");
            if (responseData != null) {
                accessToken = (String) responseData.get("access_token");

                Integer expiresIn = (Integer) responseData.get("expires_in"); // 만료 시간(초 단위)
                if (expiresIn == null) {
                    System.out.println("테스트 모드에서 expires_in 값이 없습니다. 기본 유효 시간을 사용합니다.");
                    expiresIn = 3600; // 기본 유효 시간(예: 1시간)
                }
                tokenExpiration = LocalDateTime.now().plusSeconds(expiresIn);

                System.out.println("발급된 토큰: " + accessToken);
                System.out.println("토큰 만료 시간: " + tokenExpiration);
                return accessToken;
            } else {
                System.err.println("응답 데이터에서 'response' 키를 찾을 수 없습니다.");
                throw new IllegalStateException("응답에서 토큰을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("아임포트 API 요청 중 오류 발생");
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new RuntimeException("아임포트 API 요청 중 오류 발생", e);
        }
    }

    // 결제 정보를 조회하고 검증하는 메서드
    @Override
    public boolean verifyPayment(String impUid, int amount) {
        String token = getIamportToken();
        String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();

            Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
            Map<String, Object> responseData = (Map<String, Object>) responseMap.get("response");

            if (responseData == null) {
                throw new IllegalStateException("결제 정보가 없습니다.");
            }

            Integer paidAmount = (Integer) responseData.get("amount");
            String status = (String) responseData.get("status");

            return paidAmount != null && paidAmount.equals(amount) && "paid".equals(status);
        } catch (Exception e) {
            throw new RuntimeException("결제 정보 검증 중 오류 발생", e);
        }
    }
}
