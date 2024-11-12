package kopo.delivery.controller;

import jakarta.servlet.http.HttpSession;
import kopo.delivery.dto.UserDTO;
import kopo.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        // 카카오 로그인 URL 생성
        String kakaoLoginUrl = "/oauth2/authorization/kakao";

        // 카카오 로그인 URL을 모델에 추가하여 뷰에서 사용
        model.addAttribute("kakaoLoginUrl", kakaoLoginUrl);

        return "user/login";
    }

   /* 검증부분은 UserDetailsService에 넘김.
   @PostMapping("/login/complete")
    @ResponseBody
    public Map<String, Object> loginComplete(@RequestBody UserDTO userDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        List<UserDTO> result = userService.loginCompleteProcess(userDTO);
        System.out.println("로그인 검증 중");

        if (result!=null && !result.isEmpty()) {
            response.put("status", "success");
            response.put("response", result);
            session.setAttribute("loginuser",result);
            System.out.println("로그인 검증 완료");
        }else {
            response.put("status", "fail");
            response.put("message", "비밀번호나 아이디가 일치하지 않습니다.");
        }

        return response;
    }*/

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

    /*@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }*/

    @GetMapping("/oauth/kakao")
    public String redirectToKakaoLogin() {
        return "redirect:/oauth2/authorization/kakao";
    }

    @GetMapping("/isLoggedIn")
    @ResponseBody
    public Map<String, Object> isLoggedIn(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Object userId = session.getAttribute("userID");
        response.put("loggedIn", userId != null);  // 로그인 여부 확인
        return response;
    }

}
