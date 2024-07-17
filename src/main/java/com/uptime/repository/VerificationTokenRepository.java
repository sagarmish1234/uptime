package com.uptime.repository;

import com.uptime.model.UserInfo;
import com.uptime.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,String> {

    Optional<VerificationToken> findByToken(String token);
}
