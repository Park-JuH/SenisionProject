package medilux.senisionProject.service;

import lombok.RequiredArgsConstructor;
import medilux.senisionProject.domain.AccessEntity;
import medilux.senisionProject.domain.Member;
import medilux.senisionProject.domain.dto.JoinDTO;
import medilux.senisionProject.domain.dto.LoginDTO;
import medilux.senisionProject.domain.dto.MemberRequestDTO;
import medilux.senisionProject.domain.dto.MemberResponseDTO;
import medilux.senisionProject.jwt.JWTUtil;
import medilux.senisionProject.repository.AccessRepository;
import medilux.senisionProject.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final AccessRepository accessRepository;
    public Long joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String phone = joinDTO.getPhone();

        Boolean isExist = memberRepository.existsByPhone(phone);

        if (isExist) {
            return null;
        }

        Member newMember = new Member();

        newMember.setUsername(username);
        newMember.setPhone(phone);
        newMember.setRole("ROLE_USER");
        newMember.setAge(20);

        memberRepository.save(newMember);

        return newMember.getId();
    }
    public LoginDTO loadUserByPhone(String phone) {

        Member member = memberRepository.findByPhone(phone);
        if (member == null) {
            return null; // Or throw an appropriate exception.
        }

        String newAccess = jwtUtil.createJwt(member.getPhone(), member.getRole(), 60 * 60 * 10L); // Ensure milliseconds are correctly calculated.
        Date expirationDate = new Date(System.currentTimeMillis() + (60 * 60 * 10L));

        AccessEntity accessEntity = new AccessEntity(); // Create new if doesn't exist.
        accessEntity.setPhone(phone);
        accessEntity.setAccess(newAccess);
        accessEntity.setExpiration(expirationDate.toString());
        accessRepository.save(accessEntity);

        return new LoginDTO(MemberResponseDTO.from(member), newAccess);
    }

    public MemberResponseDTO saveMemberInfo(Member member, MemberRequestDTO memberRequestDTO){
        member.setAge(memberRequestDTO.getAge());
        member.setBreakfast(memberRequestDTO.getBreakfast());
        member.setLunch(memberRequestDTO.getLunch());
        member.setDinner(memberRequestDTO.getDinner());
        member.setWakeup(memberRequestDTO.getWakeup());
        member.setSleep(memberRequestDTO.getSleep());
        member.setBodyPart(memberRequestDTO.getBodyPart());

        return MemberResponseDTO.from(member);
    }

    public MemberResponseDTO findMemberByToken(Member member){
        return MemberResponseDTO.from(member);
    }

}
