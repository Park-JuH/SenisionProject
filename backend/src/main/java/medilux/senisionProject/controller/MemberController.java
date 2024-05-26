package medilux.senisionProject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import medilux.senisionProject.domain.AccessEntity;
import medilux.senisionProject.domain.Member;
import medilux.senisionProject.domain.dto.JoinDTO;
import medilux.senisionProject.domain.dto.LoginDTO;
import medilux.senisionProject.domain.dto.MemberRequestDTO;
import medilux.senisionProject.domain.dto.MemberResponseDTO;
import medilux.senisionProject.repository.AccessRepository;
import medilux.senisionProject.repository.MemberRepository;
import medilux.senisionProject.service.MemberService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        System.out.println(joinDTO.getUsername());
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
}
