package medilux.senisionProject.service;

import medilux.senisionProject.sms.SmsUtil;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {

    private final ConcurrentHashMap<String, CodeEntry> codeStorage = new ConcurrentHashMap<>();

    // Inner class to hold code and timestamp
    private static class CodeEntry {
        String code;
        long timestamp;

        public CodeEntry(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
    }

    private final SmsUtil smsUtil;

    public SmsService(SmsUtil smsUtil) {
        this.smsUtil = smsUtil;
    }

    public ResponseEntity<String> sendSmsToPhone(String phone) {

        String verificationCode = generateOTP();
        long timestamp = System.currentTimeMillis();
        codeStorage.put(phone, new CodeEntry(verificationCode, timestamp));

        try {
            SingleMessageSentResponse response = smsUtil.sendOne(phone, verificationCode);
//            if (response.getStatusCode().equals("200")) {
//                return ResponseEntity.ok("SMS sent successfully");
//            } else {
//                return ResponseEntity.status(500).body("Failed to send SMS");
//            }
            return ResponseEntity.ok("SMS sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending SMS: " + e.getMessage());
        }
    }

    // Validate the verification code
    public boolean validateCode(String phone, String inputCode) {
        CodeEntry entry = codeStorage.get(phone);
        if (entry != null && entry.code.equals(inputCode)) {
            // Check if the code has expired (5 minutes expiration)
            if (System.currentTimeMillis() - entry.timestamp < TimeUnit.MINUTES.toMillis(5)) {
                return true;
            }
        }
        return false;
    }


    // 6자리 인증번호 생성
    private String generateOTP() {
        Random random = new Random();
        int num = random.nextInt(999999);
        return String.format("%06d", num);
    }

}
