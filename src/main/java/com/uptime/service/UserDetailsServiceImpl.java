package com.uptime.service;

import com.uptime.config.SecurityConfig;
import com.uptime.dto.CustomUserDetails;
import com.uptime.dto.SignupRequest;
import com.uptime.exception.UserExistsException;
import com.uptime.model.UserInfo;
import com.uptime.model.VerificationToken;
import com.uptime.repository.UserInfoRepository;
import com.uptime.util.ServiceConstants;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserInfoRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    ExecutorService executorService;

    @Autowired
    VerificationTokenService verificationTokenService;



    PasswordEncoder passwordEncoder = SecurityConfig.passwordEncoder();

    public static UserInfo extractCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof CustomUserDetails userDetails){
            return userDetails.getUserInfo();
        }
        throw new UsernameNotFoundException("User not exists");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Entering in loadUserByUsername Method...");
        Optional<UserInfo> userInfo = userRepository.findByEmail(username);
        if (userInfo.isEmpty()) {
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException(ServiceConstants.USER_NOT_FOUND);
        }
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(userInfo.get());
    }

    public void registerUser(SignupRequest request) throws UserExistsException, MessagingException {

        Optional<UserInfo> userInfoOptional = userRepository.findByEmail(request.email());
        if (userInfoOptional.isPresent()) {
            throw new UserExistsException();
        }

        UserInfo userInfo = UserInfo.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .company(request.company())
                .build();
        userRepository.save(userInfo);
        VerificationToken verificationToken = verificationTokenService.createVerificationToken(userInfo);
        executorService.execute(() -> mailService.sendMail(userInfo,verificationToken));
    }



}
