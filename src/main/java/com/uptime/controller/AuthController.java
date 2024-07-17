package com.uptime.controller;

import com.uptime.dto.AuthRequest;
import com.uptime.dto.JwtResponse;
import com.uptime.dto.MessageResponse;
import com.uptime.dto.SignupRequest;
import com.uptime.exception.UserExistsException;
import com.uptime.exception.UserNotVerified;
import com.uptime.service.UserDetailsServiceImpl;
import com.uptime.service.VerificationTokenService;
import com.uptime.util.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/v1")
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
//        String port = environment.getProperty("server.port");
//
//        // Local address
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
//        String hostName = InetAddress.getLocalHost().getHostName();
//
//        // Remote address
//        String remoteHostAddress = InetAddress.getLoopbackAddress().getHostAddress();
//        String remoteHostName = InetAddress.getLoopbackAddress().getHostName();
//        log.info("{}-{}-{}-{}-{}",remoteHostName);
        return "You are authenticated";
    }

    @PostMapping("/login")
    public JwtResponse signIn(@RequestBody AuthRequest authRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
            return new JwtResponse(JwtUtil.GenerateToken(authRequest.email()));
        }
        catch(DisabledException disabledException){
            log.debug("User is not verified");
            throw new UserNotVerified();
        }
        catch (Exception exception) {
            log.debug("Encountered exception ",exception);
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @GetMapping("/verify")
    public RedirectView verifyUser(@RequestParam String token){
        verificationTokenService.completeSignup(token);
        return new RedirectView("https://www.google.com/");
    }

    @PostMapping("/signup")
    public MessageResponse signup(@RequestBody SignupRequest request) throws UserExistsException, MessagingException {
        userDetailsService.registerUser(request);
        return new MessageResponse("User registered successfully");
    }

}
