package medilux.senisionProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/hello").permitAll()
//                .anyRequest().authenticated();
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig.filterChain");
        http
                .csrf().disable() // Disabling CSRF for simplicity (consider enabling in production)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login").permitAll() // Allow public access to homepage
                                .anyRequest().authenticated()) // All other requests require authentication
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/") // Redirect to Google login when attempting to access a secured page
                                .defaultSuccessUrl("/mainPage", true) // Redirect to /mainPage upon successful login
                                .failureUrl("/?error=true")) // Redirect to a custom error page in case of login failure
                .logout(logout ->
                        logout.logoutSuccessUrl("/?logout=true").permitAll()); // Custom logout behavior

        System.out.println("http = " + http);
        return http.build();
    }
}
