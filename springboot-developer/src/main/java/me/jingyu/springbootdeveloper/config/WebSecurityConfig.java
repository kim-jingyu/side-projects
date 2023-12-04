//package me.jingyu.springbootdeveloper.config;
//
//import lombok.RequiredArgsConstructor;
//import me.jingyu.springbootdeveloper.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//    private final UserDetailService userService;
//
//    /** 스프링 시큐리티 기능 비활성화
//     * 인증, 인가 서비스를 모든 곳에 모두 적용하지 않는다.
//    */
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
//    /** 특정 http 요청에 대한 웹 기반 보안 구성
//     * 인증/인가 및 로그인, 로그아웃 관련 설정
//     * authorizeHttpRequests(): 특정 경로에 대한 액세스 설정
//     * requestMatchers(): 특정 요청과 일치하는 url에 대한 액세스를 설정
//     * permitAll(): 누구나 접근 가능. 즉, /login, /signup, /user로 요청이 오면 인증/인가 없이도 접근할 수 있음.
//     * anyRequest(): 위에서 설정한 url 이외의 요청에 대해 설정
//     * authenticated(): 별도의 인가는 필요하지 않지만, 인증이 접근할 수 있음.
//     * invalidateHttpSession(): 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정
//    */
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests()    // 인증, 인가 설정
//                .requestMatchers("/login", "/signup", "/user").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()    // 폼 기반 로그인 설정
//                .loginPage("/login")
//                .defaultSuccessUrl("/articles")
//                .and()
//                .logout() // 로그아웃 설정
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
//                .and()
//                .csrf().disable()
//                .build();
//    }
//
//    /** 인증 관리자 관련 설정
//     * 사용자 정보를 가져올 서비스를 재정의하거나, 인증 방법. 예를 들어, LDAP, JDBC 기반 인증 등을 설정할때 사용
//     * userDetailsService(): 사용자 정보를 가져올 서비스를 설정한다. 이때 설정하는 서비스 클래스는 반드시 UserDetailService를 상속받은 클래스여야 한다.
//     * passwordEncoder(): 비밀번호를 암호화하기 위한 Encoder를 설정한다.
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userService)
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
