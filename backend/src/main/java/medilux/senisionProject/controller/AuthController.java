package medilux.senisionProject.controller;

import medilux.senisionProject.dto.JoinDTO;
import medilux.senisionProject.service.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
public class AuthController {

    private final SmsService smsService;

    public AuthController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/make")
    @ResponseBody
    public ResponseEntity<String> makeVerification(@RequestParam String phone) {


        if (phone == null || phone.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Phone number is required.");
        }

        try {
            ResponseEntity<String> response = smsService.sendSmsToPhone(phone);
            return ResponseEntity.ok("Send Verification Number Success: " + response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/verification")
    @ResponseBody
    public ResponseEntity<String> checkVerification(@RequestParam String phone, @RequestParam String inputCode) {

        if (!smsService.validateCode(phone, inputCode)) {
            return ResponseEntity.badRequest().body("VERIFICATION FAILED");
        }

        return ResponseEntity.ok("MATCH SUCCESS" );
    }
}
