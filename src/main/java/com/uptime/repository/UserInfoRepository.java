package com.uptime.repository;

import com.uptime.model.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findByEmail(String s);

    @Modifying
    @Transactional
    @Query(value = "update users set is_verified=true where email=:email",nativeQuery = true)
    void updateUserVerification(@Param("email") String email);
}
