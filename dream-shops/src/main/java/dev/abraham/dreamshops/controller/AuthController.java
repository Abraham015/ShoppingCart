package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.request.login.LoginRequest;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.response.JwtResponse;
import dev.abraham.dreamshops.security.jwt.JwtUtils;
import dev.abraham.dreamshops.security.user.ShopUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt=jwtUtils.generateTokenforUser(authentication);
            ShopUserDetail user=(ShopUserDetail)authentication.getPrincipal();
            JwtResponse userDetails=new JwtResponse(user.getId(), jwt);
            return ResponseEntity.ok(new APIResponse("Login success",userDetails));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse(e.getMessage(), null));
        }
    }
}
