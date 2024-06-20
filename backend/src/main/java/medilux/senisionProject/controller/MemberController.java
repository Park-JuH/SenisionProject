package medilux.senisionProject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import medilux.senisionProject.domain.Member;
import medilux.senisionProject.domain.Nutrient;
import medilux.senisionProject.domain.dto.*;
import medilux.senisionProject.repository.AccessRepository;
import medilux.senisionProject.repository.MemberRepository;
import medilux.senisionProject.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AccessRepository accessRepository;
    @PostMapping("/login")
    public ResponseEntity<MemberResponseDTO> login(@RequestParam String phone, HttpServletResponse response) {
        LoginDTO loginResponse = memberService.loadUserByPhone(phone);
        if (loginResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String accessToken = loginResponse.getAccess();
        response.addHeader("Authorization", "Bearer " + accessToken);
        return ResponseEntity.ok(loginResponse.getMemberResponseDto());
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDTO joinDTO) {

        System.out.println(joinDTO.getPhone());
        Long newId = memberService.joinProcess(joinDTO);

        if (newId == null) {
            System.out.println("Failed to register user.");
            return ResponseEntity.badRequest().body("Registration failed");
        }

        return ResponseEntity.ok("User registered successfully with ID: " + newId);
    }
    @PostMapping("/api/member")
    public ResponseEntity<?> saveInfo(HttpServletRequest request, @RequestBody MemberRequestDTO memberRequestDTO){
        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];
        String phone = accessRepository.findByAccess(accessToken).getPhone();
        Member member = memberRepository.findByPhone(phone);

        try{
            MemberResponseDTO res = memberService.saveMemberInfo(member, memberRequestDTO);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/api/member")
    public ResponseEntity<?> updateInfo(HttpServletRequest request, @RequestBody MemberRequestDTO memberRequestDTO){
        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];
        String phone = accessRepository.findByAccess(accessToken).getPhone();
        Member member = memberRepository.findByPhone(phone);

        try{
            MemberResponseDTO res = memberService.saveMemberInfo(member, memberRequestDTO);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/api/member")
    public ResponseEntity<?> getInfo(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];
        String phone = accessRepository.findByAccess(accessToken).getPhone();
        Member member = memberRepository.findByPhone(phone);

        try{
            MemberResponseDTO res = memberService.findMemberByToken(member);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/nutrient")
    public ResponseEntity<?> getNutrientInfo(@RequestParam Long userId) {
        Optional<Member> memberOptional = memberRepository.findById(userId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            try {
                Nutrient nutrient = member.getNutrient();
                NutrientDTO res = NutrientDTO.fromEntity(nutrient);
                return ResponseEntity.ok(res);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            // 회원 정보로 로직 처리
        } else {
            return new ResponseEntity<>("Invalid member Id in DB", HttpStatus.BAD_REQUEST);
            // 회원 정보가 없는 경우의 로직 처리
        }
    }

    @PostMapping("/api/nutrient")
    public ResponseEntity<?> updateNutrientInfo(@RequestParam Long userId, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            }

            // Process the file here. For example, saving the file to the server.
            NutrientDTO updateNutrient = memberService.saveNutrientInfo(userId, file);

            // You might want to return the URL of the saved file or some confirmation message
            System.out.println("updateNutrient = " + updateNutrient.getProtein());
            return ResponseEntity.ok(updateNutrient);
        } catch (Exception e) {
            // It's a good practice to log the exception here
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }

    }
}
