package com.redhands.api_gateway.controller;

import com.redhands.api_gateway.dto.LoginRequest;
import com.redhands.api_gateway.dto.LoginResponse;
import com.redhands.api_gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 간단한 하드코딩 인증 (실제로는 DB 조회)
        if ("admin".equals(loginRequest.getUsername()) &&
                "password".equals(loginRequest.getPassword())) {

            // JWT 토큰 생성
            String token = jwtUtil.generateToken("admin", "ADMIN");

            return ResponseEntity.ok(new LoginResponse(token, "로그인 성공"));
        } else if ("user".equals(loginRequest.getUsername()) &&
                "password".equals(loginRequest.getPassword())) {

            String token = jwtUtil.generateToken("user", "USER");

            return ResponseEntity.ok(new LoginResponse(token, "로그인 성공"));
        }

        return ResponseEntity.status(401).body("로그인 실패: 잘못된 사용자명 또는 비밀번호");
    }
}