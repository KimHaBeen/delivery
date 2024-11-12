package kopo.delivery.kakaoAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KakaoAPI {

    @Value("${kakao.client-id}")
    private String kakaoApiKey;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    public Object getKakaoApiKey() {
        return kakaoApiKey;
    }

    public Object getKakaoRedirectUri() {
        return kakaoRedirectUri;
    }

    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqUrl = "https://kauth.kakao.com/oauth/token";


        return accessToken;
    }

    public Map<String, Object> getUserInfo(String accessToken) {
        return null;
    }
}
