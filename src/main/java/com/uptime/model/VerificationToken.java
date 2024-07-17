package com.uptime.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VERIFICATION_TOKEN")
@Entity
@Builder
public class VerificationToken {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String token;

    @OneToOne
    private UserInfo userInfo;

    private LocalDateTime expiryDate;

    /**
     * The link expires in 24 hours
     */

    public void calculateExpiryDate() {
        if (expiryDate == null) {
            this.expiryDate = LocalDateTime.now().plusDays(1);
        }
    }

}
