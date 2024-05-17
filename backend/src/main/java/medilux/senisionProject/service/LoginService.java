package medilux.senisionProject.service;

import medilux.senisionProject.domain.AccessEntity;
import medilux.senisionProject.domain.Member;
import medilux.senisionProject.dto.LoginDTO;
import medilux.senisionProject.dto.MemberDTO;
import medilux.senisionProject.jwt.JWTUtil;
import medilux.senisionProject.repository.AccessRepository;
import medilux.senisionProject.repository.MemberRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final AccessRepository accessRepository;

    public LoginService(MemberRepository memberRepository, JWTUtil jwtUtil, AccessRepository accessRepository) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.accessRepository = accessRepository;
    }

//    @Override
    public LoginDTO loadUserByPhone(String phone) {

        Member member = memberRepository.findByPhone(phone);
        if (member == null) {
            return null; // Or throw an appropriate exception.
        }

        MemberDTO memberDto = new MemberDTO();
        memberDto.setUsername(member.getUsername());
        memberDto.setId(member.getId());
        memberDto.setPhone(member.getPhone());
        memberDto.setRole(member.getRole());

//        AccessEntity accessEntity = accessRepository.findByPhone(phone);

//        if (accessEntity != null) {
//            boolean isTokenExpired = jwtUtil.isExpired(accessEntity.getAccess());
//            if (!isTokenExpired) {
//                return new LoginDTO(memberDto, accessEntity.getAccess());
//            } else {
//                System.out.println("Token expired, generating a new one.");
//            }
//        }

        String newAccess = jwtUtil.createJwt(member.getPhone(), member.getRole(), 60 * 60 * 10L); // Ensure milliseconds are correctly calculated.
        Date expirationDate = new Date(System.currentTimeMillis() + (60 * 60 * 10L));

        AccessEntity accessEntity = new AccessEntity(); // Create new if doesn't exist.
        accessEntity.setPhone(phone);
        accessEntity.setAccess(newAccess);
        accessEntity.setExpiration(expirationDate.toString());
        accessRepository.save(accessEntity);

        return new LoginDTO(memberDto, newAccess);
    }
}
