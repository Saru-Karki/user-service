package com.dokotech.platform.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Tag(name = "OAuth2", description = "OAuth2 authentication endpoints")
public class OAuth2Controller {

    @GetMapping("/login-urls")
    @Operation(summary = "Get OAuth2 login URLs", description = "Returns OAuth2 login URLs for all configured providers")
    public ResponseEntity<Map<String, String>> getOAuth2LoginUrls() {
        Map<String, String> loginUrls = new HashMap<>();
        loginUrls.put("google", "/oauth2/authorization/google");
        loginUrls.put("facebook", "/oauth2/authorization/facebook");
        loginUrls.put("apple", "/oauth2/authorization/apple");

        return ResponseEntity.ok(loginUrls);
    }
}

