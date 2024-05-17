package medilux.senisionProject.service;

import medilux.senisionProject.domain.Member;
import medilux.senisionProject.dto.JoinDTO;
import medilux.senisionProject.repository.MemberRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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
}
