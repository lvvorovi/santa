package Secret.Santa.Secret.Santa.confs;

import Secret.Santa.Secret.Santa.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF is typically disabled for REST APIs
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/api/v1/groups/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/groups/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/groups/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/groups/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/gifts/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/gifts/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/gifts/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/gifts/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/generate-santa/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/generate-santa/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/generate-santa/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable() // Disable form login
                .logout().disable(); // Disable logout

        return http.build();
    }
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
