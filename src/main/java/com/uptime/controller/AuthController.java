package com.uptime.controller;

import com.uptime.dto.*;
import com.uptime.exception.UserExistsException;
import com.uptime.exception.UserNotVerified;
import com.uptime.service.UserDetailsServiceImpl;
import com.uptime.service.VerificationTokenService;
import com.uptime.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.UnknownHostException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  VerificationTokenService verificationTokenService;

  @GetMapping("/ping")
  public String ping() throws UnknownHostException {
    return "You are authenticated";
  }

  @PostMapping("/login")
  public JwtResponse signIn(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
    try {
      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
      String accessToken = JwtUtil.GenerateToken(authRequest.email());
      CustomUserDetails details = (CustomUserDetails)userDetailsService.loadUserByUsername(authRequest.email());
      return new JwtResponse(accessToken,details.getUserInfo());
    } catch (DisabledException disabledException) {
      throw new UserNotVerified();
    }
  }

  @GetMapping("/verify")
  public RedirectView verifyUser(@RequestParam String token) {
    verificationTokenService.completeSignup(token);
    return new RedirectView("/");
  }

  @PostMapping("/signup")
  public MessageResponse signup(@RequestBody SignupRequest request) throws UserExistsException, MessagingException {
    userDetailsService.registerUser(request);
    return new MessageResponse("User registered successfully");
  }

}
