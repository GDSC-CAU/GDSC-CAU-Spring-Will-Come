package cau.gdsc.config.security;

import cau.gdsc.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// OncePerRequestFilter: 요청이 들어올 때마다 필터링을 수행
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

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
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // 다음 필터로 넘어감
            return;
        }

        token = authHeader.substring(7);
        email = jwtService.extractUsername(token);

        if (email != null || SecurityContextHolder.getContext().getAuthentication() == null) {
        }
    }
}
