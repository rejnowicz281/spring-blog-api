package com.springblog.springblogapi.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.springblog.springblogapi.config.JwtService;

@Service
public class AuthenticationService {
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationService(JwtService jwtService,
                        AuthenticationManager authenticationManager) {
                this.jwtService = jwtService;
                this.authenticationManager = authenticationManager;
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                null,
                                                request.getPassword()));

                var jwtToken = jwtService.generateToken();

                return new AuthenticationResponse(jwtToken);
        }
}
