package com.example.noticeboardprj.jwt;

import com.example.noticeboardprj.dto.mapper.CustomUserDetails;
import com.example.noticeboardprj.entity.RoleEntity;
import com.example.noticeboardprj.entity.UserEntity;
import com.example.noticeboardprj.repository.RoleRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RoleRepository roleRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JWTFilter - Request URI: {}", request.getRequestURI());

        // 헤더에서 access키에 담긴 access토큰을 가져옴
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            log.warn("JWTFilter - No access token found.");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 만료 여부 확인, 만료시 다음 필터로 넘기지 않음!!
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            log.error("JWTFilter - Access token expired.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("invalid access token");
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);
        if (!"access".equals(category)) {
            log.error("JWTFilter - Invalid token category.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("invalid access token");
            return;
        }

        // username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);
        log.info("JWTFilter - Username: {}, Role: {}", username, role);

        // 역할 이름으로 RoleEntity 조회
        Optional<RoleEntity> roleEntityOptional = roleRepository.findByName(role);
        if (roleEntityOptional.isEmpty()) {
            log.error("JWTFilter - Role not found: {}", role);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("invalid role");
            return;
        }

        RoleEntity roleEntity = roleEntityOptional.get();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setRole(Set.of(roleEntity));


        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);


    }
}
