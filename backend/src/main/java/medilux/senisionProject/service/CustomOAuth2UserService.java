package medilux.senisionProject.service;

import medilux.senisionProject.domain.Member;
import medilux.senisionProject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    public CustomOAuth2UserService() {
////        this.memberRepository = memberRepository;
////    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return processOAuth2User(user);
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        // Extract user details from oAuth2User
        String email = oAuth2User.getAttribute("email");
        // Check if user already exists
        Member existingUser = memberRepository.findByEmail(email);
        if (existingUser == null) {
            // Register new user
            Member newMember = new Member();
            newMember.setEmail(email);
            newMember.setAge(26);
            newMember.setName(oAuth2User.getAttribute("name"));
            memberRepository.save(newMember);
        }
        return oAuth2User;
    }
}