package com.example.noticeboardprj.jwt;

import com.example.noticeboardprj.dto.CustomUserDetails;
import com.example.noticeboardprj.entity.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 access키에 담긴 access토큰을 가져옴
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if(accessToken == null){

            filterChain.doFilter(request, response);

            return;
        }

        // Token 만료 여부 확인, 만료시 다음 필터로 넘기지 않음!!
        try{
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e){

            // response body
            PrintWriter witer = response.getWriter();

            // response status code (상태 코드)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setRole(role);
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);


        // ===================================================================
        // 단일토큰 검증 필터
//        // request에서 Authorization 헤더를 찾음
//        String authorization = request.getHeader("Authorization");
//
//
//        // Authorization 헤더 검증
//        if(authorization == null || !authorization.startsWith("Bearer ")) {
//
//            System.out.println("token null");
//            filterChain.doFilter(request, response);
//
//            // 조건이 해당되면 메서드 종료
//            return;
//        }
//
//        System.out.println("authorization now");
//
//        // Bearer 부분 제거 후 순수 토큰만 획득
//        String token = authorization.split(" ")[1];
//        System.out.println(token);
//
//        // 토큰 소멸 시간 검증
//        if(jwtUtill.isExpired(token)){
//            System.out.println("token expired");
//            filterChain.doFilter(request, response);
//
//            // 조건이 해당되면 메소드 종료
//            return;
//        }
//
//        // 토큰에서 username과 role 획득
//        String username = jwtUtill.getUsername(token);
//        String role = jwtUtill.getRole(token);
//
//        // userEntity를 생성하여 값 set
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername(username);
//        userEntity.setPassword("temppassword");
//        userEntity.setRole(role);
//
//        // UserDetails에 회원 정보 객체 담기
//        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
//
//        // 스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//
//        // 세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        filterChain.doFilter(request, response);
    }
}
