package kopo.delivery.controller;

import jakarta.servlet.http.HttpSession;
import kopo.delivery.dto.UserDTO;
import kopo.delivery.dto.kakaodto.KakaoUserInfoResponseDTO;
import kopo.delivery.service.KakaoService;
import kopo.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @GetMapping("/login")
    public String login(Model model) {
        // 카카오 로그인 URL 생성
        String kakaoLoginUrl = "/oauth2/authorization/kakao";

        // 카카오 로그인 URL을 모델에 추가하여 뷰에서 사용
        model.addAttribute("kakaoLoginUrl", kakaoLoginUrl);

        return "user/login";
    }

    @PostMapping("/login/complete")
    @ResponseBody
    public Map<String, Object> loginComplete(@RequestBody UserDTO userDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        List<UserDTO> result = userService.loginCompleteProcess(userDTO);

        if (result!=null && !result.isEmpty()) {
            response.put("status", "success");
            response.put("response", result);
            session.setAttribute("loginuser",result);
        }else {
            response.put("status", "fail");
            response.put("message", "비밀번호나 아이디가 일치하지 않습니다.");
        }

        return response;
    }

    @GetMapping("/signup")
    public String signup() {

        return "user/signup";
    }

    @PostMapping("/signup/complete")
    public String signComplete(UserDTO userDTO) {
        System.out.println(userDTO.getUserName());
        userService.signUpComplete(userDTO);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);

        KakaoUserInfoResponseDTO userInfo = kakaoService.getUserInfo(accessToken);

        // User 로그인, 또는 회원가입 로직 추가
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
