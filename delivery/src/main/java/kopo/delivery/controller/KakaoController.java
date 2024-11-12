package kopo.delivery.controller;

import kopo.delivery.kakaoAPI.KakaoAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class KakaoController {

    private final KakaoAPI kakaoAPI;

    @GetMapping("/kakao/login")
    public RedirectView loginForm(Model model){
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoAPI.getKakaoApiKey()
                + "&redirect_uri=" + kakaoAPI.getKakaoRedirectUri() + "&response_type=code";

        model.addAttribute("kakaoAuthUrl", kakaoAuthUrl);
        return new RedirectView(kakaoAuthUrl);
    }

    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
