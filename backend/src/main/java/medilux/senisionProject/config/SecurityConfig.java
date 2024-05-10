package medilux.senisionProject.config;

import lombok.RequiredArgsConstructor;
import medilux.senisionProject.repository.MemberRepository;
import medilux.senisionProject.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

//system 출력 안 될시 @Component 했는지 점검
@Component
@EnableWebSecurity()
@RequiredArgsConstructor
public class SecurityConfig {

    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class.getName());

    @Autowired
    MemberRepository memberRepository;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig.filterChain");
        http
                .csrf().disable() // Disabling CSRF for simplicity (consider enabling in production)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/**").permitAll() // Allow public access to homepage
                                .anyRequest().authenticated()) // All other requests require authentication
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login") // Redirect to Google login when attempting to access a secured page
                                .userInfoEndpoint().userService(this.oauthUserService()) // 사용자 정보를 가져오는 서비스 설정/
                                .and()
                                .defaultSuccessUrl("/mainPage", true) // Redirect to /mainPage upon successful login
                                .failureUrl("/?error=true")) // Redirect to a custom error page in case of login failure
                .logout(logout ->
                        logout.logoutSuccessUrl("/?logout=true").permitAll()); // Custom logout behavior

        System.out.println("http = " + http.toString());
//        LOGGER.info("Configured HttpSecurity: " + http.toString());  // Log the HttpSecurity configuration
        return http.build();
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService() {
        return new CustomOAuth2UserService(memberRepository);
    }
}
