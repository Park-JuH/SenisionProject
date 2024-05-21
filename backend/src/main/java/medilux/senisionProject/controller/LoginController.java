package medilux.senisionProject.controller;

import jakarta.servlet.http.HttpServletResponse;
import medilux.senisionProject.dto.LoginDTO;
import medilux.senisionProject.dto.MemberDTO;
import medilux.senisionProject.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<MemberDTO> login(@RequestParam String phone, HttpServletResponse response) {
        LoginDTO loginResponse = loginService.loadUserByPhone(phone);
        if (loginResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String accessToken = loginResponse.getAccess();
        response.addHeader("Authorization", "Bearer " + accessToken);
        return ResponseEntity.ok(loginResponse.getMemberDto());
    }
}
