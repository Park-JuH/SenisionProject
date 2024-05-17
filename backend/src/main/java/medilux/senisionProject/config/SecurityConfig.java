package medilux.senisionProject.config;

import lombok.RequiredArgsConstructor;
//import medilux.senisionProject.jwt.CustomLogoutFilter;
////import medilux.senisionProject.jwt.JWTFilter;
//import medilux.senisionProject.jwt.JWTUtil;
//import medilux.senisionProject.jwt.LoginFilter;
////import medilux.senisionProject.oauth2.CustomSuccessHandler;
//import medilux.senisionProject.repository.MemberRepository;
//import medilux.senisionProject.repository.RefreshRepository;
////import medilux.senisionProject.service.CustomOAuth2UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutFilter;
//import org.springframework.stereotype.Component;
//
//import java.util.logging.Logger;
//
////system 출력 안 될시 @Component 했는지 점검
//@Configuration
//@EnableWebSecurity()
//public class SecurityConfig {
//
////    private final CustomOAuth2UserService customOAuth2UserService;
////    private final CustomSuccessHandler customSuccessHandler;
////    private final JWTUtil jwtUtil;
//
////    private final RefreshRepository refreshRepository;
//
//    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
//    private final AuthenticationConfiguration authenticationConfiguration;
//
//    //JWTUtil 주입
//    private final JWTUtil jwtUtil;
//
//    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
//
//        this.authenticationConfiguration = authenticationConfiguration;
//        this.jwtUtil = jwtUtil;
//    }
//
//    //AuthenticationManager Bean 등록
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//
//        http
//                .csrf((auth) -> auth.disable());
//
//        http
//                .formLogin((auth) -> auth.disable());
//
//        http
//                .httpBasic((auth) -> auth.disable());
//
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/login", "/", "/join").permitAll()
//                        .anyRequest().authenticated());
//
//        //AuthenticationManager()와 JWTUtil 인수 전달
//        http
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//        http
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//
//        return http.build();
//    }

//    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil, RefreshRepository refreshRepository) {
//
//        this.customOAuth2UserService = customOAuth2UserService;
//        this.customSuccessHandler = customSuccessHandler;
//        this.jwtUtil = jwtUtil;
//        this.refreshRepository = refreshRepository;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        //csrf disable
//        http
//                .csrf((auth) -> auth.disable());
//
//        //From 로그인 방식 disable
//        http
//                .formLogin((auth) -> auth.disable());
//
//        //HTTP Basic 인증 방식 disable
//        http
//                .httpBasic((auth) -> auth.disable());
//
//        //JWTFilter 추가
//        http
//                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//        http
//                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
//
//
//
//        //oauth2
//        http
//                .oauth2Login((oauth2) -> oauth2
//                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
//                                .userService(customOAuth2UserService))
//                        .successHandler(customSuccessHandler)
//                );
//
//        //경로별 인가 작업
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/").permitAll()
////                        .requestMatchers("/mainPage").permitAll()
//                        .anyRequest().authenticated());
//
//        //세션 설정 : STATELESS
//        http
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }

//    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class.getName());
//
//    @Autowired
//    MemberRepository memberRepository;
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        System.out.println("SecurityConfig.filterChain");
//        http
//                .csrf().disable() // Disabling CSRF for simplicity (consider enabling in production)
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/**").permitAll() // Allow public access to homepage
//                                .anyRequest().authenticated()) // All other requests require authentication
//                .oauth2Login(oauth2Login ->
//                        oauth2Login
//                                .loginPage("/login") // Redirect to Google login when attempting to access a secured page
//                                .userInfoEndpoint().userService(this.oauthUserService()) // 사용자 정보를 가져오는 서비스 설정/
//                                .and()
//                                .defaultSuccessUrl("/mainPage", true) // Redirect to /mainPage upon successful login
//                                .failureUrl("/?error=true")) // Redirect to a custom error page in case of login failure
//                .logout(logout ->
//                        logout.logoutSuccessUrl("/?logout=true").permitAll()); // Custom logout behavior
//
//        System.out.println("http = " + http.toString());
////        LOGGER.info("Configured HttpSecurity: " + http.toString());  // Log the HttpSecurity configuration
//        return http.build();
//    }
//
//    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService() {
//        return new CustomOAuth2UserService(memberRepository);
//    }

//}
