package com.example.personal_stretch_api.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // String authHeader = request.getHeader("Authorization");
        String token = resolveToken(request);
        // トークンが存在する場合のみ、認証処理を試みる
        if (token != null) {
            try {
                Claims claims = jwtUtil.parse(token).getBody();
                String username = claims.getSubject();
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) claims.get("roles", List.class);
                var auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        roles == null ? List.of() :
                                roles.stream().map(SimpleGrantedAuthority::new).toList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException e) {
                // トークンが無効な場合は、ログ出力などを行い、認証情報をクリアする
                // これにより、認証失敗として扱われる
                logger.warn("JWTトークンの検証に失敗しました", e);
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest req) {
       // Authorization ヘッダーの値を取得
        String bearerToken = req.getHeader("Authorization");

        // "Bearer " で始まっているか、かつ null や空でないかを確認
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // "Bearer " (7文字) 以降の文字列をトークンとして返す
            return bearerToken.substring(7);
        }
        return null;
    }
}


