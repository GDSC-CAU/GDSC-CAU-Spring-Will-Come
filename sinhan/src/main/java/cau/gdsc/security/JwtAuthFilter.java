package cau.gdsc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// OncePerRequestFilter: 요청이 들어올 때마다 필터링을 수행
// 이 필터는 회원가입이나 로그인 요청에서 동작하지 않는다. (SecurityConfig 참조)
// 이 필터는 이미 가입한 사용자가 인증이 요구되는 엔드포인트에 진입했을때 JWT 토큰 인증을 수행한다. 즉, email과 만료기간을 검증한다.
// 따라서, UsernamePasswordAuthenticationToken에 비밀번호가 필요하지 않다.
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    // 요청 헤더의 Authorization 필드에 담긴 JWT 토근을 추출하여 검증
    // 검증 방식: UserDetailsService를 통해 DB에서 사용자 정보를 가져온 후, JWT 토큰의 유효성을 검증
    // 검증에 성공하면 SecurityContext에 인증 정보를 저장, authenticated 상태로 만듦
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, // 요청 헤더
            HttpServletResponse response, // 응답 헤더
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String email;
        final String token;

        // 요청 헤더에 Authorization 필드가 없거나, Bearer로 시작하지 않으면 필터링을 수행하지 않음
        // jwt 토큰 형식이 아니면 다음 필터로 넘어가게된다.
        // SecurityConfig의 체인에 의해 인증 정보가 authenticated 상태여야 요청이 처리된다.(즉, DispatcherServlet이 호출된다.)
        // 요청 헤더 인증 정보가 jwt 토큰 형식이 아니면 결국 요청 거부된다.(AccessDeniedHandler)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // 다음 필터로 넘어감
            return;
        }

        token = authHeader.substring(7);
        email = jwtUtils.extractUsername(token);

        // 아직 '인증'되지 않았을 때 토큰 검증 후 SecurityContext에 인증 정보를 저장
        // SecurityContext는 요청이 응답될 때까지 유지되는 인증 정보 저장소, 즉, 매 요청마다 초기화된다.
        // SecurityContextHolder는 각 쓰레드(요청)에서 관리하는 SecurityContext를 담고 있는 저장소이다.
        if (email != null || SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email); // DB에서 사용자 정보를 가져옴
            // 회원의 JWT 토큰이 유효하면 SecurityContext에 인증 정보를 저장
            if (jwtUtils.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, // Principal: 인증된 사용자 객체
                        null, // Credentials: 인증된 사용자의 비밀번호, 이미 회원가입때 인증됐기 때문에 필요 없음
                        userDetails.getAuthorities() // Authorities: 인증된 사용자의 권한. Authorities가 null이 아니면 인증된 사용자로 간주
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                ); // 토큰에 세부 정보 기록
                SecurityContextHolder.getContext().setAuthentication(authToken); // SecurityContext에 인증 정보 저장
            }
        }
        filterChain.doFilter(request, response);
    }
}
