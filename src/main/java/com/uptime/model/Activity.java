package com.uptime.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ACTIVITY")
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;
    @ManyToOne
    private Monitor monitor;
    @Enumerated(EnumType.STRING)
    private CheckStatus status;
    private LocalDateTime dateTime;
}
