package com.uptime.service;

import com.uptime.exception.VerificationLinkExpired;
import com.uptime.model.UserInfo;
import com.uptime.model.VerificationToken;
import com.uptime.repository.UserInfoRepository;
import com.uptime.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserInfoRepository userRepository;


    public VerificationToken createVerificationToken(UserInfo userInfo) {
        VerificationToken verificationToken = VerificationToken.builder()
                .userInfo(userInfo)
                .token(UUID.randomUUID().toString())
                .build();
        verificationToken.calculateExpiryDate();
        return verificationToken;
    }

    public void saveVerificationToken(VerificationToken token){
        verificationTokenRepository.save(token);
    }

    public void completeSignup(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        if (verificationTokenOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        verifyTokenExpiry(verificationTokenOptional.get());
    }

    private void verifyTokenExpiry(VerificationToken token) {
        boolean after = token.getExpiryDate().isAfter(LocalDateTime.now());
        if (!after) {
            throw new VerificationLinkExpired();
        }
        userRepository.updateUserVerification(token.getUserInfo().getEmail());
    }

}
